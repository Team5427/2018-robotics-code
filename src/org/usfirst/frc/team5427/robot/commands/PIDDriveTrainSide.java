package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/**
 * This class is adapted from the WPILib PIDCommand Class. This Class is made to
 * make the robot drive straight after ramping up power to a desired power. It
 * works by setting one side of the drive train to the desired power and varying
 * the speed of the other side of the drive train to maintain the robot driving
 * exactly straight forward (a YAW of 0)
 * 
 * 
 * In theory, this class can be used in any case where two SpeedControllerGroup
 * objects work together to accomplish the same task.
 */
public class PIDDriveTrainSide extends PIDCommand {
	private SpeedControllerGroup scgPIDControlled;
	private SpeedControllerGroup scgConstant;
	public double power;
	private double desiredDistance;
	// increment every other iteration, tried it but did not make significant diff,
	// may come back
	// private boolean flipFlop;
	/**
	 * Constructor for this class
	 * 
	 * @param scgPIDControlled
	 *            - the SpeedControllerGroup for the side whose power will be
	 *            controlled via the PID Loop
	 * @param scgConstant
	 *            - the SpeedControllerGroup for teh side whose power will be set to
	 *            a constant value
	 * @param p
	 *            - the P value for the PID Loop
	 * @param i
	 *            - the I value for the PID Loop
	 * @param d
	 *            - the D value for the PID Loop
	 * @param setpoint
	 *            - the Set Point for the PID Loop
	 * @param desiredDistance:
	 *            The distance you would like the PID Command to travel
	 */
	public PIDDriveTrainSide(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double setpoint, double desiredDistance) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D);
		// m_controller = new PIDController(p, i, d, m_source, m_output);
		super.setSetpoint(setpoint);
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		this.desiredDistance = desiredDistance;
		resetOurValues();
		super.setSetpoint(setpoint);
		// initialize();
	}

	@Override
	// begins the PID loop (enables)
	protected void initialize() {
		super.getPIDController().enable();
		this.power = 0.05;
		this.scgPIDControlled.set(0.05);
		this.scgConstant.set(0.05);
	
	}

	// Ends (disables) the PID loop and stops the motors of the
	// SpeedControllerGroups
	@Override
	protected void end() {
		super.end();
//		System.out.println("ENDED DISTANCE " + this.desiredDistance);
		Robot.encRight.reset();
		Robot.encLeft.reset();
		super.getPIDController().disable();
		scgPIDControlled.set(0);
		scgConstant.set(0);
		resetOurValues();
		super.getPIDController().reset();
		super.free();
	}

	// Code to run when this command is interrupted
	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		// isFinished for coasting PID
		// If the robot is finished coasting
		// if(pidCoasting!=null && pidCoasting.isFinished()) {
		// System.out.println("Is FINISHED TRUE FOr "+ desiredDistance);
		// end();
		// return true;
		// }
		// if(/*robot encoder value is larger than desiredDistance -
		// Config.getCoastDistance()*/)
		// if(0>=Config.getCoastingDistance(desiredPower))//TODO Create Encoders in
		// Robot and use their value here and use
		// parameter desiredDistance
		// If the robot is disabled
		// if (RobotState.isDisabled()) {
		// end();
		// return true;
		// }
//		System.out.println((Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance()))/ 2+" ");
	if ((Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2 > desiredDistance - Config.PID_STRAIGHT_TOLERANCE) {
			// robot stopped
//			if (Math.abs(Robot.encLeft.getRate()) < .1) {
//				if (initialStop == 0) {
//					initialStop = (Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2;
//				}
//				return true;
//			}
			return true;
		}
		return false;
	}

	public void setPower(double power) {
		this.power = power;
	}
	public void powerIncrement() {
		if (this.power < Config.PID_STRAIGHT_POWER) {
			// Linear incrementation
//			this.power+=this.increment;
			// Exponential incrementation
			this.power*=Config.EXPONENTIAL_INCREMENT_VALUE;
//			if(power==0) {
//				System.out.println("POWER SHOULD NOT BE 0");
//			}
//			SmartDashboard.putNumber("POWER IN INCREMENT",power);
		}
		if(this.power>=Config.PID_STRAIGHT_POWER)
		{this.power=Config.PID_STRAIGHT_POWER;}
//		else {
//			scgConstant.set(power);
//			SmartDashboard.putNumber("POWER IN ELSE",power);
//		}
	}

	/**
	 * reutrns the input of the PID LOOP (AKA AHRS Yaw)
	 */
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	/**
	 * method to set motor values using the parameter "output" This method is
	 * automatically called by PIDCommand
	 */
