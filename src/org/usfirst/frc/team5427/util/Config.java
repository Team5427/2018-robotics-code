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
	public static final int FRONT_RIGHT_MOTOR = 0;
	public static final int REAR_RIGHT_MOTOR = 1;
	public static final int FRONT_LEFT_MOTOR = 2;
	public static final int REAR_LEFT_MOTOR = 3;
	public static final int INTAKE_MOTOR_LEFT = 4;
	public static final int INTAKE_MOTOR_RIGHT = 5;
	
	/*----------Pneumatic Control Module PORTS-----------*/
	public static final int PCM_SOLENOID_FORWARD = 0;
	public static final int PCM_SOLENOID_REVERSE = 1;

	/*-------------Motor Bias-------------------*/

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
	public static final int CHANNEL_A_RIGHT = 0;
	public static final int CHANNEL_B_RIGHT = 0;
	public static final int CHANNEL_A_LEFT = 0;
	public static final int CHANNEL_B_LEFT = 0;
	public static final int ELEVATOR_MOTOR = 7;
	
	

}
