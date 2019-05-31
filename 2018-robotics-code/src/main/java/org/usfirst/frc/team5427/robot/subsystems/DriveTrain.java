package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This subsystem controls the movements of the drive train.
 * 
 * @author Akshat Jain
 */
@SameLine
public class DriveTrain extends Subsystem {

	/**
	 * The drive train subsystem we implement from WPILib.
	 */
	public DifferentialDrive drive;

	/**
	 * The SpeedControllers on the left side of the robot's drive train.
	 */
	public SpeedControllerGroup drive_Left;

	/**
	 * The SpeedControllers on the right side of the robot's drive train.
	 */
	public SpeedControllerGroup drive_Right;

	/**
	 * Assigns each of the components of the drive train based off of the
	 * subsystem's parameters.
	 * 
	 * @param drive_Left
	 *            the SpeedControllers on the left side of the robot's drive train.
	 * @param drive_Right
	 *            the SpeedControllers on the right side of the robot's drive train.
	 * @param drive
	 *            the Drive Train that is created inside of Robot.java.
	 */
	public DriveTrain(SpeedControllerGroup drive_Left, SpeedControllerGroup drive_Right, DifferentialDrive drive) {

		this.drive = drive;
		this.drive_Left = drive_Left;
		this.drive_Right = drive_Right;
	}

	/**
	 * Initializes the DriveWithJoystick command.
	 */
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	/**
	 * Receives information from the joy stick and uses it to control the robot's
	 * speed and direction
	 * 
	 * @param joy
	 *            the joystick we utilize to drive the robot.
	 */
	public void takeJoystickInputs(Joystick joy) {
		double speed = Math.abs(joy.getY()) > 0.05 ? joy.getY() : 0f;
		drive.arcadeDrive(-joy.getY(), joy.getZ() * .75);

	}

	/**
	 * Stops the robot's motors.
	 */
	public void stop() {
		drive.stopMotor();
	}
}
