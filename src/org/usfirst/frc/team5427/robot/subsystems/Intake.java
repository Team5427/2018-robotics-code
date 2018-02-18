/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.util.NextLine;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Varsha Subsystem for the two flywheels that turn in opposite
 *         directions
 */

@NextLine
public class Intake extends Subsystem
{
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private SpeedController leftFlywheel, rightFlywheel;
	private Timer timer;
	private boolean timerStarted;

	// motor biases
	public static final double LEFT_FLYWHEEL_BIAS_FORWARD = 1;
	public static final double LEFT_FLYWHEEL_BIAS_BACKWARD = 1;
	public static final double RIGHT_FLYWHEEL_BIAS_FORWARD = 1;
	public static final double RIGHT_FLYWHEEL_BIAS_BACKWARD = 1;
	// motor offsets
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
		timer = new Timer();
		timerStarted = false;
	}

	public void setSpeed(double speed)
	{
		if (speed < 0)// if the speed is negative
		{
			// left goes backward, right goes forward
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_BACKWARD + LEFT_FLYWHEEL_OFFSET_BACKWARD);
			rightFlywheel.set(-speed * RIGHT_FLYWHEEL_BIAS_FORWARD + RIGHT_FLYWHEEL_OFFSET_FORWARD);

		}
		// otherwise
		// left goes forward, right goes backward
		leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_FORWARD + LEFT_FLYWHEEL_OFFSET_FORWARD);
		rightFlywheel.set(-speed * RIGHT_FLYWHEEL_BIAS_BACKWARD + RIGHT_FLYWHEEL_OFFSET_BACKWARD);

	}
	
	public boolean setSpeedTime(double speed, double time)
	{
		if(!timerStarted) {
			timer.reset();
			timer.start();
			timerStarted=true;
		}
		if(timer.get() < time) {
			if (speed < 0)// if the speed is negative
			{
				// left goes backward, right goes forward
				leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_BACKWARD + LEFT_FLYWHEEL_OFFSET_BACKWARD);
				rightFlywheel.set(-speed * RIGHT_FLYWHEEL_BIAS_FORWARD + RIGHT_FLYWHEEL_OFFSET_FORWARD);
			}
			// otherwise
			// left goes forward, right goes backward
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_FORWARD + LEFT_FLYWHEEL_OFFSET_FORWARD);
			rightFlywheel.set(-speed * RIGHT_FLYWHEEL_BIAS_BACKWARD + RIGHT_FLYWHEEL_OFFSET_BACKWARD);
		}
		else
		{
			leftFlywheel.set(0);
			rightFlywheel.set(0);
			return true;
		}
		return false;
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
