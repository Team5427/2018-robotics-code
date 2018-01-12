package org.usfirst.frc.team5427.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO FIXED CODE fEB 20 2017
/**
 * This file will store ALL of the variables, offsets, measurements, etc. that
 * our robot will use during the year. All variables are to be static, and
 * nothing in this file should ever have to be initiated.
 * 
 * @author Andrew Kennedy, Bo Corman, Ethan Bennikutty
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
	// Speeds for the different things that the robot needs to do
	// Controlled by grip
	// later

	/*----------PWM PORTS-----------*/
	
	/*----------PCM PORTS-----------*/
	public static final int PCM_SOLENOID_FORWARD = 0;
	public static final int PCM_SOLENOID_REVERSE = 1;

	/*-------------Ultrasonic Sensor Ports---------*/

	/*----------MULTIPLIERS AND DEADSETS----------*/

	/*-------------Motor Bias-------------------*/

	public static final double INTAKE_BACKWARD = 1;
	public static final double INTAKE_FORWARD = .35;
	/*-------------Motor Offset------------------*/

	/*-------------Motor Speeds-----------------*/

	/* ----------Controller Ports(Joystick)---------- */
	public static final int JOYSTICK_PORT = 0;
	public static final int ALT_JOYSTICK_PORT = 0;
	public static final int ONE_JOYSTICK = 0; // static var for above
	public static final int TWO_JOYSTICKS = 1; // static var for above
	public static final int JOYSTICK_MODE = ONE_JOYSTICK;

	/*------------speed for auto-------*/

	/*------------timings for auto-------*/

	/*-----------spot-----------*/

	/*----------autonomous aiming------*/

	/*---------------Buttons------------------- */

	/*------------Mulipurpose Flap Vars------*/

	/* Servo Motors */

}
