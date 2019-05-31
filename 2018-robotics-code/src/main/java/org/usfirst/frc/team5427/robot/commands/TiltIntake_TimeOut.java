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
 * Tilts the intake either up or down, and stops after a timeout set in config.
 * 
 * @author Akshat Jain
 */
public class TiltIntake_TimeOut extends Command {

	/**
	 * How long it takes for the intake to tilt up or down. This variable is
	 * initialized in initialize() using config values.
	 */
	private double tilt_time_out;

	/**
	 * Sets the timeout to values from config, depending on whether the intake needs
	 * to tilt up or down next.
	 */
	@Override
	protected void initialize() {
		if (Robot.tiltUpNext) {
			tilt_time_out = Config.TILT_TIMEOUT_UP;
		}
		else {
			tilt_time_out = Config.TILT_TIMEOUT_DOWN;
		}

		setTimeout(tilt_time_out);
	}

	/**
	 * Sets the speed of the tilt intake using speeds from config.
	 */
	@Override
	protected void execute() {
		if (Robot.tiltUpNext) {
			Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_UP);
		}
		else {
			Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_DOWN);
		}
	}

	/**
	 * Returns true after the command is timed out
	 * 
	 * @return true if the command is timed out
	 */
	@Override
	public boolean isFinished() {
		return isTimedOut();
	}

	/**
	 * Sets the tilt motor speed to 0 Changes whether the intake should tilt up or
	 * down next
	 */
	@Override
	protected void end() {
		Robot.motorPWM_TiltIntake.set(0);
		Robot.tiltUpNext = !Robot.tiltUpNext;
	}

	/**
	 * Called when another command which requires one or more of the same subsystems
	 * is scheduled to run
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
