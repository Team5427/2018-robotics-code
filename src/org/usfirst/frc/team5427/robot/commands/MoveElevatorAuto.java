/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

/**
 * @author Kipp
 * This command will be called in autonomous to set the elevator to the height of the switch or scale,
 * depending on which value we send it.
 */
public class MoveElevatorAuto extends Command {
	// Height = 1: Switch. Height = 2: Scale.
	public static int height;
	Timer timer;
	
	public MoveElevatorAuto(int height) {
		this.height = height;
		timer = new Timer();
		//requires(Robot.kExampleSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(height == 1) {
			Robot.motorPWM_Elevator.set(.3);
		}
		else if(height == 2) {
			Robot.motorPWM_Elevator.set(.3);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// TODO change Config values for time
		if((height == 1 && timer.get() > Config.ELEVATOR_TIME_SWITCH) || (height == 2 && timer.get() > Config.ELEVATOR_TIME_SCALE))
			return true;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motorPWM_Elevator.disable();
		timer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}