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
import org.usfirst.frc.team5427.util.NextLine;

/**
 * This command simply sets the speed of the intake motors 
 * to the config value for speed (to shoot out cubes). Runs
 * while the outtake button is held down.
 * 
 * @author Blake Romero
 */

public class IntakeActivateOut extends Command {
	
	public IntakeActivateOut() {
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		this.setInterruptible(true);
	}

	
	/**
	 *  Sets the speed of the intake motor to a negative value to the 
	 *  config value for speed (to shoot out cubes).
	 */
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(Config.INTAKE_MOTOR_SPEED_OUT);
	}

	/**
	 * @return true when the intake out button is released
	 */
	@Override
	protected boolean isFinished() {
		if(Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_MOTOR_INTAKE_OUT))
			return true;
		return false;
	}

	/**
	 * Sets the intake motor to 0 speed
	 */
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
	}

	/**
	 * Sets the intake motor to 0 speed
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
