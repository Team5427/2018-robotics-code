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
 * @author Akshat
 * This command will be called in autonomous to set the elevator to the height of the switch or scale,
 * depending on which value we send it.
 */
public class MoveElevatorFull extends Command {
	// Height = 1: Switch. Height = 2: Scale.

	public static boolean up = true;
	public MoveElevatorFull(boolean up) {
		this.up=up;
		//requires(Robot.kExampleSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(this.up) {
			up = false;
		}
		else {
			up = true;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(this.up) {
		Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
		} else {
		Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
		}
	}
	
	public boolean maxHeightReached()
	{
		return isTimedOut();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		// TODO change Config values for time
//		if((height == 1 && timer.get() > Config.ELEVATOR_TIME_SWITCH) || (height == 2 && timer.get() > Config.ELEVATOR_TIME_SCALE))
//			return true;
//		return false;
		if(this.up) {
			if(!Robot.elevatorLimitSwitchUp.get()) {
				Robot.motorPWM_Elevator.set(0);
				return true;	
			}
			return false;
		}
		else {
			if(!Robot.elevatorLimitSwitchDown.get())
				return true;
			return false;
		}
//		else {
//			Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
//		}
		
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motorPWM_Elevator.set(0);
//		timer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
//		Robot.motorPWM_Elevator.disable();
		Robot.motorPWM_Elevator.set(0);
		end();
//		timer.reset();
	}
}