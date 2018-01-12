/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
/**
 * This file maps out the robot
 * This file will use the same line method
 * 
 * @author Akshat
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.


	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static final int frontleftValue = 2;
	public static final int frontrightValue = 0;
	public static final int rearleftValue = 3;
	public static final int rearrightValue = 1;
	
	public static final int FRONT_RIGHT_MOTOR = 5;
	public static final int REAR_RIGHT_MOTOR = 4;
	public static final int FRONT_LEFT_MOTOR = 2;
	public static final int REAR_LEFT_MOTOR = 3;
	public static final int INTAKE_MOTOR_LEFT = 1;
	public static final int INTAKE_MOTOR_RIGHT = 0;
}
