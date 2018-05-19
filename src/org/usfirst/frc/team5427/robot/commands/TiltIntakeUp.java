/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manually tilts the intake arms upward.
 * 
 * @author Akshat Jain
 */
@SameLine
public class TiltIntakeUp extends Command {

	/**
	 * Sets the motor speed to the tilt up speed in config.
	 */
	@Override
	protected void execute() {
		Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_UP);
	}

	/**
	 * Returns true when the intake tilt up button is released.
	 * 
	 * @return true if the intake tilt up button is released.
	 */
	@Override
	protected boolean isFinished() {
		return Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_INTAKE_TILTER_UP);
	}

	/**
	 * Sets the speed of the intake tilt motor to 0.
	 */
	@Override
	protected void end() {
		Robot.motorPWM_TiltIntake.set(0);
	}

	/**
	 * Called when another command which requires one or more of the same subsystems
	 * is scheduled to run.
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
