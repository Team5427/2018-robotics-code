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
import org.usfirst.frc.team5427.util.NextLine;

/**
 * This command simply sets the speed of the intake motors
 * 
 * @author Blake
 */

@NextLine
public class IntakeActivateIn extends Command
{
	
	public IntakeActivateIn()
	{

		// Use requires() here to declare subsystem dependencies
//		Log.info("Intake being activated");
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
//		Log.info("Intake being initialized");
		this.setInterruptible(true);
	}

	
	// Called repeatedly when this Command is scheduled to run
	@Override

	protected void execute()
	{
//		Log.info("Intake being executed");
		Robot.intakeSubsystem.setSpeed(Config.INTAKE_MOTOR_SPEED_IN);
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
		if(Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_MOTOR_INTAKE_IN))
			return true;
		return false;
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
		end();
	}
}
