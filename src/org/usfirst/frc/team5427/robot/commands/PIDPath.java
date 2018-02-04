package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class PIDPath extends Command
{
	private PIDDriveTrainSide firstDistance, secondDistance, thirdDistance;
	private PIDTurnCommand firstAngle, secondAngle;
	
	
	public PIDPath()
	{
		firstDistance = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0, 160);
//		firstAngle = new PIDTurnCommand(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, double p, double i, double d, 25);
	}

	//begins the command
	 public void initialize() {
		  
	  
	 }
	
	public void execute()
	{
		
	}

	@Override
	protected boolean isFinished() {
		
		return false;
	}
}
