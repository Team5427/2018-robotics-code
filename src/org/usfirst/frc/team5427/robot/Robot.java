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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5427.robot.OI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import org.usfirst.frc.team5427.autoCommands.*;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorDown;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorFull;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorUp;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.Log;
//import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.SameLine;
import com.kauailabs.navx.frc.AHRS;

/**
 * Robot is used as the core of the robot's code, and it contains all of the
 * subsystems of the robot and their core functions.
 */
@SameLine
public class Robot extends IterativeRobot {
	/**
	 * OI contains all of the controls that are used on the robot & the commands and
	 * command groups that are linked to them.
	 */
	public static OI oi;

	/**
	 * DriveTrain contains the SpeedControllers that control the left and right
	 * sides of the drive train on the robot in order to move.
	 */
	public static DriveTrain driveTrain;

	/**
	 * The SpeedController that controls the front left motor of the drivetrain.
	 */
	public static SpeedController motor_pwm_frontLeft;

	/**
	 * The SpeedController that controls the rear left motor of the drivetrain.
	 */
	public static SpeedController motor_pwm_rearLeft;

	/**
	 * The SpeedControllerGroup that includes the left side of the drivetrain.
	 */
	SpeedControllerGroup speedcontrollergroup_left;

	/**
	 * The SpeedController that controls the front right motor of the drive train.
	 */
	public static SpeedController motor_pwm_frontRight;

	/**
	 * The SpeedController that controls the rear right motor of the drive train.
	 */
	public static SpeedController motor_pwm_rearRight;

	/**
	 * The SpeedControllerGroup that includes the right side of the drive train.
	 */
	SpeedControllerGroup speedcontrollergroup_right;

	/**
	 * The command that controls the drive train and its movement.
	 */
	DifferentialDrive drive;

	/**
	 * The command that uses joystick inputs to manipulate the drive train and other
	 * subsystems.
	 */
	DriveWithJoystick dwj;

	/**
	 * The SpeedController that controls the left wheel of the intake.
	 */
	public static SpeedController motorPWM_Intake_Left;

	/**
	 * The SpeedController that controls the right wheel of the intake.
	 */
	public static SpeedController motorPWM_Intake_Right;

	/**
	 * The SpeedController that controls the left motor of the elevator.
	 */
	public static SpeedController motorPWM_Elevator_Left;
	
	/**
	 * The SpeedController that controls the right motor of the elevator.
	 */
	public static SpeedController motorPWM_Elevator_Right;
	
	/**
	 * The SpeedControllerGroup that includes the motors of the elevator.
	 */
	public static SpeedControllerGroup elevator_SpeedControllerGroup;

	/**
	 * The SpeedController that controls the arm of the climber.
	 */
	public static SpeedController motorPWM_ClimberArm;

	/**
	 * The SpeedController that controls the motor of the climber.
	 */
	public static SpeedController motorPWM_Climber;

	/**
	 * The SpeedController that controls the tilting of the intake.
	 */
	public static SpeedController motorPWM_TiltIntake;

	/**
	 * The subsystem containing the intake SpeedControllers that controls the
	 * movement of the intake.
	 */
	public static Intake intakeSubsystem;

	/**
	 * The encoder that is attached to the left side of the drivetrain used to track
	 * the distance the robot travels.
	 */
	public static Encoder encLeft;

	/**
	 * The String that includes the data sent to us from the FMS before autonomous.
	 */
	private String gameData;

	/**
	 * The field position we select on the SmartDashboard.
	 */
	private int field_position;

	/**
	 * The desired placement of the cube we select on the SmartDashboard.
	 */
	private int switch_or_scale;

	/**
	 * The side of our switch we can control.
	 */
	private char switchSide;

	/**
	 * The side of the scale we can control.
	 */
	private char scaleSide;

	/**
	 * The path that we choose to follow in autonomous, selected based off of our
	 * selections from the SmartDashboard and the gameData sent via the FMS.
	 */
	public AutoPath autoPath;

	/**
	 * The class representing the NavX on the Robot that reads our current angular
	 * placement.
	 */
	public static AHRS ahrs;

	/**
	 * The Limit Switch that detects when the Elevator has reached the top of its
	 * path.
	 */
	public static DigitalInput elevatorLimitSwitchUp;

