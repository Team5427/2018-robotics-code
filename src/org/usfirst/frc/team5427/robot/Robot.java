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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;

import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;
import org.usfirst.frc.team5427.robot.commands.ExampleCommand;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;

import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.robot.subsystems.PIDDriveTrainRightSide;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@NextLine
public class Robot extends TimedRobot
{
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI m_oi;
	public static DriveTrain driveTrain;
	
	Command m_autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	public static SpeedController motorPWM_Intake_Left;
	public static SpeedController motorPWM_Intake_Right;
	
	public PIDDriveTrainRightSide pidRight;
	
	/**
	 * values used for PID loops
	 */
	public double pidRightP;
	public double pidRightI;
	public double pidRightD;
	
	public double pidLeftP;
	public double pidLeftI;
	public double pidLeftD;
	
	
	double startTime;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		driveTrain = new DriveTrain();
		m_oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		
		Log.init("Initializing intake motors: ");
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);
	}
	
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit()
	{
		
	}
	
	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
		m_autonomousCommand = chooser.getSelected();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null)
		{
			m_autonomousCommand.start();
		}
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null)
		{
			m_autonomousCommand.cancel();
		}
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		startTime = System.nanoTime() / 1000000000.;
		pidRight = new PIDDriveTrainRightSide(pidRightP, pidRightI, pidRightD, 1, driveTrain.getM_right()); 
		// TODO temporary setpoint
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic()
	{
		
	}
}
