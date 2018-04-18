/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;

/**
 * A command that moves the robot forwards and then backwards. Used at the start of autonomous to
 * hit the robot against the wall, causing the intake to fall down.
 */
public class Fidget extends Command {
	
	private boolean forwardDone;
	
	/**
	 * Sets the timeout to .1 seconds
	 */
	public Fidget() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		this.forwardDone = false;
		setTimeout(0.1);
	}

	/**
	 *  Called just before this Command runs the first time
	 */
	@Override
	protected void initialize() {
	}

	/**
	 *  Moves the robot forward at .5 speed for .1 seconds, and then backward at .3 for .6 seconds
	 */
	@Override
	protected void execute() {
		if(!forwardDone)
		{
			Robot.driveTrain.drive_Left.set(0.5);
			Robot.driveTrain.drive_Right.set(-0.5);
		}
		else if(forwardDone)
		{
			Robot.driveTrain.drive_Left.set(-0.3);
			Robot.driveTrain.drive_Right.set(0.3);
		}
	}

	/**
	 * @return true when the robot is done moving forward and back
	 */
	@Override
	public boolean isFinished() {
		if(this.isTimedOut() && !forwardDone)
		{
			forwardDone = true;
			setTimeout(0.6);
		}
		else if(this.isTimedOut() && forwardDone)
		{
			return true;
		}
		return false;
//		return true;
	}

	/**
	 * Sets the speed of both sides of the driveTrain to 0
	 */
	@Override
	protected void end() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}

	/**
	 * Sets the speed of both sides of the driveTrain to 0
	 */
	@Override
	protected void interrupted() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}
}
