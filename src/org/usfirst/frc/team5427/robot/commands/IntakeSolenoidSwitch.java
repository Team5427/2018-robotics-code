/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.NextLine;

/**
 * @author Blake 
 * Command is used to change the direction of the Solenoid controlling the intake of Power Cubes.
 * 
 * TODO: Test whether this needs to be held & set to off or just press once.
 */

@NextLine
public class IntakeSolenoidSwitch extends Command
{
	//Stores whether or not the solenoid is forward for use in determining the direction to switch in.
	public static boolean isForward;

	public IntakeSolenoidSwitch()
	{
		// No Subsystem Needed
		
		isForward = false;
	}

	/* Called when the command initially runs.
	 * Checks to see if the position of the Solenoid is forward or reverse.
	 * If forward, it moves to reverse.
	 * If reverse, it moves to forward.
	 */
	@Override
	protected void initialize()
	{
		if(isForward)
			Robot.intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
			
		else
			Robot.intakeSolenoid.set(DoubleSolenoid.Value.kForward);

		isForward = !isForward;
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
		return true;
	}
 
	// Called once after isFinished returns true
	@Override
	protected void end()
	{
		
	}

	// Should be unnecessary considering it does not require a subsystem.
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted()
	{
	}
}
