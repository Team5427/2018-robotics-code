/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is used to move the robot forward for a specified amount of
 * time.
 * 
 * @author Andrew Li
 */
public class RightScale_DriveToScale extends Command {
	/**
	 * The time that the robot will drive forwards for.
	 */
	private double time = 1.5;
	
//	private double speed = 0.5;
	
//	private double rotationValue = 0.65;

	/**
	 * Receives the time the robot should drive forwards for and sets the timeout
	 * to that. Requires the drive train subsystem.
	 * 
	 * @param time
	 *            the time the robot should drive forwards for.
	 */
	public RightScale_DriveToScale() {
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
	 * drive train to .3 power forwards.
	 */
	@Override
	protected void execute() {
//		this.speed/=1.015;
//		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue,false);
		Robot.driveTrain.drive_Left.set(.3);
		Robot.driveTrain.drive_Right.set(-.3);
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
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}

	/**
	 * Called once if the command is interrupted. Calls the end method in response.
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
