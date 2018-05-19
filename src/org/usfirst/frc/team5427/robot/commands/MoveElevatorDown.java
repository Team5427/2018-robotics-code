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
 * This command moves the elevator downwards along its path and stops whenever the lower limit switch is activated.
 * @author Blake Romero Command
 */

@SameLine
public class MoveElevatorDown extends Command {
	/**
	 * Makes MoveElevatorDown object.
	 * 
	 * Moves the elevator down until button has stop being pressed Or lower limit
	 * switch is pressed.
	 */
	public MoveElevatorDown() {}

	/*
	 * Sets the elevator motor to .5 speed down.
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#initialize()
	 */
	@Override
	protected void initialize() {
		this.setInterruptible(true);
		Robot.motorPWM_Elevator_Right.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
		Robot.motorPWM_Elevator_Left.set(-Config.ELEVATOR_MOTOR_SPEED_DOWN);
	}

	/**
	 * Scheduler continually calls this method. Does not do anything.
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#execute()
	 */
	@Override
	protected void execute() {}

	/**
	 * If lower limit switch is true, the robot is at its lowest height. Method
	 * returns true and calls end()
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return (Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_ELEVATOR_DOWN) || !Robot.elevatorLimitSwitchDown.get());
	}

	/**
	 * Stops the motion of the Elevator. When isFinished returns true Or command is
	 * interrupted.
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#end()
	 */
	@Override
	protected void end() {
		Robot.motorPWM_Elevator_Right.set(0);
		Robot.motorPWM_Elevator_Left.set(0);
	}

	/**
	 * Ends command when command is interrupted
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#interrupted()
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
