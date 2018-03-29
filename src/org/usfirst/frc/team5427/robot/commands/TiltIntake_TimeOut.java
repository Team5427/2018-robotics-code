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
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;

/**
 * @author Akshat This command
 */

@NextLine
public class TiltIntake_TimeOut extends Command {
	
	private double tilt_time_out;
	
	public TiltIntake_TimeOut() {
		tilt_time_out = Config.TILT_TIMEOUT;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		setTimeout(tilt_time_out);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(Robot.tiltUp) {
			Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_UP);
		}
		else {
			Robot.motorPWM_TiltIntake.set(Config.INTAKE_TILTER_MOTOR_SPEED_DOWN);
		}
//		Robot.motorPWM_Intake_Left.set(Config.INTAKE_TILTER_MOTOR_SPEED_UP);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
//			return Robot.oi.getJoy().getRawButtonReleased(Config.BUTTON_INTAKE_TILTER_UP);
		
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motorPWM_TiltIntake.set(0);
		Robot.tiltUp=!Robot.tiltUp;
//		Robot.motorPWM_Intake_Left.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