//	private int count;
	@Override
	public void execute()
	{
		SmartDashboard.putNumber("Pre-incr PWR", ((PWM)Robot.motor_pwm_frontLeft).getRaw());
	
		powerIncrement();
		SmartDashboard.putNumber("Pre-set PWR", ((PWM)Robot.motor_pwm_frontLeft).getRaw());

		scgConstant.set(power);
		
		if(power<.1) {
					scgPIDControlled.set(power);
		}
		
		SmartDashboard.putNumber("EXEC POWER", ((PWM)Robot.motor_pwm_frontLeft).getRaw());
		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
	}
	
	
	@Override
	protected void usePIDOutput(double output) {
//		isInRange = desiredDistance - (Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2 < Config.getCoastingDistance(power);
//		SmartDashboard.putNumber("encRight", Math.abs(Robot.encRight.getDistance()));
//		SmartDashboard.putNumber("encLeft", Math.abs(Robot.encLeft.getDistance()));
//		SmartDashboard.putNumber("encRightVal", Math.abs(Robot.encRight.getDistance()));
//		SmartDashboard.putNumber("encLeftVal", Math.abs(Robot.encLeft.getDistance()));
//		SmartDashboard.putNumber("PID output", output);
		

		
		
		// if current power is less than the goal, increment the power
//		if (!isInRange) {
//			scgConstant.set(power);

//		if(System.nanoTime()/1000000000.0%1<0.05) {
//			System.out.println(count);
//		}
//		SmartDashboard.putNumber("Pre-incr PWR", ((PWM)Robot.motor_pwm_frontLeft).getRaw());
//
//		powerIncrement();
//		SmartDashboard.putNumber("Pre-set PWR", ((PWM)Robot.motor_pwm_frontLeft).getRaw());
//
//		scgConstant.set(power);
		
		if(power>=.1) {
			scgPIDControlled.pidWrite(output);
		}
		else {
			scgPIDControlled.set(power);
		}
//		
//		SmartDashboard.putNumber("POWER", ((PWM)Robot.motor_pwm_frontLeft).getRaw());
//		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
//		}
//		else
//		{
//			scgConstant.set(0);
//			scgPIDControlled.set(0);
//		}
//		// else if it is equal to goal, print the time it took, and iterations
//		else {
//			if (toGoalTime == 0) {
//				toGoalTime = System.nanoTime() / 1000000000.0 - this.startTime;
//			}
//		}
		// if (isCoasting && this.pidCoasting == null) {
		// System.out.println("Distance traveled: " +
		// ((Math.abs(Robot.encLeft.getDistance()) +
		// Math.abs(Robot.encRight.getDistance())) / 2)+"for
		// distance"+this.desiredDistance);
		// pidCoasting = new PIDCoasting(this.scgPIDControlled, this.scgConstant,
		// this.desiredDistance);
		// }
	}

	@Override
	public void free() {
		super.free();
		super.getPIDController().disable();
		resetOurValues();
		scgPIDControlled.set(0);
		scgConstant.set(0);
		// pidCoasting.free();
		super.getPIDController().reset();
	}

	/**
	 * resets the values of certain
	 */
	public void resetOurValues() {
		this.power = 0;
//		this.scgPIDControlled.set(0);
//		this.scgConstant.set(0);
//		System.nanoTime() / 1000000000;
		// this.pidCoasting = null;
		super.getPIDController().reset();
		Robot.encLeft.reset();
		 Robot.encRight.reset();
	}
	// public boolean getIsCoasting() {
	// return isCoasting;
	// }
}
