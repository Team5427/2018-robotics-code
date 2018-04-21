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
 * The subsystem that controls the two motors of the intake.
 * 
 * @author Varsha Kumar
 */

@SameLine
public class Intake extends Subsystem {
	/**
	 * The SpeedController that controls the left motor of the intake.
	 */
	private SpeedController leftFlywheel;
	
	/**
	 * The SpeedController that controls the right motor of the intake.
	 */
	private SpeedController rightFlywheel;

	/**
	 * The motor bias for the left motor of the intake in the forward direction.
	 */
	public static final double LEFT_FLYWHEEL_BIAS_FORWARD = 1;
	
	/**
	 * The motor bias for the left motor of the intake in the backward direction.
	 */
	public static final double LEFT_FLYWHEEL_BIAS_BACKWARD = 1;
	
	/**
	 * The motor bias for the right motor of the intake in the forward direction.
	 */
	public static final double RIGHT_FLYWHEEL_BIAS_FORWARD = 1;
	
	/**
	 * The motor bias for the right motor of the intake in the backwards direction.
	 */
	public static final double RIGHT_FLYWHEEL_BIAS_BACKWARD = 1;

	/**
	 * The motor offset for the left motor of the intake in the forward direction.
	 */
	public static final double LEFT_FLYWHEEL_OFFSET_FORWARD = 0;
	
	/**
	 * The motor offset for the left motor of the intake in the backward direction.
	 */
	public static final double LEFT_FLYWHEEL_OFFSET_BACKWARD = 0;
	
	/**
	 * The motor offset for the right motor of the intake in the forward direction.
	 */
	public static final double RIGHT_FLYWHEEL_OFFSET_FORWARD = 0;
	
	/**
	 * The motor offset for the right motor of the intake in the backwards direction.
	 */
	public static final double RIGHT_FLYWHEEL_OFFSET_BACKWARD = 0;

	/**
	 * Assigns each SpeedController to its received motor.
	 * 
	 * @param leftFlywheel
	 *            the SpeedController of the left motor of the intake.
	 * @param rightFlywheel
	 *            the SpeedController of the right motor of the intake.
	 */
	public Intake(SpeedController leftFlywheel, SpeedController rightFlywheel) {
		this.leftFlywheel = leftFlywheel;
		this.rightFlywheel = rightFlywheel;
	}

	/**
	 * Sets the speed of each SpeedController to whatever the received speed parameter is.
	 * 
	 * @param speed
	 *            the desired power to set the intake to.
	 */
	public void setSpeed(double speed) {
		if (speed < 0) {
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_BACKWARD + LEFT_FLYWHEEL_OFFSET_BACKWARD);
			rightFlywheel.set(speed * RIGHT_FLYWHEEL_BIAS_FORWARD + RIGHT_FLYWHEEL_OFFSET_FORWARD);
		}
		else if (speed > 0) {
			leftFlywheel.set(speed * LEFT_FLYWHEEL_BIAS_FORWARD + LEFT_FLYWHEEL_OFFSET_FORWARD);
			rightFlywheel.set(speed * RIGHT_FLYWHEEL_BIAS_BACKWARD + RIGHT_FLYWHEEL_OFFSET_BACKWARD);
		}
		else {
			leftFlywheel.set(0);
			rightFlywheel.set(0);
		}
	}

	/**
	 * Unused method but required by extending SubSystem class
	 */
	public void initDefaultCommand() {
	}

	/**
	 * Stops the motors of the intake.
	 */
	public void stop() {
		setSpeed(0);
	}

}
