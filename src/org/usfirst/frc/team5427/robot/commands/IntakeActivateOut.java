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
 * to the config value for speed (to shoot out cubes).
 * 
 * @author Blake
 */

@NextLine
public class IntakeActivateOut extends Command
{
	
	public IntakeActivateOut()
	{

		// Use requires() here to declare subsystem dependencies
//		Log.info("Intake being activated");
		requires(Robot.intakeSubsystem);
	}

	/**
	 *  Called just before this Command runs the first time
	 */
	@Override
	protected void initialize()
	{
//		Log.info("Intake being initialized");
		this.setInterruptible(true);
	}

	
	/**
	 *  Sets the speed of the intake motor to the config value for speed (reverse direction to shoot out cubes)
	 */
	@Override
	protected void execute()
	{
//		Log.info("Intake being executed");
//		if(Robot.motorPWM_Intake_Left.get)
		Robot.intakeSubsystem.setSpeed(Config.INTAKE_MOTOR_SPEED_OUT);
//		if(Robot.oi.joy1.getThrottle()<0) {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle());
//			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle());
//		}
//		else {
//			Robot.intakeSubsystem.setSpeed(Robot.oi.joy1.getThrottle());
//			Log.info("Throttle Value: "+Robot.oi.joy1.getThrottle());
//		}
	}

	/**
	 * @return true when the outtake button is released
	 */
	@Override
	protected boolean isFinished()
	{
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
//		this.free();
	}

	/**
	 * Sets the intake motor to 0 speed
	 */
	@Override
	protected void interrupted() {
//		Log.info("END TO INTAKE INTERRUPTED");
		end();
	}
}
