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
 * This command will be called in autonomous to set the elevator to the height
 * of the switch or scale, depending on which value we send it.
 * 
 * @author Akshat Jain 
 */
public class MoveElevatorFull extends Command {
	public static boolean up = true;

	public MoveElevatorFull(boolean up) {
		this.up = up;
	}

	@Override
	protected void initialize() {
		if (this.up) {
			up = false;
		}
		else {
			up = true;
		}
	}

	@Override
	protected void execute() {
		if (this.up) {
			Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_UP);
			Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_UP);
		}
		else {
			Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
			Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_DOWN);
		}
	}

	public boolean maxHeightReached() {
		return isTimedOut();
	}

	@Override
	public boolean isFinished() {
		if (this.up) {
			if (!Robot.elevatorLimitSwitchUp.get()) {
				Robot.motorPWM_Elevator_Right.set(0);
				Robot.motorPWM_Elevator_Left.set(0);
				return true;
			}
			return false;
		}
		else {
			if (!Robot.elevatorLimitSwitchDown.get())
				return true;
			return false;
		}

	}

	@Override
	protected void end() {
		Robot.motorPWM_Elevator_Right.set(0);
		Robot.motorPWM_Elevator_Left.set(0);	}

	@Override
	protected void interrupted() {
		Robot.motorPWM_Elevator_Right.set(0);
		Robot.motorPWM_Elevator_Left.set(0);		end();
	}
}