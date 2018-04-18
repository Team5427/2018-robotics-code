/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Varsha 
 * 
 * Subsystem for the two flywheels that turn in opposite
 * directions
 */

@SameLine
public class Intake extends Subsystem
{
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private SpeedController leftFlywheel, rightFlywheel;

	/**
	 * The motor biases of each flywheel in both directions
	 */
	public static final double LEFT_FLYWHEEL_BIAS_FORWARD = 1;
	public static final double LEFT_FLYWHEEL_BIAS_BACKWARD = 1;
	public static final double RIGHT_FLYWHEEL_BIAS_FORWARD = 1;
	public static final double RIGHT_FLYWHEEL_BIAS_BACKWARD = 1;
	
	/**
	 * The motor offsets of each flywheel in both directions
	 */
	public static final double LEFT_FLYWHEEL_OFFSET_FORWARD = 0;
	public static final double LEFT_FLYWHEEL_OFFSET_BACKWARD = 0;
	public static final double RIGHT_FLYWHEEL_OFFSET_FORWARD = 0;
	public static final double RIGHT_FLYWHEEL_OFFSET_BACKWARD = 0;

	/**
	 * Constructor for the Intake subsystem
	 * 
	 * @param leftFlywheel
	 *            the SpeedController of the leftFlywheel
	 * @param rightFlywheel
	 *            the SpeedController of the rightFlywheel
	 */
	public Intake(SpeedController leftFlywheel, SpeedController rightFlywheel)
	{
		this.leftFlywheel = leftFlywheel;
		this.rightFlywheel = rightFlywheel;
	}

	/**
	 * Sets the speed of each flywheel to whatever the received speed parameter is
	 * @param speed
	 * 				how fast the flywheels will go
	 */
	public void setSpeed(double speed)
	{
		if (speed < 0)
		{
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_BACKWARD + LEFT_FLYWHEEL_OFFSET_BACKWARD);
			rightFlywheel.set(speed * RIGHT_FLYWHEEL_BIAS_FORWARD + RIGHT_FLYWHEEL_OFFSET_FORWARD);

		}
		else if(speed>0)
		{
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_FORWARD + LEFT_FLYWHEEL_OFFSET_FORWARD);
			rightFlywheel.set(speed * RIGHT_FLYWHEEL_BIAS_BACKWARD + RIGHT_FLYWHEEL_OFFSET_BACKWARD);
		}
		else
		{
			leftFlywheel.set(0);
			rightFlywheel.set(0);
		}

	}
	
	/**
	 * Unused method but required by extending SubSystem class
	 */
	public void initDefaultCommand() 
	{
		// Initializes default command
	}
	
	
	/**
	 * Stops the flywheels
	 */
	public void stop()
	{
		setSpeed(0);
	}
	
}
