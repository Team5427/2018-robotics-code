package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.RobotMap;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.util.Log;
<<<<<<< HEAD
import org.usfirst.frc.team5427.util.SameLine;
=======
>>>>>>> 94e8af4f05a4c8b429ccd908753676d73dff71e0

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This file controls the drive train This file will use the same line method
 * 
 * @author Akshat
 */
@SameLine
public class DriveTrain extends Subsystem {
<<<<<<< HEAD

	public DifferentialDrive drive;
	public SpeedControllerGroup drive_Left;
	public SpeedControllerGroup drive_Right;

	public DriveTrain(SpeedControllerGroup drive_Left, SpeedControllerGroup drive_Right, DifferentialDrive drive) {
=======
	
	private DifferentialDrive drive;
	private SpeedController drive_Left;
	private SpeedController drive_Right;
	public DriveTrain(SpeedController drive_Left, SpeedController drive_Right ,DifferentialDrive drive){
>>>>>>> 94e8af4f05a4c8b429ccd908753676d73dff71e0
		Log.info("DriveTrain made");
		this.drive = drive;
		this.drive_Left = drive_Left;
		this.drive_Right = drive_Right;
	}
<<<<<<< HEAD

	@Override
=======
		
	   	@Override
>>>>>>> 94e8af4f05a4c8b429ccd908753676d73dff71e0
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveWithJoystick());
	}

	public void takeJoystickInputs(Joystick joy) {

		// double speed = Math.abs(joy.getY()) > 0.05 ? joy.getY() : 0f;
		Log.info("drive instruction recieved");
<<<<<<< HEAD
		drive.arcadeDrive(-joy.getY(), joy.getZ());
=======
		drive.arcadeDrive(-joy.getY(),joy.getZ());
>>>>>>> 94e8af4f05a4c8b429ccd908753676d73dff71e0
		// frontLeftMotor.set(speed);
		// frontRightMotor.set(speed);
		// //rearLeftMotor.set(speed);
		// rearRightMotor.set(speed);

	}

	public void stop() {
<<<<<<< HEAD
=======
		Log.info("stopped motor");
>>>>>>> 94e8af4f05a4c8b429ccd908753676d73dff71e0
		drive.stopMotor();
	}
}
