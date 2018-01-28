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

/**
 * This command is meant to ramp up the power in the driveTrain, then call PIDDriveTrainSide for
 * a fixed amount of time/distance, based on encoder values for distance for coasting.
 * 
 * Structure: 
 * 1) Ramp up power using Increment command. 
 * 2) Run PIDDriveTrainSide UNTIL 3) FinalDistance-CoastDistance is reached
 */
public class MoveForwardCommand extends Command {
	
	//desired power that the robot will drive at
	private double desiredPower; 
	//desired distance that the robot should travel, in INCHES!!!
	private double desiredDistance; 
	//stores the command for driving straight
	PIDDriveTrainSide pidDTS;
	
	public MoveForwardCommand(double desiredPower) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		this.desiredPower = desiredPower;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//TODO reset encoder value
		new Increment(desiredPower);
		pidDTS = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, 0,40);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
//		if(/*robot encoder value is larger than desiredDistance - Config.getCoastDistance()*/)
		if(0>=Config.getCoastingDistance(desiredPower))//TODO Create Encoders in Robot and use their value here
			return true;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		pidDTS.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
