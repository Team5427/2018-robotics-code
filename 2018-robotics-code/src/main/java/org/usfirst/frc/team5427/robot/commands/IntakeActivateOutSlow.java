/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the speed of the intake motors to the received value,
 * typically used to shoot out boxes at a different speed than listed in Config.
 * Runs while the intake button is held down.
 * 
 * @author Aditi
 */

public class IntakeActivateOutSlow extends Command {

	/**
	 * The intended speed for the intake motors.
	 */
	double speed;

	/**
	 * The command, IntakeActivateOutSlow, requires the intake subsystem for its
	 * use.
	 * 
	 * @param speed
	 *            the speed to set the intake motors to.
	 */
	public IntakeActivateOutSlow(double speed) {
		requires(Robot.intakeSubsystem);
		this.speed = speed;
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
	 * intake motors to the received value for speed.
	 */
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(speed);
	}

	/**
	 * Called periodically while the command is running.
	 * 
	 * @return if the intake button is released
	 */
	@Override
	protected boolean isFinished() {
		if (Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_MOTOR_INTAKE_OUT_SLOW))
			return true;
		return false;
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
