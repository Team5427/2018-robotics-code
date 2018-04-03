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
 * @author Kipp
 * This command will be called in autonomous to set the elevator to the height of the switch or scale,
 * depending on which value we send it.
 */
public class MoveElevatorAuto extends Command {
	// Height = 1: Switch. Height = 2: Scale.
	public static int height;
	Timer timer;
	boolean maxHeightReached = false;
	
	public MoveElevatorAuto(int height) {
		this.height = height;
//		timer = new Timer();
		if(1==height)
			this.setTimeout(Config.ELEVATOR_TIME_SWITCH);
		else if(2==height)
			this.setTimeout(Config.ELEVATOR_TIME_SCALE);
		else if(3 == height)
			this.setTimeout(Config.ELEVATOR_TIME_SCALE_DOWN);
		
		//requires(Robot.kExampleSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		maxHeightReached = false;
		SmartDashboard.putNumber("THE HEIGHT IS", this.height);
		SmartDashboard.putBoolean("THE TIMEOUT IS", this.isTimedOut());
//		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putBoolean("THE TIMEOUT IS", this.isTimedOut());

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
//		if(!Robot.elevatorLimitSwitchUp.get())
//			maxHeightReached = false;
		return !Robot.elevatorLimitSwitchUp.get();
	}
	
//	public boolean maxHeightReachedLS()
//	{
//		return !Robot.elevatorLimitSwitchUp.get();
//	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		// TODO change Config values for time
//		if((height == 1 && timer.get() > Config.ELEVATOR_TIME_SWITCH) || (height == 2 && timer.get() > Config.ELEVATOR_TIME_SCALE))
//			return true;
//		return false;
//		if(!Robot.elevatorLimitSwitchUp.get())
//			Robot.motorPWM_Elevator.set(0);
//		else {
//			Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
//		}
		if(2==height)
			return false;
		return isTimedOut();
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