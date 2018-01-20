/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;

import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.ExampleCommand;
import org.usfirst.frc.team5427.robot.commands.IntakeSolenoidSwitch;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;

import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;
import org.usfirst.frc.team5427.util.SameLine;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SameLine
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static DriveTrain driveTrain;
	
	SpeedController motorPWM_Front_Left;
	SpeedController motorPWM_Rear_Left;
	SpeedControllerGroup driveTrainLeft;

	SpeedController motorPWM_Front_Right;
	SpeedController motorPWM_Rear_Right;
	SpeedControllerGroup driveTrainRight;

	DifferentialDrive drive;

	DriveWithJoystick dwj;

	SendableChooser<Command> chooser = new SendableChooser<>();

	public static DoubleSolenoid intakeSolenoid;

	public static SpeedController motorPWM_Intake_Left;
	public static SpeedController motorPWM_Intake_Right;

	public static SpeedController motorPWM_Elevator;

	public static Intake intakeSubsystem;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());

		/*
		 * COMMENTED DUE TO ERRORS TODO ADD PORTS FOR SOLENOID
		 */
		//Log.init("Initializing solenoid");
		//intakeSolenoid = new DoubleSolenoid(Config.PCM_SOLENOID_FORWARD,
		//Config.PCM_SOLENOID_REVERSE); 

		Log.init("Initializing intake motors: ");
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);

		Log.init("Initializing Subsystems: ");
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);

		Log.init("Intializing Elevator Motor: ");
		//motorPWM_Elevator = new SteelTalon(Config.ELEVATOR_MOTOR);
		
//		motorPWM_Front_Left = new SteelTalon(Config.FRONT_LEFT_MOTOR);
//		motorPWM_Rear_Left = new SteelTalon(Config.REAR_LEFT_MOTOR);
//		driveTrainLeft = new SpeedControllerGroup(motorPWM_Front_Left, motorPWM_Rear_Left);
//
//		motorPWM_Rear_Right = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
//		motorPWM_Front_Right = new SteelTalon(Config.REAR_RIGHT_MOTOR);
//		driveTrainRight = new SpeedControllerGroup(motorPWM_Rear_Right, motorPWM_Front_Right);
//
//		drive = new DifferentialDrive(driveTrainLeft, driveTrainRight);

	//	driveTrain = new DriveTrain(driveTrainLeft, driveTrainRight, drive);

		oi = new OI();
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

	

	//	dwj = new DriveWithJoystick();
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
