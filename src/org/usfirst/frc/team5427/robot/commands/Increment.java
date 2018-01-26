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
 * This command is meant to ramp up the power in the driveTrain to a desired power value
 */
public class Increment extends Command {
	
	//the desired power value
	private double desiredPower;
	
	public Increment(double desiredPower) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
		
		this.desiredPower = desiredPower;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		//increments the speed of the robot by a set value
		Robot.driveTrain.drive_Left.set(Robot.driveTrain.drive_Left.get()+Config.DRIVE_SPEED_INCREMENT_VALUE);
		Robot.driveTrain.drive_Right.set(Robot.driveTrain.drive_Right.get()+Config.DRIVE_SPEED_INCREMENT_VALUE);
		//space of time between increments
		try {
			this.wait((long) Config.DRIVE_INCREMENT_WAIT_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		//TODO see if want to use right or left motor controller
		return Robot.driveTrain.drive_Left.get() >= desiredPower;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
