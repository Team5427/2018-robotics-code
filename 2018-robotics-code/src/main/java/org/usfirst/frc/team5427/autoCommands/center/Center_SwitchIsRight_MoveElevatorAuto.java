/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.autoCommands.center;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will be called in autonomous to set the elevator to the height
 * of the switch or scale, depending on which value we send it.
 * 
 * @author Akshat Jain
 */
public class Center_SwitchIsRight_MoveElevatorAuto extends Command {
	/**
	 * Sets the timeout for the command based off of the desired position of the
	 * elevator and how long it takes to get there.
	 * 
	 * @param height
	 *            the desired position for the elevator.
	 */
	public Center_SwitchIsRight_MoveElevatorAuto() {
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SWITCH);
	}

	/**
	 * Called once when the command is started. Signifies that this iteration of the
	 * command has not reached its desired position yet.
	 */
	@Override
	protected void initialize() {
	}

	/**
	 * Called periodically while the command is running. Sets the speed of the
	 * elevator in relation to the desired direction of movement unless the elevator
	 * has run into a limit switch.
	 */
	@Override
	protected void execute() {
			if (!Robot.elevatorLimitSwitchUp.get()) {
				Robot.motorPWM_Elevator_Right.set(0);	
				Robot.motorPWM_Elevator_Left.set(0);
			}
			else {
				Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_UP);
				Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_UP);
			}
	}

	/**
	 * @return if the command has timed out.
	 */
	public boolean maxHeightReachedTime() {
		return isTimedOut();
	}

	/**
	 * @return if the top limit switch has been activated.
	 */
	public boolean maxHeightReached() {
		return !Robot.elevatorLimitSwitchUp.get();
	}


	/**
	 * Called periodically while the command is running to check when it is
	 * finished.
	 * 
	 * @return if the command has timed out, unless it is going to its highest
	 *         position.
	 */
	@Override
	public boolean isFinished() {
		return isTimedOut();
	}

	/**
	 * Called once when the isFinished method returns true. Sets the elevator motor
	 * to 0 power in order to stop it from moving.
	 */
	@Override
	protected void end() {
		Robot.motorPWM_Elevator_Right.set(0);
		Robot.motorPWM_Elevator_Left.set(0);
	}

	/**
	 * Called once when the command is interrupted. Calls the end method in order to
	 * stop the elevator motor from moving.
	 */
	@Override
	protected void interrupted() {
		Robot.motorPWM_Elevator_Right.set(0);
		Robot.motorPWM_Elevator_Left.set(0);
		end();
	}
}