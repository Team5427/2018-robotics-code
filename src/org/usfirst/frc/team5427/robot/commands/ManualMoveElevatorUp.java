/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command activates the motor of the elevator in order to move it up and
 * bypasses any restrictions of the limit switch present at the top of its path.
 * Meant to be used when the limit switch is not functioning properly in order
 * to continue to use the elevator.
 * 
 * @author Akshat Jain
 */
@SameLine
public class ManualMoveElevatorUp extends Command {

	/**
	 * The command, ManualMoveElevatorUp, does not require a subsystem.
	 */
	public ManualMoveElevatorUp() {}

	/**
	 * Called once when the command is started. Signifies that this command is able
	 * to be interrupted by others.
	 */
	@Override
	protected void initialize() {
		this.setInterruptible(true);
	}

	/**
	 * Called periodically while the command is running. Sets the speed of the
	 * elevator to the config value for its upward speed and is not restricted by
	 * the limit switches.
	 */
	@Override
	protected void execute() {
		Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_UP);
		Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_UP);
	}

	/**
	 * Called periodically while the command is running.
	 * 
	 * @return if the button is released
	 */
	@Override
	public boolean isFinished() {
		if (Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_ELEVATOR_UP))
			return true;
		return false;
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
		end();
	}
}
