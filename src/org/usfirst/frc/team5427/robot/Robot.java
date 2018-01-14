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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

import org.usfirst.frc.team5427.robot.OurClasses.PIDAction;
import org.usfirst.frc.team5427.robot.OurClasses.SteelTalon;
import org.usfirst.frc.team5427.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team5427.robot.commands.ExampleCommand;
import org.usfirst.frc.team5427.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5427.robot.subsystems.Intake;
import org.usfirst.frc.team5427.robot.subsystems.PIDDriveTrainLeftSide;
import org.usfirst.frc.team5427.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5427.robot.subsystems.PIDDriveTrainRightSide;
import org.usfirst.frc.team5427.util.Config;

import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.NextLine;
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
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();

	public static OI oi;
	public static AHRS ahrs;
	public static DriveTrain driveTrain;

	//Speed Controllers created for the left speed control groups
		SpeedController motor_pwm_frontLeft;
	    SpeedController motor_pwm_rearLeft ;
	    SpeedControllerGroup speedcontrollergroup_left;

	 //Speed Controllers created for the left speed control groups
	    SpeedController motor_pwm_frontRight;
	    SpeedController motor_pwm_rearRight;
	    SpeedControllerGroup speedcontrollergroup_right;
	    DifferentialDrive drive;
	    
	    DriveWithJoystick dwj;
	

	Command m_autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	public static SpeedController motorPWM_Intake_Left;
	public static SpeedController motorPWM_Intake_Right;

	public static Intake intakeSubsystem;
	// makes an encoder to go straight
	public static Encoder encoderStraight;

	public PIDDriveTrainRightSide pidRight;
	public PIDDriveTrainLeftSide pidLeft;

	/**
	 * values used for PID loops
	 */
	public double pidRightP = .085000;
	public double pidRightI = .008333;
	public double pidRightD = .001042;

	public double pidLeftP;
	public double pidLeftI;
	public double pidLeftD;

	double startTime;
	double rotateToAngleRate=0;
	double rightMotorSpeed = 0;
	double leftMotorSpeed = 0;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", new ExampleCommand());

		//initializes intake motors
		Log.init("Initializing intake motors: ");
		motorPWM_Intake_Left = new SteelTalon(Config.INTAKE_MOTOR_LEFT);
		motorPWM_Intake_Right = new SteelTalon(Config.INTAKE_MOTOR_RIGHT);

		// creates the intake subsystem
		Log.init("Initializing Subsystems: ");
		intakeSubsystem = new Intake(motorPWM_Intake_Left, motorPWM_Intake_Right);
		
		//encoder created for counting rpm of the motor (used for pid forward movement)
		Log.init("Initializing Encoders: "); // need info of ports
		//encoderStraight = new Encoder(0, 0);

		
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
		m_autonomousCommand = chooser.getSelected();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
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
		 motor_pwm_frontLeft = new SteelTalon(Config.FRONT_LEFT_MOTOR);
	     motor_pwm_rearLeft = new SteelTalon(Config.REAR_LEFT_MOTOR);
	     speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);

	     motor_pwm_frontRight = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
	     motor_pwm_rearRight = new SteelTalon(Config.REAR_RIGHT_MOTOR);
	     speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
	     
	     // each part(left and right) of the drive train act separately
	     drive = new DifferentialDrive(speedcontrollergroup_left,speedcontrollergroup_right);
	     driveTrain = new DriveTrain(speedcontrollergroup_left,speedcontrollergroup_right,drive);
	     dwj= new DriveWithJoystick();
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
		 motor_pwm_frontLeft = new SteelTalon(Config.FRONT_LEFT_MOTOR);
	     motor_pwm_rearLeft = new SteelTalon(Config.REAR_LEFT_MOTOR);
	     speedcontrollergroup_left = new SpeedControllerGroup(motor_pwm_frontLeft, motor_pwm_rearLeft);

	     motor_pwm_frontRight = new SteelTalon(Config.FRONT_RIGHT_MOTOR);
	     motor_pwm_rearRight = new SteelTalon(Config.REAR_RIGHT_MOTOR);
	     speedcontrollergroup_right = new SpeedControllerGroup(motor_pwm_frontRight, motor_pwm_rearRight);
	     
	  // each part(left and right) of the drive train act separately
	     drive = new DifferentialDrive(speedcontrollergroup_left,speedcontrollergroup_right);
	     driveTrain = new DriveTrain(speedcontrollergroup_left,speedcontrollergroup_right,drive);
	     dwj= new DriveWithJoystick();
		
		try{
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//initializes the start time used for the pid
		startTime = System.nanoTime() / 1000000000.;

		//creates an instance of the ahrs(navx) each time intialized
		makeAHRS();
		
		//for straight(setpoint is 1. going straight)
		pidRight = new PIDDriveTrainRightSide(pidRightP, pidRightI, pidRightD, 1, driveTrain.drive_Right, ahrs); 
		pidLeft = new PIDDriveTrainLeftSide(pidLeftP, pidLeftI,pidLeftD,1,driveTrain.drive_Left, ahrs);
		
		//enables the right side pid controller
		pidRight.getPIDController().enable();
		pidLeft.getPIDController().enable();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
//		add values to smartdashboard for PID testing (graph)
		 SmartDashboard.putNumber("PID Output: ", rotateToAngleRate);
		 SmartDashboard.putNumber("Yaw: ", pidRight.ahrs.getYaw());
		 Log.info("[R] IS ENABLED: "+pidRight.getPIDController().isEnabled());
		 Log.info("[L] IS ENABLED: "+pidRight.getPIDController().isEnabled());
		 Log.info("YAW: "+ pidRight.ahrs.getYaw());
		 driveTrain.drive.tankDrive(pidLeft.spgLeft.get(), pidRight.spgRight.get());
		/*//rotateToAngleRate is the pidOutput
		double currentRotationRate = rotateToAngleRate-.5;
		Log.info(""+currentRotationRate);
		
		//shows us the right motor speed and sets it to the drive train
		Log.info("rightMotorSpeed: " + rightMotorSpeed);
 		driveTrain.drive.tankDrive(-currentRotationRate, -rightMotorSpeed);
	
 		if(rightMotorSpeed>-.5)
			rightMotorSpeed-=0.006;
		
		if(rightMotorSpeed<-.5)
			rightMotorSpeed = -.5;*/
	}
	
	//ahrs is created to reset everytime code is deployed to run
	public void makeAHRS() {
	        try {
				/* Communicate w/navX-MXP via the MXP SPI Bus. */
				/* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
				/* See: http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details.*/
				ahrs = new AHRS(SPI.Port.kMXP) {
					@Override
					public double pidGet() {
						return ahrs.getYaw();
					}
				};
			} catch (RuntimeException ex) {
				DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
			}
		}
}
