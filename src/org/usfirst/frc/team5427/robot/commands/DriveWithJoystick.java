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
 * Utilizes the joystick inputs in order to drive the robot.
 * 
 * @author Varsha Kumar
 */
@SameLine
public class DriveWithJoystick extends Command {

	/**
	 * DriveWithJoystick requires the drive train subsystem.
	 */
	public DriveWithJoystick() {
		requires(Robot.driveTrain);
	}

	/**
	 * Called once when the command is started but is not used for anything.
	 */
	protected void initialize() {}

	/**
	 * Called periodically while the command is not finished. Delivers the joystick
	 * inputs into the drive train subsystem in order to drive the robot.
	 */
	protected void execute() {
		Robot.driveTrain.takeJoystickInputs(Robot.oi.getJoy());
	}

	/**
	 * Called periodically while the command is running to check when the command is
	 * finished.
	 * 
	 * @return false
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Called once when the command is finished. Sets the velocity of the drive
	 * train to 0 power.
	 */
	protected void end() {
		Robot.driveTrain.stop();
	}

	/**
	 * Called once if the command is interrupted. Calls the end method in response.
	 */
	protected void interrupted() {
		end();
	}
}