	/**
	 * The Limit Switch that detects when the Elevator has reached the bottom of its
	 * path.
	 */
	public static DigitalInput elevatorLimitSwitchDown;

	/**
	 * The command that moves the Elevator up.
	 */
	public static MoveElevatorUp mou = new MoveElevatorUp();

	/**
	 * The command that moves the Elevator down.
	 */
	public static MoveElevatorDown mod = new MoveElevatorDown();

	/**
	 * The server used to send camera data from the RoboRio to the driver station.
	 */
	public static CameraServer camServer;

	/**
	 * The USB Camera attached to the robot for visibility.
	 */
	public static UsbCamera usbCam;

	/**
	 * Used to determine if we need to tilt the intake up next or not.
	 */
	public static boolean tiltUpNext = true;

	/**
	 * Initializes all of the SpeedControllers, Subsystems, and other Objects within
	 * the core of the code.
	 */
	@Override
	public void robotInit() {
		elevatorLimitSwitchUp = new DigitalInput(Config.ELEVATOR_LIMIT_SWITCH_UP);
		elevatorLimitSwitchUp.setSubsystem("ELSU");
		elevatorLimitSwitchDown = new DigitalInput(Config.ELEVATOR_LIMIT_SWITCH_DOWN);
		motor_pwm_frontLeft = new PWMVictorSPX(Config.FRONT_LEFT_MOTOR);
		motor_pwm_rearLeft = new PWMVictorSPX(Config.REAR_LEFT_MOTOR);
		speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);
		motor_pwm_frontRight = new PWMVictorSPX(Config.FRONT_RIGHT_MOTOR);
		motor_pwm_rearRight = new PWMVictorSPX(Config.REAR_RIGHT_MOTOR);
		speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
		drive = new DifferentialDrive(speedcontrollergroup_left, speedcontrollergroup_right);
		drive.setSafetyEnabled(false);
		driveTrain = new DriveTrain(speedcontrollergroup_left, speedcontrollergroup_right, drive);
		motorPWM_Intake_Left = new PWMVictorSPX(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new PWMVictorSPX(Config.INTAKE_MOTOR_RIGHT);
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);
		motorPWM_Elevator_Left = new PWMVictorSPX(Config.ELEVATOR_MOTOR_LEFT);
		motorPWM_Elevator_Right = new PWMVictorSPX(Config.ELEVATOR_MOTOR_RIGHT);
		elevator_SpeedControllerGroup = new SpeedControllerGroup(motorPWM_Elevator_Left, motorPWM_Elevator_Right);
		motorPWM_TiltIntake = new PWMVictorSPX(Config.TILT_INTAKE_MOTOR);
		tiltUpNext = true;
//		motorPWM_ClimberArm = new PWMVictorSPX(Config.CLIMBER_ARM_MOTOR);
//		motorPWM_Climber = new PWMVictorSPX(Config.CLIMBER_MOTOR);
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}
		encLeft = new Encoder(Config.ENCODER_LEFT_CHANNEL_A, Config.ENCODER_LEFT_CHANNEL_B, false, Encoder.EncodingType.k4X);
		encLeft.setDistancePerPulse((6 * Math.PI / 360));

		camServer = CameraServer.getInstance();
		usbCam = new UsbCamera("USB Camera", 0);
		usbCam.setFPS(15);
		camServer.addCamera(usbCam);
		camServer.startAutomaticCapture(usbCam);
		oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		encLeft.reset();
		ahrs.reset();
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		SmartDashboard.putNumber("encLeft", encLeft.getDistance());
	}

	/**
	 * This function is called periodically while the robot is in Disabled mode. You
	 * can use it to maintain zero points of any subsystem information while the
	 * robot is disabled.
	 */
	@Override
	public void disabledPeriodic() {
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		SmartDashboard.putNumber("encLeft", encLeft.getDistance());
	}

	/**
	 * This function is called once at the beginning of the autonomous period. We
	 * use it to reset any values immediately before the start of movement and
	 * obtain the gameData from the FMS. After this, we select and start our
	 * autonomous based on the gameData and selected values from the SmartDashboard.
	 * 
	 * Values: - field_position: - 1 = Right - 2 = Center - 3 = Left -
	 * switch_or_scale - 1 = Switch - 2 = Scale
	 */
	@Override
	public void autonomousInit() {
		Scheduler.getInstance().run();
		tiltUpNext = true;
		encLeft.reset();
		ahrs.reset();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		switchSide = gameData.charAt(0);
		scaleSide = gameData.charAt(1);
		field_position = oi.autoPositionChooser.getSelected();
		switch_or_scale = oi.autoCubeChooser.getSelected();
		SmartDashboard.putString("SwitchSide", switchSide + "");
		SmartDashboard.putString("ScaleSide", scaleSide + "");
		SmartDashboard.putString("FieldPosition", field_position + "");
		SmartDashboard.putString("CubePlacement", switch_or_scale + "");
		if (field_position == 1) {
			if (switch_or_scale == 1) {
				if (switchSide == 'R')
					autoPath = new Right_SwitchIsRight();
				else if (switchSide == 'L')
					autoPath = new Right_SwitchIsLeft();
			}
			else if (switch_or_scale == 2) {
				if (scaleSide == 'R')
					autoPath = new Right_ScaleIsRight();
				else if (scaleSide == 'L')
					autoPath = new Full_Right_ScaleIsLeft();
			}
		}
		else if (field_position == 2) {
			if (switchSide == 'R')
				autoPath = new Center_SwitchIsRight();
			else if (switchSide == 'L')
				autoPath = new Center_SwitchIsLeft();
		}
		else if (field_position == 3) {
			if (switch_or_scale == 1) {
				if (switchSide == 'R')
					autoPath = new Left_SwitchIsRight();
				else if (switchSide == 'L')
					autoPath = new Left_SwitchIsLeft();
			}
			else if (switch_or_scale == 2) {
				if (scaleSide == 'R')
					autoPath = new Left_ScaleIsRight();
				else if (scaleSide == 'L')
					autoPath = new Left_ScaleIsLeft();
			}
		}
		if (autoPath != null)
			autoPath.start();
	}

	/**
	 * This function is called periodically during autonomous. We also use it to
	 * re-select autonomous if it initially failed during autonomousInit.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if (null == autoPath) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			switchSide = gameData.charAt(0);
			scaleSide = gameData.charAt(1);
			field_position = oi.autoPositionChooser.getSelected();
			switch_or_scale = oi.autoCubeChooser.getSelected();
			SmartDashboard.putString("SwitchSide", switchSide + "");
			SmartDashboard.putString("ScaleSide", scaleSide + "");
			SmartDashboard.putString("FieldPosition", field_position + "");
			SmartDashboard.putString("CubePlacement", switch_or_scale + "");
			if (field_position == 1) {
				if (switch_or_scale == 1) {
					if (switchSide == 'R')
						autoPath = new Right_SwitchIsRight();
					else if (switchSide == 'L')
						autoPath = new Right_SwitchIsLeft();
				}
				else if (switch_or_scale == 2) {
					if (scaleSide == 'R')
						autoPath = new Right_ScaleIsRight();
					else if (scaleSide == 'L')
						autoPath = new Right_ScaleIsLeft();
				}
			}
			else if (field_position == 2) {
				if (switchSide == 'R')
					autoPath = new Center_SwitchIsRight();
				else if (switchSide == 'L')
					autoPath = new Center_SwitchIsLeft();
			}
			else if (field_position == 3) {
				if (switch_or_scale == 1) {
					if (switchSide == 'R')
						autoPath = new Left_SwitchIsRight();
					else if (switchSide == 'L')
						autoPath = new Left_SwitchIsLeft();
				}
				else if (switch_or_scale == 2) {
					if (scaleSide == 'R')
						autoPath = new Left_ScaleIsRight();
					else if (scaleSide == 'L')
						autoPath = new Left_ScaleIsLeft();
				}
			}
			if (autoPath != null && !autoPath.isRunning())
				autoPath.start();
		}
	}

	/**
	 * This function is called once at the beginning of the teleoperated period of
	 * the match. We use it to cancel our autonomous.
	 */
	@Override
	public void teleopInit() {
		if (autoPath != null) {
			autoPath.cancel();
		}
	}

	/**
	 * This function is called periodically during the teleoperated period of the
	 * match. We use it to check the elevator's limit switches periodically.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// This needs to be here for limit switches to work!
		elevatorLimitSwitchDown.get();
		elevatorLimitSwitchUp.get();
		mou.isFinished();
		mod.isFinished();
	}
}