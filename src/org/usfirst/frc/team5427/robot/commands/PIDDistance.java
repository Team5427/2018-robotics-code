package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * This class is adapted from the WPILib PIDCommand Class. This class is made to
 * make the robot drive a certain distance given a maximum power. It works by
 * allowing the maximum output of the PID loop to be the maximum power and
 * allowing it smoothly reach the desired distance. The other side of the drive
 * train is continually controlled by the PID loop in PIDStraightMovement.
 * 
 * @author Blake Romero
 */
@SameLine
public class PIDDistance extends PIDCommand {
	/**
//	 * The SpeedControllers that contain the motors that are controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgPIDControlled;

	/**
	 * The SpeedControllers that contain the motors that are not controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgNot;

	/**
	 * The timer that determines when the robot has been within a certain range of
	 * distance for half of a second.
	 */
	private Timer timer;

	/**
	 * The value that determines if the timer has started.
	 */
	boolean timerStarted;

	/**
	 * The distance we want the robot to travel
	 */
	double desiredDistance;

	/**
	 * The maximum speed the PIDController can output to the motors on the robot.
	 */
	double maximumSpeed;

	/**
	 * Creates the PIDController for this command using received PID values and sets
	 * the parameters of its control.
	 * 
	 * @param scgPIDControlled
	 *            the side of the robot that we are controlling with this
	 *            PIDCommand.
	 * @param maximumSpeed
	 *            the maximum speed that the robot will travel at.
	 * @param desiredDistance
	 *            the distance that we want to travel.
	 * @param p
	 *            the P value for this PIDController.
	 * @param i
	 *            the I value for this PIDController.
	 * @param d
	 *            the D value for this PIDController
	 */
	public PIDDistance(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgNot, double maximumSpeed, double desiredDistance, double p, double i, double d) {
		super(p, i, d, Config.PID_UPDATE_PERIOD);

		this.desiredDistance = desiredDistance;
		this.scgPIDControlled = scgPIDControlled;
		this.maximumSpeed = maximumSpeed;

		super.getPIDController().setOutputRange(-maximumSpeed, maximumSpeed);
		super.getPIDController().setSetpoint(desiredDistance);

		this.scgNot = scgNot;

		timer = new Timer();
	}

	/**
	 * Command implemented from PIDCommand This is called automatically after the
	 * constructor of the command is run. We only use this to start the
	 * PIDController of moving a certain distance.
	 */
	@Override
	protected void initialize() {
		super.getPIDController().enable();
		timerStarted = false;
	}

	/**
	 * Command implemented from PIDCommand This returns the value to be used by the
	 * PID loop. We are returning the distance traveled by the robot as measured by
	 * the encoders.
	 */
	@Override
	protected double returnPIDInput() {
		return (Math.abs(Robot.encLeft.getDistance()));
	}

	/**
	 * Command implemented from PIDCommand This is sent the output from the PID loop
	 * for us to use. We are setting the side of the robot that we control in this
	 * PID loop to the output to travel a certain distance.
	 * 
	 * @param output
	 *            The output from the PID loop
	 */
	@Override
	protected void usePIDOutput(double output) {
//		SmartDashboard.putNumber("PID Output Coasting", output);
		this.scgPIDControlled.pidWrite(output);
	}

	/**
	 * Command implemented from PIDCommand. When this returns true, the command runs
	 * end() Our method returns true if the robot has traveled close enough to the
	 * certain distance, with our tolerance.
	 * 
	 * @return if the robot has been within a range of inches for half of a second.
	 */
	@Override
	public boolean isFinished() {
		// TODO NOT untested !

		double distFromSetpoint = Math.abs(desiredDistance - (Math.abs(Robot.encLeft.getDistance())));
		boolean inRange = distFromSetpoint < Config.PID_DISTANCE_TOLERANCE;

		if (inRange && Math.abs(Robot.ahrs.getYaw()) < 3) {

			if (!timerStarted) {
				timer.reset();
				timer.start();
				timerStarted = true;
			}

			else if (timer.get() > 0.1 && timerStarted) { return true; }
		}
		else {
			timer.reset();
			timerStarted = false;
		}
		return false;
	}

	/**
	 * Command implemented from PIDCommand This is called whenever either
	 * isFinished() returns true or interrupted() runs. We use this to end the PID
	 * loop, reset the encoders' distances, and set the speed of our
	 * SpeedController.
	 */
	@Override
	public void end() {
		scgPIDControlled.set(0);
		scgNot.set(0);

		super.end();
		free();
	}

	/**
	 * Command implemented from PIDCommand This is called manually and is meant to
	 * disable the PID loop and reset values. We are using this to disable the PID
	 * loop, reset the PID loop and the encoders' distances, and set the speed of
	 * the side of the robot that we control to 0;
	 */
	@Override
	public void free() {
		super.free();
		super.getPIDController().disable();
		super.getPIDController().reset();

		scgPIDControlled.set(0);
	}

	/**
	 * Returns the average distance between the 2 encoders
	 * 
	 */
	public double getDistance() {
		return Math.abs(Robot.encLeft.getDistance());
	}
}