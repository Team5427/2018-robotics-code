package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.SameLine;

//RobotBuilder Version: 2.0
//
//This file was generated by RobotBuilder. It contains sections of
//code that are automatically generated and assigned by robotbuilder.
//These sections will be updated in the future when you export to
//Java from RobotBuilder. Do not put any code or make any change in
//the blocks indicating autogenerated code or it will be lost on an
//update. Deleting the comments indicating the section will prevent
//it from being updated in the future.

import edu.wpi.first.wpilibj.command.Command;

/**
 * This file executes the driving with the joystick. It makes use of the
 * drivetrain which is its subsystem This file will use the same line method
 * 
 * @author Akshat
 */

@SameLine
public class DriveWithJoystick extends Command {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public DriveWithJoystick() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
		requires(Robot.driveTrain);

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

 // Called repeatedly when this Command is scheduled to run
 protected void execute() {

 	Robot.driveTrain.takeJoystickInputs(Robot.oi.getJoy()); //TODO check this
 }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}


 // Called once after isFinished returns true
 protected void end() {
 	Robot.driveTrain.stop();


 }

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}