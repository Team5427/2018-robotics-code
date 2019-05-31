/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to move the robot backwards for a specified amount of
 * time.
 * 
 * @author Kipp Corman
 */
public class DriveBackward extends Command {

	/**
	 * Receives the time the robot should drive backwards for and sets the timeout
	 * to that. Requires the drive train subsystem.
	 * 
	 * @param time
	 *            - the time the robot should drive backwards for.
	 */
	public DriveBackward(double time) {
		requires(Robot.driveTrain);
		setTimeout(time);
	}

	/**
	 * Called once when the command is started but is not used for anything.
	 */
	@Override
	protected void initialize() {
			
	}

	/**
	 * Called periodically while the command is running. Sets the velocity of the
	 * drive train to .3 power backwards.
	 */
	@Override
	protected void execute() {
		Robot.driveTrain.drive_Left.set(-.2);
		Robot.driveTrain.drive_Right.set(.2);
	}

	/**
	 * Called periodically while the command is running to check when the command is
	 * finished.
	 * 
	 * @return true when the command times out.
	 */
	@Override
	public boolean isFinished() {
		return this.isTimedOut();
	}

	/**
	 * Called once when the command is finished. Sets the velocity of the drive
	 * train to 0 power.
	 */
	@Override
	protected void end() {
		Robot.driveTrain.drive.stopMotor();
	}

	/**
	 * Called once if the command is interrupted. Calls the end method in response.
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
