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

	/******************** PWM PORTS *******************/
	/**
	 * The PWM value for the front right motor of the drive train.
	 */
	public static final int FRONT_RIGHT_MOTOR = 3;

	/**
	 * The PWM value for the rear right motor of the drive train.
	 */
	public static final int REAR_RIGHT_MOTOR = 6;

	/**
	 * The PWM value for the front left motor of the drive train.
	 */
	public static final int FRONT_LEFT_MOTOR = 1;

	/**
	 * The PWM value for the rear left motor of the drive train.
	 */
	public static final int REAR_LEFT_MOTOR = 0;

	/**
	 * The PWM value for the left motor of the intake.
	 */
	public static final int INTAKE_MOTOR_LEFT = 7;

	/**
	 * The PWM value for the right motor of the intake.
	 */
	public static final int INTAKE_MOTOR_RIGHT = 8;

	/**
	 * The PWM value for the right motor of the elevator.
	 */
	public static final int ELEVATOR_MOTOR_RIGHT = 9;
	/**
	 * The PWM value for the left motor of the elevator.
	 */
	public static final int ELEVATOR_MOTOR_LEFT = 4;

	/**
	 * The PWM value for the motor of the intake tilt.
	 */
	public static final int TILT_INTAKE_MOTOR = 5;

	/**
	 * The PWM value for the motor of the climber arm.
	 */
	public static final int CLIMBER_ARM_MOTOR = 2;

	/**
	 * The PWM value for the motor of the climber.
	 */
	public static final int CLIMBER_MOTOR = 10;
	/*************************************************/

	/******************** DIO PORTS *******************/
	/**
	 * The DIO port value for the A channel of the encoder on the left portion of
	 * the drive train.
	 */
	public static final int ENCODER_LEFT_CHANNEL_A = 0;

	/**
	 * The DIO port value for the B channel of the encoder on the left portion of
	 * the drive train.
	 */
	public static final int ENCODER_LEFT_CHANNEL_B = 1;

	/**
	 * The DIO port value for the limit switch located at the top of the elevator.
	 */
	public static final int ELEVATOR_LIMIT_SWITCH_UP = 5;

	/**
	 * The DIO port value for the limit switch located at the bottom of the
	 * elevator.
	 */
	public static final int ELEVATOR_LIMIT_SWITCH_DOWN = 4;
	/*************************************************/

	/******************** TIMEOUTS *******************/
	/**
	 * The timeout used in shooting the intake out during autonomous.
	 */
	public static final double AUTO_OUTTAKE_TIMEOUT = 1;
	
	/**
	 * The timeout used in picking up a cube during autonomous.
	 */
	public static final double AUTO_INTAKE_TIMEOUT = 3;

	/**
	 * The timeout used in tilting the intake up.
	 */
	public static final double TILT_TIMEOUT_UP = 2.4;

	/**
	 * The timeout used in tilting the intake down.
	 */
	public static final double TILT_TIMEOUT_DOWN = 1.7;

	/**
	 * The timeout used in moving the elevator to the switch position.
	 */
	public static final double ELEVATOR_TIMEOUT_SWITCH = 1;

	/**
	 * The timeout used in moving the elevator to the scale position.
	 */
	public static final double ELEVATOR_TIMEOUT_SCALE = 2.8;

	/**
	 * The timeout used in moving the elevator back to default from the scale.
	 */
	public static final double ELEVATOR_TIMEOUT_SCALE_DOWN = 2.8;
	
	/**
	 * The timeout used in moving the elevator back to default from the switch.
	 */
	public static final double ELEVATOR_TIMEOUT_SWITCH_DOWN = 50;
	/*************************************************/

	/******************** MOTOR SPEEDS *******************/
	/**
	 * The motor speed that correlates to pulling in a box.
	 */
	public static final double INTAKE_MOTOR_SPEED_IN = 0.3;

	/**
	 * The motor speed that correlates to shooting out a box.
	 */
	public static final double INTAKE_MOTOR_SPEED_OUT = -0.75;

	/**
	 * The motor speed that correlates to shooting a box out slowly.
	 */
	public static final double INTAKE_MOTOR_SPEED_SLOW_OUT = -1.0;

	/**
	 * The motor speed that correlates to moving the elevator up.
	 */
	public static final double ELEVATOR_MOTOR_SPEED_UP = 0.8;
	
	/**
	 * The motor speed that correlates to moving the elevator up auto.
	 */
	public static final double ELEVATOR_MOTOR_SPEED_UP_AUTO = 1;

	/**
	 * The motor speed that correlates to moving the elevator down.
	 */
	public static final double ELEVATOR_MOTOR_SPEED_DOWN = -.5;

	/**
	 * The motor speed that correlates to moving the climber arm up.
	 */
	public static final double CLIMBER_ARM_MOTOR_SPEED_UP = 0.5;

	/**
	 * The motor speed that correlates to moving the climber arm down.
	 */
	public static final double CLIMBER_ARM_MOTOR_SPEED_DOWN = -0.3;

	/**
	 * The motor speed that correlates to climbing upward.
	 */
	public static final double CLIMBER_MOTOR_SPEED_UP = 1.0;

	/**
	 * The motor speed that correlates to descending.
	 */
	public static final double CLIMBER_MOTOR_SPEED_DOWN = -1.0;

	/**
	 * The motor speed that correlates to tilting the intake upward.
	 */
	public static final double INTAKE_TILTER_MOTOR_SPEED_UP = 1.0;

	/**
	 * The motor speed that correlates to tilting the intake downward.
	 */
	public static final double INTAKE_TILTER_MOTOR_SPEED_DOWN = -1.0;
	/*****************************************************/

	/******************** JOYSTICK BUTTONS *******************/
	/**
	 * The button that correlates to pulling in a box.
	 */
	public static final int BUTTON_MOTOR_INTAKE_IN = 7;

	/**
	 * The button that correlates to shooting out a box.
	 */
	public static final int BUTTON_MOTOR_INTAKE_OUT = 1;

	/**
	 * The button that correlates to shooting out a box slowly.
	 */
	public static final int BUTTON_MOTOR_INTAKE_OUT_SLOW = 0;

	/**
	 * The button that correlates to moving the elevator upward.
	 */
	public static final int BUTTON_ELEVATOR_UP = 5;

	/**
	 * The button that correlates to moving the elevator downward.
	 */
	public static final int BUTTON_ELEVATOR_DOWN = 3;

	/**
	 * The button that correlates to switching the tilt position of the intake.
	 */
	public static final int BUTTON_INTAKE_TOGGLE_TILTER = 8;

	/**
	 * The button that correlates to tilting the intake upward.
	 */
	public static final int BUTTON_INTAKE_TILTER_UP = 6;

	/**
	 * The button that correlates to tilting the intake downward.
	 */
	public static final int BUTTON_INTAKE_TILTER_DOWN = 4;

	/**
	 * The button that correlates to moving the elevator down manually without the
	 * limit switch affecting it.
	 */
	public static final int BUTTON_ELEVATOR_DOWN_MANUAL = 11;
	/*********************************************************/

	/******************** CONTROLLER PORTS *******************/
	/**
	 * The port associated with the main joystick.
	 */
	public static final int JOYSTICK_PORT = 0;

	/**
	 * The port associated with a second joystick.
	 */
	public static final int ALT_JOYSTICK_PORT = 0;

	/**
	 * The mode designating that we are using one joystick.
	 */
	public static final int ONE_JOYSTICK = 0;

	/**
	 * The mode designating that we are using two joysticks.
	 */
	public static final int TWO_JOYSTICKS = 1;

	/**
	 * Stores what mode of controller use we are currently using.
	 */
	public static final int JOYSTICK_MODE = ONE_JOYSTICK;
	/*********************************************************/

	/******************** PID VALUES *******************/
	/**
	 * The update period used inside of the PIDControllers.
	 */
	public static final double PID_UPDATE_PERIOD = 0.01;

	/**
	 * The power used to move short distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_SHORT = 0.5;

	/**
	 * The power used to move medium distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_MED = .4;

	/**
	 * The power used to move long distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_LONG = 0.7;

	/**
	 * The power used to coast distances in autonomous.
	 */
	public static final double PID_STRAIGHT_COAST_POWER = 0.01;

	/**
	 * The distance the robot needs to travel before the straight PID loop
	 * activates.
	 */
	public static final double PID_STRAIGHT_ACTIVATE_DISTANCE = 20;

	/**
	 * The tolerance, in degrees, used while moving straight.
	 */
	public static final double PID_STRAIGHT_TOLERANCE = 3;
	
	/**
	 * The tolerance, in inches, used when moving to a distance.
	 */
	public static final double PID_DISTANCE_TOLERANCE = 5;

	/**
	 * The starting power used while turning to an angle.
	 */
	public static final double PID_TURN_POWER = 0.1;

	/**
	 * The default setpoint for a turn.
	 */
	public static final double PID_TURN_SETPOINT = 90;

	/**
	 * The tolerance, in degrees, used while turning.
	 */
	public static final double PID_TURN_TOLERANCE = 3;

	/**
	 * The P value used for moving straight.
	 */
	public static final double PID_STRAIGHT_P = .135;//.22

	/**
	 * The I value used for moving straight.
	 */
	public static final double PID_STRAIGHT_I = 0;

	/**
	 * The D value used for moving straight.
	 */
	public static final double PID_STRAIGHT_D = .037;//.04

	/**
	 * The P value used for coasting .
	 */
	public static final double PID_STRAIGHT_COAST_P = 0.275;

	/**
	 * The I value used for coasting.
	 */
	public static final double PID_STRAIGHT_COAST_I = 0.012333;

	/**
	 * The D value used for coasting.
	 */
	public static final double PID_STRAIGHT_COAST_D = 0.0;

	/**
	 * The P value used for turning.
	 */
	public static final double PID_TURN_P = 0.015;

	/**
	 * The I value used for turning.
	 */
	public static final double PID_TURN_I = 0;

	/**
	 * The D value used for turning.
	 */
	public static final double PID_TURN_D = 0.01;
	/***************************************************/

	/******************** INCREMENT *******************/
	/**
	 * The linear increment used in PIDControllers.
	 */
	public static final double PID_STRAIGHT_LINEAR_INCREMENT = .004;

	/**
	 * The exponential increment used in PIDControllers.
	 */
	public static final double PID_STRAIGHT_EXPONENTIAL_INCREMENT = 1.05;

	/**
	 * The increment used while driving.
	 */
	public static final double DRIVE_SPEED_INCREMENT_VALUE = .01;
	/**************************************************/

	/******************** AUTO CHOOSER *******************/
	/**
	 * The default option in the autonomous chooser.
	 */
	public static final int AUTO_NONE = -1;

	/**
	 * The right selection for choosing the starting position in the autonomous
	 * chooser.
	 */
	public static final int RIGHT = 1;

	/**
	 * The center selection for choosing the starting position in the autonomous
	 * chooser.
	 */
	public static final int CENTER = 2;

	/**
	 * The left selection for choosing the starting the position in the autonomous
	 * chooser.
	 */
	public static final int LEFT = 3;

	/**
	 * The switch selection for choosing the destination in the autonomous chooser.
	 */
	public static final int SWITCH = 1;

	/**
	 * The scale selection for choosing the destination in the autonomous chooser.
	 */
	public static final int SCALE = 2;
	/****************************************************/

	/******************** MISCELLANEOUS *******************/
	/**
	 * The value used in PIDControllers to determine when to switch to using the PID
	 * loop.
	 */
	public static final double POST_INCR_SWITCH_TO_PID = .1;

	/**
	 * The value used to determine when to use the driving increment.
	 */
	public static final double DRIVE_INCREMENT_WAIT_VALUE = .01;
	/******************************************************/
}
