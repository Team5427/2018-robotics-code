package org.usfirst.frc.team5427.robot.OurClasses;

import edu.wpi.first.wpilibj.Talon;

/**
 * <p>
 * This is team 5427's version of <code>edu.wpi.first.wpilibj.Talon</code> that
 * is given by the WPI libraries.
 * It is crucial to ALWAYS use this class as opposed to ever using the Talon
 * Class from WPI. This is done to mitigate the number of errors in the code,
 * and keep a certain standard throughout the program.
 * </p>
 * <p>
 * <code>org.usfirst.frc.team5427.robot.ourClasses.SteelTalon</code> retains all
 * of the basic functionality of the class given by WPI, but adds the ability to
 * add any offsets to help ensure that the robot is as precise as can be.
 * </p>
 * 
 * @author Andrew Kennedy, Bo Corman
 *
 */
public class SteelTalon extends Talon {

	/**
	 * The doubles that will store the offsets for the motors, if any will be
	 * present.
	 */
	double backwardoffset, forwardOffset;
	
	/**
	 * The doubles that will store the bias for the motors. This value will be
	 * between 0.0-1.0, and will slow down the motor to the correct ratio.
	 */
	double backwardBias, forwardBias;

	/**
	 * Creates an instance of
	 * <code>org.usfirst.frc.team5427.robot.ourClasses.SteelTalon</code> that
	 * does not have any offsets. Usually used when the motor will not have to
	 * match the speed of another motor.
	 * 
	 * @param channel The PWM port that the speedcontroller is plugged into on the roborio.
	 */
	public SteelTalon(int channel) {
		super(channel);

		this.backwardoffset = 0;
		this.forwardOffset = 0;
		this.backwardBias = 1f;
		this.forwardBias = 1f;
	}

	/**
	 * 
	 * @param channel
	 * @param backwardOffset
	 * @param forwardOffset
	 */
	public SteelTalon(int channel, double backwardOffset, double forwardOffset) {
		super(channel);

		this.backwardoffset = backwardOffset;
		this.forwardOffset = forwardOffset;
		this.backwardBias = 1f;
		this.forwardBias = 1f;
	}

	/**
	 * 
	 * @param channel
	 * @param backwardOffset
	 * @param forwardOffset
	 */
	public SteelTalon(int channel, double backwardOffset, double forwardOffset, double backwardBias, double forwardBias) {
		super(channel);

		this.backwardoffset = backwardOffset;
		this.forwardOffset = forwardOffset;
		setBackwardBias(backwardBias);
		setForwardBias(forwardBias);
	}
	
	public void setBackwardOffset(double backwardOffset) {
		this.backwardoffset = backwardOffset;
	}

	public void setForwardOffset(double forwardOffset) {
		this.forwardOffset = forwardOffset;
	}

	public void setBackwardBias(double backwardBias) {
		this.backwardBias = normalizeBias(backwardBias);
	}
	
	public void setForwardBias(double forwardBias) {
		this.forwardBias = normalizeBias(forwardBias);
	}
	
	private double normalizeBias(double bias) {
		if (bias < 0f)
			return 0f;
		else if (bias > 1f)
			return 1f;
		
		return bias;
	}
	
	@Override
	public void set(double speed) {
		if (speed > .02) {
			speed *= forwardBias;
			speed += forwardOffset;
		}
		else if (speed < -.02) {
			speed *= backwardBias;
			speed -= backwardoffset;
		}

		/*
		 * ensures that the speed plus/minus the offset will not exceed the
		 * maximum values that .set can receive
		 */

		if (speed > 1)
			speed = 1;
		if (speed < -1)
			speed = -1;
		super.set(speed);
		Feed();
	}

}