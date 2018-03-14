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
 * An example command.  You can replace me with your own command.
 */
public class DriveBackward extends Command {
	private double time;
	
	public DriveBackward(double time) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		setTimeout(time);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
//		this.setTimeout(this.time);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveTrain.drive_Left.set(-.3);
		Robot.driveTrain.drive_Right.set(.3);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
