/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will be called in autonomous to set the elevator to the height
 * of the switch or scale, depending on which value we send it.
 * 
 * @author Kipp Corman
 */
public class MoveElevatorAuto extends Command {

	/**
	 * The desired position for the elevator. 1: Middle position for placement on
	 * the switch. 2: Top position for placement on the scale. 3: Bottom position
	 * for resetting the elevator.
	 */
	public int height;

	/**
	 * Stores whether the elevator has reached its desired position.
	 */
	boolean maxHeightReached = false;

	/**
	 * Sets the timeout for the command based off of the desired position of the
	 * elevator and how long it takes to get there.
	 * 
	 * @param height
	 *            the desired position for the elevator.
	 */
	public MoveElevatorAuto(int height) {
		this.height = height;

		if (1 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SWITCH);
		else if (2 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SCALE);
		else if (3 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SCALE_DOWN);
		else if (4 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SWITCH_DOWN);
		else if(5 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SWITCH_SECONDCUBE_DOWN);
		
	}

	/**
	 * Called once when the command is started. Signifies that this iteration of the
	 * command has not reached its desired position yet.
	 */
	@Override
	protected void initialize() {
		maxHeightReached = false;
	}

	/**
	 * Called periodically while the command is running. Sets the speed of the
	 * elevator in relation to the desired direction of movement unless the elevator
	 * has run into a limit switch.
	 */
	@Override
	protected void execute() {
		
		if (1 == height || 2 == height) {
			if (!Robot.elevatorLimitSwitchUp.get()) {
				Robot.motorPWM_Elevator_Right.set(0);
				Robot.motorPWM_Elevator_Left.set(0);
			}
			else {
				Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_UP);
				Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_UP);
			}
		}
		else if (3 == height || 4 == height || 5 == height) {
			if (!Robot.elevatorLimitSwitchDown.get()) {
				Robot.motorPWM_Elevator_Right.set(0);
				Robot.motorPWM_Elevator_Left.set(0);
			}
			else {
				
				Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
				Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_DOWN);
			}
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
	 * @return if the bottom limit switch has been activated.
	 */
	public boolean minHeightReached() {
		return !Robot.elevatorLimitSwitchDown.get();
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
		if (2 == height)
			return false;
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