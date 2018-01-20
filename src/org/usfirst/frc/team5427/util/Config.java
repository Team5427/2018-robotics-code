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
	
	/*----------PWM PORTS-----------*/
	public static final int INTAKE_MOTOR_LEFT = 1;
	public static final int INTAKE_MOTOR_RIGHT = 0;
	/* ----------Joystick Buttons---------- */
	public static final int BUTTON_MOTOR_INTAKE = 1;
	
	/*-------------Motor Bias-------------------*/
	public static final double INTAKE_BACKWARD = 1;
	public static final double INTAKE_FORWARD = .3;
	
}
