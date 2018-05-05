/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;

/**
 * This command is used to move the robot backwards for a specified amount of
 * time.
 * 
 * @author Akshat Jain
 */
public class Center_SwitchIsLeft_Drive extends Command {

	/**
	 * The time that the robot will drive backwards for.
	 */
	private double time;
	private double speed;

	/**
	 * Receives the time the robot should drive backwards for and sets the timeout
	 * to that. Requires the drive train subsystem.
	 * 
	 * @param time
	 *            - the time the robot should drive backwards for.
	 */
	public Center_SwitchIsLeft_Drive(double time,  double speed) {
		requires(Robot.driveTrain);
		this.time = time;
		this.speed = speed;
	}

	/**
	 * Called once when the command is started but is not used for anything.
	 */
	@Override
	protected void initialize() {
		setTimeout(time);	
	}

	/**
	 * Called periodically while the command is running. Sets the velocity of the
	 * drive train to .3 power backwards.
	 */
	@Override
	protected void execute() {
		Robot.driveTrain.drive_Left.set(-speed);
		Robot.driveTrain.drive_Right.set(speed);
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
//		Robot.driveTrain.drive_Left.set(0);
//		Robot.driveTrain.drive_Right.set(0);
//		System.out.println("Ending Drivebackward");
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
