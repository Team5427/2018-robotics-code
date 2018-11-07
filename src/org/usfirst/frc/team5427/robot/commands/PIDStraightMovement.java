/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * This class is adapted from the WPILib PIDCommand Class. This class is made to
 * make the robot drive straight after ramping up power to a desired power. It
 * works by setting one side of the drive train to the desired power and varying
 * the speed of the other side of the drive train to maintain the robot driving
 * exactly straight forward (a YAW of 0)
 * 
 * In theory, this class can be used in any case where two SpeedControllerGroup
 * objects work together to accomplish the same task.
 * 
 * @author Blake Romero
 */
public class PIDStraightMovement extends PIDCommand {

	/**
	 * The SpeedControllers that contain the motors that are controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgPIDControlled;

	/**
	 * The SpeedControllers that contain the motors that are not controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgRamping;

	private boolean isRamping;
	
	/**
	 * The maximum speed the PIDController can output to the motors on the robot.
	 */
	double maximumSpeed;

	/**
	 * The current speed of the SpeedControllerGroups
	 */
	public double power;

	/**
	 * The P value for the PIDController in PIDDistance.
	 */
	private double p;

	/**
	 * The I value for the PIDController in PIDDistance.
	 */
	private double i;

	/**
	 * The D value for the PIDController in PIDDistance.
	 */
	private double d;

	/**
	 * Creates the PIDController for this command using config PID values and sets
	 * the parameters of its control.
	 * 
	 * @param maximumSpeed
	 *            the maximum speed that the robot will travel at.
	 * @param desiredDistance
	 *            the distance that we want to travel.
	 * @param p
	 *            the P value for the PIDController in PIDDistance.
	 * @param i
	 *            the I value for the PIDController in PIDDistance.
	 * @param d
	 *            the D value for the PIDController in PIDDistance.
	 */
	public PIDStraightMovement(double maximumSpeed) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, Config.PID_UPDATE_PERIOD);

		this.scgPIDControlled = Robot.driveTrain.drive_Left;
		this.scgRamping = Robot.driveTrain.drive_Right;
		this.maximumSpeed = maximumSpeed;
		this.p = 0.01;
		this.i = 0;
		this.d = 0.01;

		this.setInterruptible(true);
		this.getPIDController().setSetpoint(0);
		setSetpoint(0);

		this.power = .01;
		
		isRamping = true;
	}

	/*
	 * Command implemented from PIDCommand This is called automatically after the
	 * constructor of the command is run. We only use this to start the
	 * PIDController of moving straight.
	 */
	@Override
	protected void initialize() {
		super.getPIDController().enable();
		Robot.encLeft.reset();
		Robot.ahrs.reset();
		power = .01;
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
	 * 
	 * @param output
	 *            the output of the PIDController within PIDStraightMovement when
	 *            given our current Yaw.
	 */
	@Override
	protected void usePIDOutput(double output) {

		scgPIDControlled.pidWrite(output);
		
		if(power < maximumSpeed && isRamping)
		{
			this.power += Config.PID_STRAIGHT_LINEAR_INCREMENT;
			scgRamping.set(power);
		}
	}

	public void setRamping(boolean ramp)
	{
		isRamping = ramp;
	}
	
	/**
	 * Command implemented from PIDCommand. When this returns true, the command runs
	 * end() Our method returns true if the robot has traveled close enough to the
	 * certain distance, with our tolerance.
	 * 
	 * @return if the PIDDistance command has finished.
	 */
	@Override
	public boolean isFinished() {
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
		super.free();
		super.getPIDController().disable();
		super.getPIDController().reset();
	}

}