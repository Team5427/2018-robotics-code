/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;

/**
 * @author Blake This command
 */

@NextLine
public class ManualMoveElevatorUp extends Command {

	public ManualMoveElevatorUp() {
//		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		x=0;
//		Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
		this.setInterruptible(true);
	}
	int x =0;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.motorPWM_Elevator.set(Config.ELEVATOR_MOTOR_SPEED_UP);
		SmartDashboard.putNumber("x", ++x);
//		if(isFinished())
//			SmartDashboard.putNumber("a", 1 );
//		else
//			SmartDashboard.putNumber("a", 0 );

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		
		
//		if(Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_ELEVATOR_UP))
//		{
//			SmartDashboard.putNumber("x", 55);
//			return true;
//		}
		if(!Robot.elevatorLimitSwitchUp.get())
		{
			SmartDashboard.putNumber("x", 99);	
			return true;

		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motorPWM_Elevator.set(0);
//		Robot.elevatorLimitSwitchUp.free();
//		Robot.elevatorLimitSwitchUp = new DigitalInput(Config.ELEVATOR_LIMIT_SWITCH_UP);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
