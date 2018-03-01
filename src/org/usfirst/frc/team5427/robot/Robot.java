/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import org.usfirst.frc.team5427.autoCommands.*;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.SameLine;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SameLine
public class Robot extends IterativeRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static DriveTrain driveTrain;
	SpeedController motor_pwm_frontLeft;
	SpeedController motor_pwm_rearLeft;
	SpeedControllerGroup speedcontrollergroup_left;
	SpeedController motor_pwm_frontRight;
	SpeedController motor_pwm_rearRight;
	SpeedControllerGroup speedcontrollergroup_right;
	DifferentialDrive drive;
	DriveWithJoystick dwj;
	Command m_autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static DoubleSolenoid intakeSolenoid;
	public static SpeedController motorPWM_Intake_Left;
	public static SpeedController motorPWM_Intake_Right;
	public static SpeedController motorPWM_Elevator;
	public static Intake intakeSubsystem;
	public static Encoder encLeft;
	public static Encoder encRight;
	private String gameData;
	private int color;
	private int field_position;
	private int switch_or_scale;
	private char switchSide;
	private char scaleSide;
	public AutoPath autoPath;
	public PIDTurn turnToAngle;
	public PIDStraightMovement moveToDistance;
	public PIDStraightMovement pi;
	public static AHRS ahrs;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		motor_pwm_frontLeft = new SteelTalon(Config.FRONT_LEFT_MOTOR);
		motor_pwm_rearLeft = new SteelTalon(Config.REAR_LEFT_MOTOR);
		speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);
		motor_pwm_frontRight = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
		motor_pwm_rearRight = new SteelTalon(Config.REAR_RIGHT_MOTOR);
		speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
		drive = new DifferentialDrive(speedcontrollergroup_left, speedcontrollergroup_right);
		drive.setSafetyEnabled(false);
		driveTrain = new DriveTrain(speedcontrollergroup_left, speedcontrollergroup_right, drive);
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);
		motorPWM_Elevator = new SteelTalon(Config.ELEVATOR_MOTOR);
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}
		encLeft = new Encoder(Config.FRONT_LEFT_MOTOR, Config.REAR_LEFT_MOTOR, false, Encoder.EncodingType.k4X);
		encRight = new Encoder(Config.FRONT_RIGHT_MOTOR, Config.REAR_RIGHT_MOTOR, false, Encoder.EncodingType.k4X);
		// Set the Encoder to diameter*pi/360 inches per pulse (each pulse is a degree)
		encRight.setDistancePerPulse((6 * Math.PI / 360));
		encLeft.setDistancePerPulse((6 * Math.PI / 360));
		oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		encRight.reset();
		encLeft.reset();
		ahrs.reset();
		SmartDashboard.putNumber("encRightVal", encRight.getDistance());
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		SmartDashboard.putNumber("encRight", encRight.getDistance());
		SmartDashboard.putNumber("encLeft", encLeft.getDistance());
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("encRightVal", encRight.getDistance());
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		SmartDashboard.putNumber("encRight", encRight.getDistance());
		SmartDashboard.putNumber("encLeft", encLeft.getDistance());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		encRight.reset();
		encLeft.reset();
		ahrs.reset();
//		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		switchSide = gameData.charAt(0);
//		scaleSide = gameData.charAt(1);
//		field_position = oi.autoPositionChooser.getSelected();
//		switch_or_scale = oi.autoCubeChooser.getSelected();
//		if (field_position == 1) {
//			if (switch_or_scale == 1) {
//				if (switchSide == 'R')
//					autoPath = new Right_SwitchIsRight();
//				else if (switchSide == 'L')
//					autoPath = new Right_SwitchIsLeft();
//			}
//			else if (switch_or_scale == 2) {
//				if (scaleSide == 'R')
//					autoPath = new Right_ScaleIsRight();
//				else if (scaleSide == 'L')
//					autoPath = new Right_ScaleIsLeft();
//			}
//		}
//		else if (field_position == 2) {
//			if (switchSide == 'R')
//				autoPath = new Center_SwitchIsRight();
//			else if (switchSide == 'L')
//				autoPath = new Center_SwitchIsLeft();
//		}
//		else if (field_position == 3) {
//			if (switch_or_scale == 1) {
//				if (switchSide == 'R')
//					autoPath = new Left_SwitchIsRight();
//				else if (switchSide == 'L')
//					autoPath = new Left_SwitchIsLeft();
//			}
//			else if (switch_or_scale == 2) {
//				if (scaleSide == 'R')
//					autoPath = new Left_ScaleIsRight();
//				else if (scaleSide == 'L')
//					autoPath = new Left_ScaleIsLeft();
//			}
//		}
//		
//		autoPath.start();
		
		// Tested and fully functional (within 15 seconds):
		// Center_SwitchIsLeft, Center_SwitchIsRight, Left_SwitchIsLeft, Left_ScaleIsLeft, Right_SwitchIsRight, 
		// Right_ScaleIsRight, Left_SwitchIsRight, Right_SwitchIsLeft, 
		
		// Not fully functional:
		// Left_SwitchIsRight- Takes too long
		// 

		autoPath = new Left_ScaleIsRight();
		autoPath.start();
		
//		moveToDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 245,
//				0.0208, 0.0, 0.026);
//		moveToDistance.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		encRight.reset();
		encLeft.reset();
		dwj = new DriveWithJoystick();
		dwj.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
