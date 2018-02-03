/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;

/**
 * @author Blake
 * This command 
 */

@NextLine
public class MoveElevator extends Command
{
	public static int direction;
	
	public MoveElevator(int direction)
	{
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize()
	{
		if(direction == 1)
		{
//			Log.init("move forward");
			Robot.motorPWM_Elevator.set(.3);
		}
		else if(direction == 2)
		{
			Robot.motorPWM_Elevator.set(-.3);
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
		Robot.motorPWM_Elevator.set(0);
	}
}
