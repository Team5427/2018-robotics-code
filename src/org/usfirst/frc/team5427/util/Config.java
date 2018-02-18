package org.usfirst.frc.team5427.util;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO FIXED CODE fEB 20 2017
/**
 * This file will store ALL of the variables, offsets, measurements, etc. that
 * our robot will use during the year. All variables are to be static, and
 * nothing in this file should ever have to be initiated.
 * 
 * @author Andrew Kennedy, Bo Corman
 */

@SameLine
public class Config {

	/**
	 * <p>
	 * The name of the program that will be used in the console, or anywhere else
	 * applicable.
	 * </p>
	 */
	public static final String PROGRAM_NAME = "Team5427RoboCode";
	/**
	 * <p>
	 * If true, then every call to <code>Log.debug()</code> will be printed in the
	 * console.
	 * </p>
	 * <p>
	 * If false, then all calls to this method will be ignored, saving the console
	 * from any spam created from debugging.
	 * </p>
	 */
	public static final boolean DEBUG_MODE = true;
	/**
	 * <p>
	 * This controls all print statements sent through <code>Log.java</code>,
	 * besides <code>Log.error</code> and <code>Log.fatal</code>.
	 * </p>
	 * <p>
	 * If true, then all printing not excluded by this method will be displayed in
	 * the console. If false, it will not.
	 * </p>
	 */
	public static final boolean LOGGING = true; // only logs errors and fatals
												// with this false
	
	/*----------PWM PORTS-----------*/
	public static final int FRONT_RIGHT_MOTOR = 0; //3
	public static final int REAR_RIGHT_MOTOR = 1; //2
	public static final int FRONT_LEFT_MOTOR = 2; //1
	public static final int REAR_LEFT_MOTOR = 3; //0
	public static final int INTAKE_MOTOR_LEFT = 6;
	public static final int INTAKE_MOTOR_RIGHT = 5;
	public static final int ELEVATOR_MOTOR = 4;
	
	/*----------Speeds----------*/
	public static final double INTAKE_SPEED_FORWARD = .3;
	public static final double INTAKE_SPEED_REVERSE = -.3;
	
	/*----------Pneumatic Control Module PORTS-----------*/
	public static final int PCM_SOLENOID_FORWARD = 0;
	public static final int PCM_SOLENOID_REVERSE = 1;

	/* ----------Joystick Buttons---------- */
	public static final int BUTTON_MOTOR_INTAKE = 1;
	public static final int BUTTON_SOLENOD_INTAKE = 2;
	public static final int BUTTON_ELEVATOR_FORWARD = 3;
	public static final int BUTTON_ELEVATOR_REVERSE= 5;
	
	/* ----------Controller Ports(Joystick)---------- */
	public static final int JOYSTICK_PORT = 0;
	public static final int ALT_JOYSTICK_PORT = 0;
	public static final int ONE_JOYSTICK = 0; // static var for above
	public static final int TWO_JOYSTICKS = 1; // static var for above
	public static final int JOYSTICK_MODE = ONE_JOYSTICK;
	
	
	// Speeds for the different things that the robot needs to do
	// Controlled by grip
	// later
	/* -------------PWM ID----------*/
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	public static final int frontleftValue = 2;
	public static final int frontrightValue = 0;
	public static final int rearleftValue = 3;
	public static final int rearrightValue = 1;

	/*-------------Motor Bias-------------------*/
	public static final double INTAKE_BACKWARD = 1;
	public static final double INTAKE_FORWARD = .5;
	
	/*-------------Motor Offset------------------*/
	
	
	/*------------Other Motor/Program Values------------*/
	public static final double DRIVE_SPEED_INCREMENT_VALUE=.01;
	public static final double DRIVE_INCREMENT_WAIT_VALUE=.01;
	
	public static final double ELEVATOR_TIME_SWITCH = 4; // TODO change to actual value (seconds)
	public static final double ELEVATOR_TIME_SCALE = 0;
	
	
	/*-------------PID VALUES------------------*/
	//former p = .085, i = .008333, d = .001042
	
