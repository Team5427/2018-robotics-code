/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.autoCommands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.PIDDistance;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive straight in the Right Scale when robot is right.
 * 
 * TODO Document class correctly.
 * 
 * @author Akshat Jain
 */
public class Right_ScaleAndSwitch_FirstDistance extends PIDCommand {
	// This command is created once we have reached the maximum power to control the
	// distance that we travel.
	private PIDDistance pidDistance;
	// This SpeedControllerGroup is the side of the robot that this command
	// controls.
	private boolean hasStarted;
	private SpeedControllerGroup scgPIDControlled;
	// This SpeedControllerGroup is the side of the robot that is controlled by a
	// constant value and PIDDistance.
	private SpeedControllerGroup scgConstant;
	// This is the maximum speed that the robot will travel at.
	private double maximumSpeed = Config.PID_STRAIGHT_POWER_LONG;
	// This is the distance that we want to travel.
	private double desiredDistance;
	// This is the power that scgPIDControlled is set to.
	private double power;
	// These are the p, i, and d values for the PID Controller in PIDDistance.
	private static final double p = 0.011;
	private static final double i = 0.0;
	private static final double d = 0.018;
	
	private double startTime;
	private double endTime;
	private double startDistance;
	private double endDistance;

	/**
	 * Constructor for PIDStraightMovement
	 * 
	 * @param scgPIDControlled
	 *            - This receives the side of the robot that we are controlling with
	 *            this PIDCommand.
	 * @param scgConstant
	 *            - This receives the side of the robot that we will control with
	 *            the PIDDistance command.
	 *
	 */

	public Right_ScaleAndSwitch_FirstDistance(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, Config.PID_UPDATE_PERIOD);
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		this.desiredDistance = 250;
		
		this.setInterruptible(true);
		this.getPIDController().setSetpoint(0);
		setSetpoint(0);
		this.power = .2;
		hasStarted = false;
	}

	/**
	 * Command implemented from PIDCommand This is called automatically after the
	 * constructor of the command is run. We only use this to start the
	 * PIDController of moving straight.
	 */

	@Override
	protected void initialize() {
		super.getPIDController().enable();
		this.pidDistance = null;
		Robot.encLeft.reset();
		Robot.ahrs.reset();
		power = .2;
		hasStarted = false;
		
	}

	/**
	 * Command implemented from PIDCommand This returns the value to be used by the
	 * PID loop. We are returning the angle received from the NavX on the robot.
	 */
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	/**
	 * Command implemented from PIDCommand This is sent the output from the PID loop
	 * for us to use. We are setting the side of the robot that we control in this
	 * PID loop to the output to maintain the angle of 0 to go straight. After we
	 * reach maximum power, we activate the PIDDistance command in order to finish
	 * traveling a certain distance.
	 */
	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Velocity", Robot.encLeft.getRate());
		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		SmartDashboard.putNumber("encLeft", Math.abs(Robot.encLeft.getDistance()));
		SmartDashboard.putNumber("encLeftVal", Math.abs(Robot.encLeft.getDistance()));

			scgPIDControlled.pidWrite(output);
			if(Robot.encLeft.getDistance()>=this.desiredDistance)
			{
				if(scgConstant.get()==0)
					SmartDashboard.putNumber("EncLeft when switch", Robot.encLeft.getDistance());
				
				scgConstant.set(0);
			}
			else if(null==pidDistance)
				scgConstant.set(power);
			
			SmartDashboard.putNumber("g", scgConstant.get());
			SmartDashboard.putNumber("o", output);
			SmartDashboard.putNumber("p", power);

			if (this.power < this.maximumSpeed&&null==pidDistance) //TODO change to boolean
			{
				
				SmartDashboard.putBoolean("Incrememtning", true);

				this.power += Config.PID_STRAIGHT_LINEAR_INCREMENT;

			}
			else if(null==pidDistance) {
				SmartDashboard.putNumber("ECLLeft when stop increment", Robot.encLeft.getDistance());

				SmartDashboard.putBoolean("AM I RUNNING?", true);

				pidDistance = new PIDDistance(this.scgConstant, this.scgPIDControlled, this.maximumSpeed, this.desiredDistance, this.p, this.i, this.d);
				pidDistance.start();
			}
			SmartDashboard.putNumber("ECLLeft", Robot.encLeft.getDistance());
			SmartDashboard.putNumber("ECLLeftVAL", Robot.encLeft.getDistance());
			if(power>=this.maximumSpeed/4)
				hasStarted = true;

	}

	/**
	 * Command implemented from PIDCommand. When this returns true, the command runs
	 * end() Our method returns true if the robot has traveled close enough to the
	 * certain distance, with our tolerance.
	 */
	@Override
	public boolean isFinished() {
		if (pidDistance != null && pidDistance.isFinished() && Math.abs(Robot.ahrs.getYaw()) < 3) {
			pidDistance.end();// TODO check if ending works correctly
			end();// TODO take these out
			return true;
		}
		else if((Robot.encLeft.getStopped())&&hasStarted)//TODO moves on if enc is stopped
		{
			if(null!=pidDistance)
				pidDistance.end();// TODO check if ending works correctly
			end();// TODO take these out
			return true;
		}
		return false;
	}

	/**
	 * Command implemented from PIDCommand This is called whenever this command is
	 * interrupted by something else. We do not use this for anything purposeful, so
	 * we just call end().
	 */
	@Override
	protected void interrupted() {
		end();
	}

	/**
	 * Command implemented from PIDCommand This is called whenever either
	 * isFinished() returns true or interrupted() runs. We use this to end the PID
	 * loop, reset the encoders' distances, and set the speed of our
	 * SpeedController.
	 */
	@Override
	public void end() {
		free();
		super.end();
	}

	/**
	 * Command implemented from PIDCommand This is called manually and is meant to
	 * disable the PID loop and reset values. We are using this to disable the PID
	 * loop, reset the PID loop and the encoders' distances, and set the speed of
	 * the side of the robot that we control to 0;
	 */
	@Override
	public void free() {
		// System.out.println("Free in PIDStraight");
		super.free();
		super.getPIDController().disable();
		super.getPIDController().reset();
	}

}