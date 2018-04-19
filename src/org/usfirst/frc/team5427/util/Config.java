package org.usfirst.frc.team5427.util;

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
	 * The name of our program as per the robot.
	 */
	public static final String PROGRAM_NAME = "Team5427RoboCode";
	
	/**
	 * Stores whether the robot code is in debug mode or not.
	 */
	public static final boolean DEBUG_MODE = true;
	
	/**
	 * Stores whether the robot code is sending messages to the log file.
	 */
	public static final boolean LOGGING = true;
	
	/*********************PWM PORTS********************/
	/**
	 * The PWM value for the front right motor of the drive train.
	 */
	public static final int FRONT_RIGHT_MOTOR = 3;
	
	/**
	 * The PWM value for the rear right motor of the drive train.
	 */
	public static final int REAR_RIGHT_MOTOR = 6;
	

	public static final int FRONT_LEFT_MOTOR = 1;
	public static final int REAR_LEFT_MOTOR = 0;
	public static final int INTAKE_MOTOR_LEFT = 7;
	public static final int INTAKE_MOTOR_RIGHT = 8;
	public static final int ELEVATOR_MOTOR = 9;
	public static final int TILT_INTAKE_MOTOR = 5;
	public static final int CLIMBER_ARM_MOTOR = 2;
	public static final int CLIMBER_MOTOR = 4;
	/*************************************************/

	/*----------DIO PORTS-----------*/
	public static final int ENCODER_LEFT_CHANNEL_A=0;
	public static final int ENCODER_LEFT_CHANNEL_B=1;
	
	/*--------Timeouts------*/
	public static final double AUTO_INTAKE_TIMEOUT = 1;
	public static final double TILT_TIMEOUT_UP = 2.4;
	public static final double TILT_TIMEOUT_DOWN = 1.7;
	
	/*----------Pneumatic Control Module PORTS-----------*/
	public static final int PCM_SOLENOID_FORWARD = 0;
	public static final int PCM_SOLENOID_REVERSE = 1;

	/*-------------Motor Speeds-------------------*/
	public static final double INTAKE_MOTOR_SPEED_IN = 0.3;
	public static final double INTAKE_MOTOR_SPEED_OUT = -1.0;
	public static final double INTAKE_MOTOR_SPEED_REVERSE = -1.0;
	public static final double INTAKE_MOTOR_SPEED_SLOW_OUT= -1.0;
	public static final double ELEVATOR_MOTOR_SPEED_UP=0.8;
	public static final double ELEVATOR_MOTOR_SPEED_DOWN=-.5;
	public static final double CLIMBER_ARM_MOTOR_SPEED_UP=0.5;
	public static final double CLIMBER_ARM_MOTOR_SPEED_DOWN=-0.3;
	public static final double CLIMBER_MOTOR_SPEED_UP=1.0;
	public static final double CLIMBER_MOTOR_SPEED_DOWN=-1.0;
	public static final double INTAKE_TILTER_MOTOR_SPEED_UP=1.0;
	public static final double INTAKE_TILTER_MOTOR_SPEED_DOWN=-1.0;
	public static final int ELEVATOR_LIMIT_SWITCH_UP = 5;
	public static final int ELEVATOR_LIMIT_SWITCH_DOWN = 4;
	
	/* ----------Joystick Buttons---------- */
	public static final int BUTTON_MOTOR_INTAKE_IN = 7;
	public static final int BUTTON_MOTOR_INTAKE_OUT = 1;
	public static final int BUTTON_MOTOR_INTAKE_OUT_SLOW = 0;
	public static final int BUTTON_ELEVATOR_UP = 5;
	public static final int BUTTON_ELEVATOR_DOWN= 3;
	public static final int BUTTON_INTAKE_TOGGLE_TILTER = 8;
	public static final int BUTTON_INTAKE_TILTER_UP = 6;
	public static final int BUTTON_INTAKE_TILTER_DOWN = 4;
	public static final int BUTTON_ELEVATOR_DOWN_MANUAL=11;
	
	/* ----------Controller Ports(Joystick)---------- */
	public static final int JOYSTICK_PORT = 0;
	public static final int ALT_JOYSTICK_PORT = 0;
	public static final int ONE_JOYSTICK = 0;
	public static final int TWO_JOYSTICKS = 1;
	public static final int JOYSTICK_MODE = ONE_JOYSTICK;
	
	/*------------Other Motor/Program Values------------*/
	public static final double DRIVE_SPEED_INCREMENT_VALUE=.01;
	public static final double DRIVE_INCREMENT_WAIT_VALUE=.01;
	public static final double ELEVATOR_TIME_SWITCH = 2;
	public static final double ELEVATOR_TIME_SCALE = 2.8;
	public static final double ELEVATOR_TIME_SCALE_DOWN = 2.8; 
	
	/*-------------PID VALUES------------------*/
	public static final double PID_UPDATE_PERIOD = 0.01;
	public static final double PID_STRAIGHT_P = .22;
	public static final double PID_STRAIGHT_I = 0;
	public static final double PID_STRAIGHT_D = .04;
	public static final double PID_STRAIGHT_POWER_SHORT = 0.5;
	public static final double PID_STRAIGHT_POWER_LONG = 0.7;

	/***Increment****/
	public static final double PID_STRAIGHT_LINEAR_INCREMENT= .003;
	public static final double POST_INCR_SWITCH_TO_PID = .1;
	public static final double PID_STRAIGHT_EXPONENTIAL_INCREMENT=1.05;
	public static final double SWITCH_TO_PID_VELOCITY = 30.;
	public static final double PID_STRAIGHT_COAST_POWER = 0.01;
	public static final double PID_STRAIGHT_COAST_P = 0.275;
	public static final double PID_STRAIGHT_COAST_I = 0.012333;
	public static final double PID_STRAIGHT_COAST_D = 0.0;
	public static final double PID_STRAIGHT_TOLERANCE = 5;
	public static final double PID_STRAIGHT_ACTIVATE_DISTANCE = 20;
	public static final double PID_TURN_POWER = 0.1;
	public static final double PID_TURN_TOLERANCE = 3;
	public static final double PID_TURN_SETPOINT =90;	
	public static final double PID_TURN_P = 0.015;
	public static final double PID_TURN_I = 0;
	public static final double PID_TURN_D = 0.01;
	
	/**********Auto Chooser*********/
	public static final int AUTO_NONE = -1;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int RIGHT = 1;
	public static final int CENTER = 2;
	public static final int LEFT = 3;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	public static final double PID_STRAIGHT_POWER_MED = .4;
}
