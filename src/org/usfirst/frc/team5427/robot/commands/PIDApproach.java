package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.subsystems.UltrasonicPID;

import edu.wpi.first.wpilibj.command.Command;

public class PIDApproach extends Command
{
	UltrasonicPID ultra;
	
	public PIDApproach()
	{
		ultra = Robot.ultra;
	}
	
	protected void initialize()
	{
		ultra.setSetpoint(12);
		ultra.enable();
	}

	@Override
	protected boolean isFinished()
	{
		return ultra.ultra.getRangeInches() == 12;
	}
	
	protected void end()
	{
		ultra.disable();
	}
}
