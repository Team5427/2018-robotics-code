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
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDPath;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.SameLine;

import com.kauailabs.navx.frc.AHRS;

//import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SameLine
public class Robot extends IterativeRobot  {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();

	public static OI oi;
	public static DriveTrain driveTrain;

	SpeedController motor_pwm_frontLeft;
    SpeedController motor_pwm_rearLeft ;
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
//	public PIDDriveTrainSide pidSide;
//	public PIDTurnCommand pidTurn;

	

	/**
	 * values used for PID loops
	 *TODO move these to Config
	 */
	
	public static Encoder encLeft;
	public static Encoder encRight;
	


	public PIDPath pid;
	public PIDStraightMovement pi;
	public static AHRS ahrs;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// driveTrain = new DriveTrain();

//		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());

		/*
		 * COMMENTED DUE TO ERRORS TODO ADD PORTS FOR SOLENOID
		 */
		// Log.init("Initializing solenoid");
		// intakeSolenoid = new DoubleSolenoid(Config.PCM_SOLENOID_FORWARD,
		// Config.PCM_SOLENOID_REVERSE);
		
//		Log.init("Initializing driveTrain: ");
		motor_pwm_frontLeft = new SteelTalon(Config.FRONT_LEFT_MOTOR);
		motor_pwm_rearLeft = new SteelTalon(Config.REAR_LEFT_MOTOR);
		speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);
		
		motor_pwm_frontRight = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
		motor_pwm_rearRight = new SteelTalon(Config.REAR_RIGHT_MOTOR);
		speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
		
		drive = new DifferentialDrive(speedcontrollergroup_left, speedcontrollergroup_right);
		driveTrain = new DriveTrain(speedcontrollergroup_left, speedcontrollergroup_right, drive);
	
		
//		Log.init("Initializing intake motors: ");
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);

//		Log.init("Initializing Subsystems: ");
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);

	

//		Log.init("Intializing Elevator Motor: ");
		motorPWM_Elevator = new SteelTalon(Config.ELEVATOR_MOTOR);
		
		try {
			ahrs = new AHRS(SPI.Port.kMXP);

		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}

		
		//create encoders
		//TODO put port values in Config
		encLeft = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		encRight = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		//Set the Encoder to diameter*pi/360 inches per pulse (each pulse is a degree)
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
//		if(pidSide!=null)
//			pidSide.free();
//		if(pidTurn!=null)
//			pidTurn.free();
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
//		m_	mousCommand = chooser.getSelected(); TODO remove this line of code
		
//		String gameData = DriverStation.getInstance().getGameSpecificMessage();
//		char[] sideColorArray = gameData.toCharArray();
//		char firstSwitch = sideColorArray[0];
//		char scale = sideColorArray[1];
//		char lastSwitch = sideColorArray[2];
//		Log.init(""+"First: "+ firstSwitch +"\nScale: "+ scale +"\nLast: "+ lastSwitch);
		//the characters have L for left or R for right

		// schedule the autonomous command (example) TODO delete these lines of code after
		//we implement our own autonomous commands
//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.start();
//		}
		
		encRight.reset();
		encLeft.reset();
		ahrs.reset();
		
		pi=new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0.3, 60);
		pi.start();
		
//		pid = new PIDPath();
//		pid.start();
//		PIDDriveTrainSide pi = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0, 160);
//		pi.start();
		//removes history of the PID loop (destroys the older loop) if pidSide stores a PIDDRiveTRainSIde object
//		if(pidSide!=null)
//			pidSide.free();
//		if(pidTurn!=null)
//			pidTurn.free();
		
//		pidSide = new PIDDriveTrainSide(driveTrain.drive_Right, driveTrain.drive_Left, 0, 60);

//		pidTurn = new PIDTurnCommand(driveTrain.drive_Right, driveTrain.drive_Left, Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, Config.PID_TURN_SETPOINT);
//
//		pidTurn.start();
		//removes history of the PID loop (destroys the older loop)
//		pidSide.free();
		
			//	new PIDDriveTrainSide(driveTrain.drive_Right, driveTrain.drive_Left, Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, 90);
		
	}
	


	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putNumber("L MOtor speed", speedcontrollergroup_left.get());
		//robot stutters
//		pidSide.incrementPower();
		
	}

	@Override
	public void teleopInit() {
		//This makes sure that the autonomous stops running when
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
//		Log.init("Initializing test");
		
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit()
	{	
		// for straight(setpoint is 1. going straight)
	
		
	}

//	
//	enum Mode{Straight, Left, Right}
//	Mode mode = Mode.Straight;
//	PIDAction currentPIDAction;
//	
//	public void turnAngleClockwise(double degrees) {
//		if(currentPIDAction.isFinished()) {
//			//currentPIDAction = new PIDAction(startAngle, endAngle, currentAngle);
//		}
//	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

			
	}

   
}
