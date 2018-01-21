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
import org.usfirst.frc.team5427.robot.commands.PIDDriveTrainPower;
import org.usfirst.frc.team5427.robot.commands.PIDDriveTrainSide;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.Log;
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
	

	/**
	 * values used for PID loops
	 *TODO move these to Config
	 */
	


	
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
		
		Log.init("Initializing driveTrain: ");
		motor_pwm_frontLeft = new SteelTalon(Config.FRONT_LEFT_MOTOR);
		motor_pwm_rearLeft = new SteelTalon(Config.REAR_LEFT_MOTOR);
		speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);
		
		motor_pwm_frontRight = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
		motor_pwm_rearRight = new SteelTalon(Config.REAR_RIGHT_MOTOR);
		speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
		
		drive = new DifferentialDrive(speedcontrollergroup_left, speedcontrollergroup_right);
		driveTrain = new DriveTrain(speedcontrollergroup_left, speedcontrollergroup_right, drive);
	
		
		Log.init("Initializing intake motors: ");
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);

		Log.init("Initializing Subsystems: ");
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);

	

		Log.init("Intializing Elevator Motor: ");
		motorPWM_Elevator = new SteelTalon(Config.ELEVATOR_MOTOR);
		
		try {

			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
			 * SerialPort.Port.kUSB
			 */
			/*
			 * See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
			 * for details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP);

		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}

		

		oi = new OI();
		Log.init("Robot init done");
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		m_autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		ahrs.reset();
		new PIDDriveTrainPower(new PIDDriveTrainSide(driveTrain.drive_Right, driveTrain.drive_Left, Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, 0, 0), 0,.4, Config.PID_POWER_P, Config.PID_POWER_I, Config.PID_POWER_D);
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
		Log.init("Teleop init");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		dwj = new DriveWithJoystick();
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
