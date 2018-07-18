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
 * This command sets the speed of the intake motors to the config value for
 * speed (to pick up cubes). Runs while the intake button is held down.
 * 
 * @author Blake Romero
 */
@SameLine
public class IntakeActivateIn extends Command {

	/**
	 * The command, IntakeActivateIn, requires the intake subsystem for its use.
	 */
	public IntakeActivateIn() {
		requires(Robot.intakeSubsystem);
	}

	/**
	 * Called once when the command is started. Signifies that this command is able
	 * to be interrupted by others.
	 */
	@Override
	protected void initialize() {
		this.setInterruptible(true);
	}

	/**
	 * Called periodically while the command is running. Sets the speed of the
	 * intake motors to the config value for speed.
	 */
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(Config.INTAKE_MOTOR_SPEED_IN);
	}

	/**
	 * Called periodically while the command is running.
	 * 
	 * @return if the intake button is released
	 */
	@Override
	protected boolean isFinished() {
		return !Robot.oi.getJoy().getRawButton(Config.BUTTON_MOTOR_INTAKE_IN);
	}

	/**
	 * Called once when the isFinished method returns true. Sets the intake motors
	 * to 0 power in order to stop them from moving.
	 */
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
	}

	/**
	 * Called once when the command is interrupted. Calls the end method in order to
	 * stop the intake motors from moving.
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
