package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.SameLine;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This file controls the drive train This file will use the same line method
 * 
 * @author Akshat
 */
@SameLine
public class DriveTrain extends Subsystem {

	public DifferentialDrive drive;
	public SpeedControllerGroup drive_Left;
	public SpeedControllerGroup drive_Right;

	public DriveTrain(SpeedControllerGroup drive_Left, SpeedControllerGroup drive_Right, DifferentialDrive drive) {

		this.drive = drive;
		this.drive_Left = drive_Left;
		this.drive_Right = drive_Right;
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
//		setDefaultCommand(new DriveWithJoystick());
	}

	public void takeJoystickInputs(Joystick joy) {
		
		// double speed = Math.abs(joy.getY()) > 0.05 ? joy.getY() : 0f;

		drive.arcadeDrive(-joy.getY(), joy.getZ());

		
		//TODO use these to sort of disable some of the motors
//		Robot.motor_pwm_frontLeft.set(0);
//		Robot.motor_pwm_rearLeft.set(0);
//		Robot.motor_pwm_frontRight.set(0);
//		Robot.motor_pwm_rearRight.set(0);
		

	}
	

	public void stop() {

		drive.stopMotor();
	}
}
