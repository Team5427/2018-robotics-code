/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

/**
 * @author Kipp Corman Command
 * @author Akshat Jain Commenting
 * This command will be called in autonomous to set the elevator to the height of the switch or scale,
 * depending on which value we send it.
 */
public class MoveElevatorAuto extends Command {
	
	public static int height;
	Timer timer;
	boolean maxHeightReached = false;
	
	public MoveElevatorAuto(int height) {
		this.height = height;

		if(1==height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SWITCH);
		else if(2==height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SCALE);
		else if(3 == height)
			this.setTimeout(Config.ELEVATOR_TIMEOUT_SCALE_DOWN);
		
		
	}

	@Override
	protected void initialize() {
		maxHeightReached = false;
	}

	
	@Override
	protected void execute() {
		if(1 == height || 2 == height)
		{
			if(!Robot.elevatorLimitSwitchUp.get()) {
				Robot.motorPWM_Elevator.set(0);
			}
			else {
				Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
			}
		}
		else if(3 == height)
		{
			if(!Robot.elevatorLimitSwitchDown.get())
			{
				Robot.motorPWM_Elevator.set(0);
			}
			else
			{
				Robot.motorPWM_Elevator.set(-Config.ELEVATOR_MOTOR_SPEED_DOWN);
			}
		}
	}
	
	public boolean maxHeightReachedTime()
	{
		return isTimedOut();
	}
	
	public boolean maxHeightReached()
	{
		return !Robot.elevatorLimitSwitchUp.get();
	}
	
	public boolean minHeightReached()
	{
		return !Robot.elevatorLimitSwitchDown.get();
	}
	
	@Override
	public boolean isFinished() {
		if(2==height)
			return false;
		return isTimedOut();
	}

	@Override
	protected void end() {
		Robot.motorPWM_Elevator.set(0);
	}

	@Override
	protected void interrupted() {
		Robot.motorPWM_Elevator.set(0);
		end();
	}
}