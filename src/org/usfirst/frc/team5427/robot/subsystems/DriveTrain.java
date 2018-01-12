package org.usfirst.frc.team5427.robot.subsystems;



import org.usfirst.frc.team5427.robot.RobotMap;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * This file controls the drive train
 * This file will use the same line method
 * 
 * @author Akshat
 */

public class DriveTrain extends Subsystem {
		
	    Talon motor_pwm_frontLeft = new Talon(RobotMap.frontleftValue);
	    Talon motor_pwm_rearLeft = new Talon(RobotMap.rearleftValue);
	    SpeedControllerGroup m_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);

	    Talon motor_pwm_frontRight = new Talon(RobotMap.frontrightValue);
	    Talon motor_pwm_rearRight = new Talon(RobotMap.rearrightValue);
	    SpeedControllerGroup m_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);

	    DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
	    
	    //Change config channel A and B values when we figure out what it means
	    Encoder frontRightStraight = new Encoder(Config.CHANNEL_A_RIGHT, Config.CHANNEL_B_RIGHT);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveWithJoystick());
	}
	public void takeJoystickInputs(Joystick joy) {

		// double speed = Math.abs(joy.getY()) > 0.05 ? joy.getY() : 0f;

		m_drive.arcadeDrive(joy.getX(),joy.getZ());
		// frontLeftMotor.set(speed);
		// frontRightMotor.set(speed);
		// //rearLeftMotor.set(speed);
		// rearRightMotor.set(speed);

	}
	public SpeedControllerGroup getM_left() {
		return m_left;
	}
	public void setM_left(SpeedControllerGroup m_left) {
		this.m_left = m_left;
	}
	public SpeedControllerGroup getM_right() {
		return m_right;
	}
	public void setM_right(SpeedControllerGroup m_right) {
		this.m_right = m_right;
	}
	public void stop() {
		m_drive.stopMotor();
	}
}
