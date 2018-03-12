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
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team5427.robot.OI;
import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import org.usfirst.frc.team5427.autoCommands.*;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorDown;
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
	public static SpeedController motor_pwm_frontLeft;
	public static SpeedController motor_pwm_rearLeft;
	SpeedControllerGroup speedcontrollergroup_left;
	public static SpeedController motor_pwm_frontRight;
	public static SpeedController motor_pwm_rearRight;
	SpeedControllerGroup speedcontrollergroup_right;
	DifferentialDrive drive;
	DriveWithJoystick dwj;
	// Command m_autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static DoubleSolenoid intakeSolenoid;
	public static SpeedController motorPWM_Intake_Left;
	public static SpeedController motorPWM_Intake_Right;
	public static SpeedController motorPWM_Elevator;
	// public static SpeedController motorPWM_Climber;
	public static Intake intakeSubsystem;
	public static Encoder encLeft;
	// public static Encoder encRight;
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
	public static DigitalInput elevatorLimitSwitchUp;
	public static DigitalInput elevatorLimitSwitchDown;
	public static MoveElevatorUp mou = new MoveElevatorUp();
	public static MoveElevatorDown mod = new MoveElevatorDown();
	/**
	 * values used for PID loops TODO move these to Config
	 */
	public double pidRightP = .085000;
	public double pidRightI = .008333;
	public double pidRightD = .001042;
	public double pidLeftP;
	public double pidLeftI;
	public double pidLeftD;
	double rotateToAngleRate = 0;
	double rightMotorSpeed = 0;
	double leftMotorSpeed = 0;
	public static CameraServer camServer;
	public static UsbCamera usbCam;
	public static UsbCamera usbCam1;
	public static AxisCamera axisCam;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// driveTrain = new DriveTrain();
		// chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		/*
		 * COMMENTED DUE TO ERRORS TODO ADD PORTS FOR SOLENOID
		 */
		// Log.init("Initializing solenoid");
		// intakeSolenoid = new DoubleSolenoid(Config.PCM_SOLENOID_FORWARD,
		// Config.PCM_SOLENOID_REVERSE);
		// Log.init("Initializing driveTrain: ");
		elevatorLimitSwitchUp = new DigitalInput(Config.ELEVATOR_LIMIT_SWITCH_UP);
		elevatorLimitSwitchUp.setSubsystem("ELSU");
		elevatorLimitSwitchDown = new DigitalInput(Config.ELEVATOR_LIMIT_SWITCH_DOWN);
		// elevatorLimitSwitchUp.free();
		// elevatorLimitSwitchDown.
		motor_pwm_frontLeft = new PWMVictorSPX(Config.FRONT_LEFT_MOTOR);
		motor_pwm_rearLeft = new PWMVictorSPX(Config.REAR_LEFT_MOTOR);
		// motor_pwm_rearLeft
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
		motorPWM_Elevator = new PWMVictorSPX(Config.ELEVATOR_MOTOR);
		// motorPWM_Climber = new SteelTalon(Config.CLIMBER_MOTOR);
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}
		encLeft = new Encoder(Config.ENCODER_LEFT_CHANNEL_A, Config.ENCODER_LEFT_CHANNEL_B, false, Encoder.EncodingType.k4X);
		// encRight = new Encoder(Config.FRONT_RIGHT_MOTOR, Config.REAR_RIGHT_MOTOR,
		// false, Encoder.EncodingType.k4X);
		// // Set the Encoder to diameter*pi/360 inches per pulse (each pulse is a
		// degree)
		// encRight.setDistancePerPulse((6 * Math.PI / 360));
		encLeft.setDistancePerPulse((6 * Math.PI / 360));
		//
		// need info of ports
		// Log.init("Initializing Encoders: ");
		// encoderStraight = new Encoder(0, 0);
		// encRight = new Encoder(0,1,false,Encoder.EncodingType.k4X);
		// encLeft = new Encoder(2,3,false,Encoder.EncodingType.k4X);
		camServer = CameraServer.getInstance();
		usbCam = new UsbCamera("USB Camera", 0);
		usbCam.setFPS(15);
		camServer.addCamera(usbCam);
		camServer.startAutomaticCapture(usbCam);
		usbCam1 = new UsbCamera("USB Camera", 1);
		usbCam1.setFPS(15);
		camServer.addCamera(usbCam1);
		camServer.startAutomaticCapture(usbCam1);
		//// axisCam = new AxisCamera("Axis Camera", "10.54.27.11");
		//// axisCam.setFPS(15);
		//// camServer.addAxisCamera("Axis Camera", "10.54.27.11");
		////
		//// camServer.startAutomaticCapture(axisCam);
		//
		// camServer.addServer("USB Camera Server");
		// camServer.putVideo("USB Camera",100,100);
		// Log.info("E");
		//
		// Log.info("E");
		//
		// Log.info("E");
		//
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
		//// encRight.reset();
		encLeft.reset();
		ahrs.reset();
		// SmartDashboard.putNumber("encRightVal", encRight.getDistance());
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		// SmartDashboard.putNumber("encRight", encRight.getDistance());
		SmartDashboard.putNumber("encLeft", encLeft.getDistance());
	}

	@Override
	public void disabledPeriodic() {
		// Scheduler.getInstance().run();
		// SmartDashboard.putNumber("encRightVal", encRight.getDistance());
		SmartDashboard.putNumber("encLeftVal", encLeft.getDistance());
		// SmartDashboard.putNumber("encRight", encRight.getDistance());
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
		Scheduler.getInstance().run();
		// encRight.reset();
		encLeft.reset();
		ahrs.reset();
		// new PIDStraightMovement(this.speedcontrollergroup_right,
		// this.speedcontrollergroup_left, .3, 154, 0.0115, 0, 0.005).start();
		// new PIDTurn(speedcontrollergroup_left, speedcontrollergroup_right,
		// 90).start();
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
		autoPath.start();
		// f = new Fidget();
		// f.start();
		// b = false;
		// Tested and fully functional (within 15 seconds):
		// Center_SwitchIsLeft, Center_SwitchIsRight, Left_SwitchIsLeft,
		// Not fully functional:
		// Left_SwitchIsRight- Takes too long
		//
//		autoPath = new Right_ScaleIsRight();
//		autoPath.start();
	}
	// boolean b = false;
	// Fidget f;

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		// this.speedcontrollergroup_left.set(.3);
		// this.speedcontrollergroup_right.set(-.3);
		// if (f.isFinished() && !b) {
		// new MoveElevatorAuto(1).start();
		// b = true;
		// }
	}

	@Override
	public void teleopInit() {
		// SmartDashboard.putBoolean("UPLS", elevatorLimitSwitchUp.get());
		// SmartDashboard.putBoolean("DLS", elevatorLimitSwitchDown.get());
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autoPath != null) {
			autoPath.cancel();
		}
		// encRight.reset();
		// encLeft.reset();
		// dwj = new DriveWithJoystick();
		// dwj.start();
		/*
		 * encRight.reset(); encLeft.reset();
		 * 
		 * encRight.setDistancePerPulse((6*Math.PI)/360);
		 * encLeft.setDistancePerPulse((6*Math.PI)/360);
		 */
		// 360 cycles per WPILIB REvolution
		// Even though AndyMark SAYS:
		// 1440 pulses per revolution
		// 360 cycles per revolution
		// 4 pulses per cycle
		// Log.info();
		// dwj = new DriveWithJoystick();
		// x=0;
	}

	// int x =0;
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// System.out.print("E:");
		// Log.info("E");
		if (elevatorLimitSwitchUp.get())
			SmartDashboard.putNumber("UPLS", 1);
		else
			SmartDashboard.putNumber("UPLS", 0);
		// TODO This needs to be here for limit switches to work!
		SmartDashboard.putBoolean("a", mou.isFinished());
		SmartDashboard.putBoolean("a", mod.isFinished());
		// System.out.print("E:"+elevatorLimitSwitchUp.get());
		// System.out.print("EEEEE");
		// 4 counts for every rev
		/*
		 * SmartDashboard.putNumber("RightCount", encRight.get());
		 * SmartDashboard.putNumber("LeftCount", encLeft.get());
		 * 
		 * SmartDashboard.putNumber("RightDist",encRight.getDistance());
		 * SmartDashboard.putNumber("LeftDist",encLeft.getDistance());
		 */
	}
}
