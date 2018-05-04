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
import org.usfirst.frc.team5427.util.SameLine;

/**
 * This command activates the motor of the elevator in order to move it down and
 * bypasses any restrictions of the limit switch present at the bottom of its
 * path. Meant to be used when the limit switch is not functioning properly in
 * order to continue to use the elevator.
 * 
 * @author Akshat Jain
 */
@SameLine
public class ManualMoveElevatorDown extends Command {

	/**
	 * The command, ManualMoveElevatorDown, does not require a subsystem.
	 */
	public ManualMoveElevatorDown() {}

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
	 * elevator to the config value for its downward speed and is not restricted by
	 * the limit switches.
	 */
	@Override
	protected void execute() {
		Robot.elevator_SpeedControllerGroup.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
	}

	/**
	 * Called periodically while the command is running.
	 * 
	 * @return if the button is released
	 */
	@Override
	public boolean isFinished() {
		if (Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_ELEVATOR_DOWN_MANUAL))
			return true;
		return false;
	}

	/**
	 * Called once when the isFinished method returns true. Sets the elevator motor
	 * to 0 power in order to stop it from moving.
	 */
	@Override
	protected void end() {
		Robot.elevator_SpeedControllerGroup.set(0);
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
