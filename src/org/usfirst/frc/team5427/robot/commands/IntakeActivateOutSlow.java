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

/**
 * This command sets the speed of the intake motors to the received parameter.
 * 
 * @author Aditi
 */

public class IntakeActivateOutSlow extends Command {
	
	double speed;
	
	/**
	 * @param speed
	 */
	public IntakeActivateOutSlow(double speed) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
		this.speed = speed;
	}

	/**
	 *  Called just before this Command runs the first time
	 */
	@Override
	protected void initialize() {
		this.setInterruptible(true);
	}

	/**
	 * Sets the intake motor speed to the value in the constructor
	 */
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(speed);
	}

	/**
	 * @return true if the joystick button is released
	 */
	@Override
	protected boolean isFinished() {
		if(Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_MOTOR_INTAKE_OUT_SLOW))
			return true;
		return false;
	}

	/**
	 * Sets the intake speed to 0
	 */
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
	}

	/**
	 * Sets the intake speed to 0
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
