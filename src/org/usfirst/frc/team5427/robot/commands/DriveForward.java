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
 * This class is used to move the robot forwards by setting the motors of each side of the driveTrain to forward
 */
public class DriveForward extends Command {
	private double time;
	
	/**
	 * Sets the timeout of the command in seconds
	 * 
	 * @param time
	 */
	public DriveForward(double time) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		setTimeout(time);
	}

	/**
	 *  Called just before this Command runs the first time
	 */
	@Override
	protected void initialize() {
		// this.setTimeout(this.time);
	}

	/**
	 * Sets the left and right motors of the driveTrain to .3 (right is negative because it is orientated differently)
	 */
	@Override
	protected void execute() {
		Robot.driveTrain.drive_Left.set(.3);
		Robot.driveTrain.drive_Right.set(-.3);
	}

	/**
	 *  Make this return true when this Command no longer needs to run execute()
	 *  
	 *  @return true if the set time is up
	 */
	@Override
	public boolean isFinished() {
		return this.isTimedOut();
	}

	/**
	 *  Sets the right and left motors to 0
	 */
	@Override
	protected void end() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}

	/**
	 *  Sets the right and left motors to 0
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
