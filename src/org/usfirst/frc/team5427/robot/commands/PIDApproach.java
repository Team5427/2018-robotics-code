package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.subsystems.UltrasonicPID;

import edu.wpi.first.wpilibj.command.Command;

public class PIDApproach extends Command
{
	UltrasonicPID ultraPID;
	PIDStraightMovement straightPID;
	
	public PIDApproach()
	{
		ultraPID = Robot.ultra;
	}
	
	protected void initialize()
	{
		straightPID = new PIDStraightMovement(0.5);//TODO: maximum speed, add to config
		ultraPID.setSetpoint(0);
	}

	@Override
	protected void execute()
	{
		if(ultraPID.ultra.getRangeInches() < 24)
		{
			straightPID.setRamping(false);
			ultraPID.enable();
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		return ultraPID.ultra.getRangeInches() == 12;
	}
	
	protected void end()
	{
		ultraPID.disable();
		straightPID.end();
	}
}
