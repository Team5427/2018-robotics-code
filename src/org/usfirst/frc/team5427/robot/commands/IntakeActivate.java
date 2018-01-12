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
import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;

/**
 * This command simply sets the speed of the intake motors to the current throttle on the joystick.
 * 
 * @author Blake
 */

@NextLine
public class IntakeActivate extends Command
{
	public IntakeActivate()
	{
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		if(Robot.oi.joy1.getThrottle()<0) {
			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle()*Config.INTAKE_FORWARD);
			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle()*Config.INTAKE_FORWARD);
		}
		else {
			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle()*Config.INTAKE_BACKWARD);
			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle()*Config.INTAKE_BACKWARD);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
		Log.info("END TO INTAKE INTERRUPTED");
		Robot.intakeSubsystem.setSpeed(0);
	}
}
