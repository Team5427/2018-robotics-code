/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
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
 * This class is adapted from the WPILib PIDCommand Class. This Class is made to
 * make the robot drive straight after ramping up power to a desired power. It
 * works by setting one side of the drive train to the desired power and varying
 * the speed of the other side of the drive train to maintain the robot driving
 * exactly straight forward (a YAW of 0)
 * 
 * In theory, this class can be used in any case where two SpeedControllerGroup
 * objects work together to accomplish the same task.
 * 
 * @author Blake
 */
public class PIDStraightMovement extends PIDCommand {
	// This command is created once we have reached the maximum power to control the
	// distance that we travel.
	private PIDDistance pidDistance;
	// This SpeedControllerGroup is the side of the robot that this command
	// controls.
	private SpeedControllerGroup scgPIDControlled;
	// This SpeedControllerGroup is the side of the robot that is controlled by a
	// constant value and PIDDistance.
	private SpeedControllerGroup scgConstant;
	// This is the maximum speed that the robot will travel at.
	private double maximumSpeed;
	// This is the distance that we want to travel.
	private double desiredDistance;
	// This is the power that scgPIDControlled is set to.
	private double power;
	// These are the p, i, and d values for the PID Controller in PIDDistance.
	private double p, i, d;
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
	 * @param maximumSpeed
	 *            - This receives the maximum speed that the robot will travel at.
	 * @param desiredDistance
	 *            - This receives the distance that we want to travel.
	 * @param p,
	 *            i, d - These receive the P, I, and D values for the PID Controller
	 *            in PIDDistance.
	 */
	public PIDStraightMovement(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double maximumSpeed, double desiredDistance, double p, double i, double d) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, Config.PID_UPDATE_PERIOD);
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		this.maximumSpeed = maximumSpeed;
		this.desiredDistance = desiredDistance;
		this.p = p;
		this.i = i;
		this.d = d;
//		this.getPIDController().setOutputRange(-maximumSpeed, maximumSpeed);
//		this.getPIDController().setAbsoluteTolerance(.1);
		
		this.getPIDController().setSetpoint(0);
//		super.setSetpoint(0);
		setSetpoint(0);
		this.power = .01;
		// scgConstant.set(0);
		// scgPIDControlled.set(0);
	}

	/*
	 * Command implemented from PIDCommand This is called automatically after the
	 * constructor of the command is run. We only use this to start the
	 * PIDController of moving straight.
	 */
	int e;
	int c;

	@Override
	protected void initialize() {
		c = 0;
		e = 0;
		SmartDashboard.putNumber("c", c);
		SmartDashboard.putNumber("E", e);
		super.getPIDController().enable();
		this.pidDistance = null;
		Robot.encLeft.reset();
		Robot.ahrs.reset();
		// Robot.encRight.reset();
		// if using exponential increment
		power = .01;
//		scgConstant.set(power);
//		scgPIDControlled.set(-power);
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
//		if (this.power <= this.maximumSpeed && this.pidDistance == null) {
//			// linear increment
//			// this.power += Config.PID_STRAIGHT_LINEAR_INCREMENT;
//			scgConstant.set(power);
//			SmartDashboard.putNumber("g", scgConstant.get());
//
////			if(Robot.encLeft.getRate() < -Config.SWITCH_TO_PID_VELOCITY)
////				// TODO change this
////				scgPIDControlled.set(-power);
////			else
//				scgPIDControlled.pidWrite(output);
////			this.power *= Config.PID_STRAIGHT_EXPONENTIAL_INCREMENT;
//			this.power += Config.PID_STRAIGHT_LINEAR_INCREMENT;
//			System.out.println("Power: " + power);
//			SmartDashboard.putNumber("PID output", output);
//			SmartDashboard.putNumber("SCGconstant", scgConstant.get());
//		}
//		/*if (this.power >= this.maximumSpeed && pidDistance == null) {
////			this.pidDistance = new PIDDistance(this.scgConstant, this.scgPIDControlled, this.maximumSpeed, this.desiredDistance, this, this.p, this.i, this.d);
////			pidDistance.start();
//		}
//		else
//		if (this.power >= this.maximumSpeed) {
			scgPIDControlled.pidWrite(output);
			scgConstant.set(power);
			SmartDashboard.putNumber("g", scgConstant.get());
			SmartDashboard.putNumber("o", output);
			SmartDashboard.putNumber("p", power);

			if (this.power <= this.maximumSpeed && this.pidDistance == null) 
				this.power += Config.PID_STRAIGHT_LINEAR_INCREMENT;

//		}
//		if (pidDistance != null && pidDistance.getDistance() > this.desiredDistance)
//			super.getPIDController().setOutputRange(-.5, .5);// TODO do not set if already set
	}

	/**
	 * Command implemented from PIDCommand. When this returns true, the command runs
	 * end() Our method returns true if the robot has traveled close enough to the
	 * certain distance, with our tolerance.
	 */
	@Override
	public boolean isFinished() {
//		if (pidDistance != null && pidDistance.isFinished() && Math.abs(Robot.ahrs.getYaw()) < 3) {
////			pidDistance.end();// TODO check if ending works correctly
////			end();// TODO take these out
//			return true;
//		}
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
		// System.out.println("Ending PID Straight");
		// pidDistance.end();//TODO put this back in?
		SmartDashboard.putNumber("E", ++e);
//		this.scgPIDControlled.set(0);
//		this.scgConstant.set(0);
//		this.power = 0;
		free();
		super.end();
		// System.out.println("ENDED PID STRAIGHT");
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