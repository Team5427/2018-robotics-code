/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This is our command that shoots a cube out of the robot automatically.
 * 
 * @author Blake Romero
 */
@SameLine
public class AutoOutGo extends Command {

	/**
	 * AutoOutGo requires the intake subsystem and its timeout is set via Config.
	 */
	public AutoOutGo() {
		requires(Robot.intakeSubsystem);
		this.setTimeout(Config.AUTO_OUTTAKE_TIMEOUT);
	}

	/**
	 * Called once when the command is started but is not currently being used.
	 */
	@Override
	protected void initialize() {}

	/**
	 * Called periodically while the command is running to set the speed of the
	 * intake to -.5 power.
	 */
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(-.5);
	}

	/**
	 * Called periodically while the command is running to check when it is
	 * finished.
	 * 
	 * @return true when the command times out.
	 */
	@Override
	public boolean isFinished() {
		return this.isTimedOut();
	}

	/**
	 * Called once when isFinished() returns true. Sets the speed of the intake to 0
	 * power.
	 */
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
	}

	/**
	 * Called once if the command is interrupted. Sets the speed of the intake to 0
	 * power.
	 */
	@Override
	protected void interrupted() {
		Robot.intakeSubsystem.setSpeed(0);
	}
}
