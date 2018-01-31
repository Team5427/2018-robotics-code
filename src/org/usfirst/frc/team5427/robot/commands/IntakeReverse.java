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
public class IntakeReverse extends Command
{
	public IntakeReverse()
	{
		Log.info("Intake being activated");
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		Log.info("Intake being initialized");
		
		Robot.intakeSubsystem.setSpeed(Config.INTAKE_SPEED_REVERSE);
		
//		if(Robot.oi.joy1.getY()<0) {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getY()*Config.INTAKE_FORWARD);
//			Log.info("Joystick Y Value: "+Robot.oi.joy1.getY());
//		}
//		else {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getY()*Config.INTAKE_BACKWARD);
//			Log.info("Joystick Y Value: "+Robot.oi.joy1.getY()*Config.INTAKE_BACKWARD);
//		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
//		Log.info("Intake being executed");
//		if(Robot.oi.joy1.getThrottle()<0) {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle());
//			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle());
//		}
//		else {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle());
//			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle());
//		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		if(Robot.oi.getJoy().getRawButtonReleased(1))//TODO change to Config
			return true;
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