	//for straight no d, for turn no i
	public static final double PID_STRAIGHT_P = 0.05;
	//.005 started oscilating
	public static final double PID_STRAIGHT_I = 0.0025;
	public static final double PID_STRAIGHT_D = 0.00;
	public static final double PID_STRAIGHT_POWER = .30;
	
	
	/***Increment****/
	public static final double PID_STRAIGHT_LINEAR_INCREMENT=.01;
	public static final double POST_INCR_SWITCH_TO_PID = .1;
	public static final double PID_STRAIGHT_EXPONENTIAL_INCREMENT=2;
	

	public static final double PID_STRAIGHT_COAST_POWER = 0.01;
	public static final double PID_STRAIGHT_COAST_P = 0.275;
	public static final double PID_STRAIGHT_COAST_I = 0.012333;
	public static final double PID_STRAIGHT_COAST_D = 0.0;
	public static final double PID_STRAIGHT_TOLERANCE = 1;//.1
//	public static final double PID_STRAIGHT_COAST_P = 1;
//	public static final double PID_STRAIGHT_COAST_I = 1;
//	public static final double PID_STRAIGHT_COAST_D = 1;
	
	/* Values for moving 60 inches.
	public static final double PID_STRAIGHT_DISTANCE_P = 0.1;
	public static final double PID_STRAIGHT_DISTANCE_I = 0;
	public static final double PID_STRAIGHT_DISTANCE_D = 0.55;
	*/
	
//	public static final double PID_STRAIGHT_INCREMENT_DECREMENT = .001;
	
	public static final double PID_TURN_POWER = 0.1;
	public static final double PID_TURN_TOLERANCE = 2;
	public static final double PID_TURN_SETPOINT =90;	
	
//	//values for 90 deg; p = .026, still need to be tuned
//	public static final double PID_TURN_P = 0.042;
//	public static final double PID_TURN_I = 0;
//	public static final double PID_TURN_D = 0.106;

	
	//values for 35 degree and 25 degrees
	public static final double PID_TURN_P = 0.0402;
	public static final double PID_TURN_I = 0;
	public static final double PID_TURN_D = 0.115;
	
	//VALUES FOR 45 Degrees, relatively tuned
//	public static final double PID_TURN_P = 0.055;
//	public static final double PID_TURN_I = 0.0;
//	public static final double PID_TURN_D = 0.172;
//	
	
	
	/**********Auto Chooser*********/
	public static final int AUTO_NONE = -1;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int RIGHT = 1;
	public static final int CENTER = 2;
	public static final int LEFT = 3;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	
	/**
	 * returns the average stopping for different powers from 0 to full power, 
	 * more accuracy at the higher powers
	 * @param power the speed of the drive TRain
	 * @return
	 */
	public static double getCoastingDistance(double power)
	{
		//provides correct stopping distances for various power value ranges
//		if(power<=0.2)
//			return 0; //TODO return correct number
//		if(power<=0.3)
//			return 1; //TODO return correct number
//		if(power<=0.4)
//			return 2; //TODO return correct number
//		if(power<=0.45)
//			return 3; //TODO return correct number
//		if(power<=0.5)
//			return 4; //TODO return correct number
//		if(power<=0.55)
//			return 5; //TODO return correct number
//		if(power<=0.6)
//			return 6; //TODO return correct number
//		if(power<=0.65)
//			return 7; //TODO return correct number
//		if(power<=0.7)
//			return 8; //TODO return correct number
//		if(power<=0.75)
//			return 9; //TODO return correct number
//		if(power<=0.8)
//			return 10; //TODO return correct number
//		if(power<=0.83)
//			return 11; //TODO return correct number
//		if(power<=0.85)
//			return 12; //TODO return correct number
//		if(power<=0.9)
//			return 13; //TODO return correct number
//		if(power<=0.95)
//			return 14; //TODO return correct number
//		return 100;
		return 22;
	}

}
