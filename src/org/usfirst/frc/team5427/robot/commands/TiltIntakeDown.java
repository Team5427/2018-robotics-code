/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.SameLine;

/**
 * Manually tilts the intake arms downward.
 * 
 * @author Akshat Jain
 */
@SameLine
public class TiltIntakeDown extends Command {

	/**
	 * Sets the tilt intake motor speed to the speed in config
	 */
	@Override
	protected void execute() {
		Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_DOWN);
	}

	/**
	 * Returns true when the intake tilt down button is released
	 * 
	 * @return true if the intake tilt down button is released
	 */
	@Override
	protected boolean isFinished() {
		return Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_INTAKE_TILTER_DOWN);
	}

	/**
	 * Sets the speed of the intake tilt motor to 0
	 */
	@Override
	protected void end() {
		Robot.motorPWM_TiltIntake.set(0);
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
