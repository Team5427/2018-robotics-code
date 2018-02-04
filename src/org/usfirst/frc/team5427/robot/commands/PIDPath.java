package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class PIDPath extends Command
{
	private PIDDriveTrainSide firstDistance, secondDistance, thirdDistance;
	private PIDTurnCommand firstAngle, secondAngle;
	
	
	public PIDPath()
	{
		
	}

	//begins the command
	  public void initialize() {
		  
	  }
	
	

	@Override
	protected boolean isFinished() {
		
		return false;
	}
}
