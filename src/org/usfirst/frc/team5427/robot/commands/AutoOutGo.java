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
 * Makes the Intake deliver the cube 
 */
public class AutoOutGo extends Command {
	public AutoOutGo() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
		this.setTimeout(Config.AUTO_INTAKE_TIMEOUT);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.intakeSubsystem.setSpeed(-.5);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		return this.isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.intakeSubsystem.setSpeed(0);
	}
}
