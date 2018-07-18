package org.usfirst.frc.team5427.autoCommands.left;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.PIDDistance;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * This command moves the robot forwards 250 inches.
 * Used in the Right_ScaleIsRight command.
 * 
 * @author Akshat Jain
 */
public class Left_ScaleIsLeft_FirstDistance extends PIDCommand {

	/**
	 * PIDCommand created to control distance after we have reached the maximum
	 * power in this controller.
	 */
	private PIDDistance pidDistance;

	/**
	 * Stores whether the PIDController has started acting on the robot.
	 */
	private boolean hasStarted;

	/**
	 * The SpeedControllers that contain the motors that are controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgPIDControlled;

	/**
	 * The SpeedControllers that contain the motors that are not controlled by the
	 * PIDController within this command.
	 */
	private SpeedControllerGroup scgNot;

	/**
	 * The distance we want the robot to travel
	 */
	double desiredDistance;

	/**
	 * The maximum speed the PIDController can output to the motors on the robot.
	 */
	double maximumSpeed;

	/**
	 * The current speed of the SpeedControllerGroups when the PIDController have
	 * not been activated.
	 */
	private double power;

	/**
	 * Creates the PIDController for this command using config PID values and sets
	 * the parameters of its control.
	 * 
	 * @param scgPIDControlled
	 *            the side of the robot that we are controlling with this
	 *            PIDCommand.
	 * @param scgConstant
	 *            the side of the robot that we will control with the PIDDistance
	 *            command.
	 */
	public Left_ScaleIsLeft_FirstDistance(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, Config.PID_UPDATE_PERIOD);

		this.scgPIDControlled = scgPIDControlled;
		this.scgNot = scgConstant;
		maximumSpeed = Config.PID_STRAIGHT_POWER_LONG;
		desiredDistance = 205;
		
		this.setInterruptible(true);
		this.getPIDController().setSetpoint(0);
		setSetpoint(0);
		
		

		hasStarted = false;
	}

	/*
	 * Command implemented from PIDCommand This is called automatically after the
	 * constructor of the command is run. We only use this to start the
	 * PIDController of moving straight.
	 */
	@Override
	protected void initialize() {
		super.getPIDController().enable();
		this.pidDistance = null;
		Robot.encLeft.reset();
		Robot.ahrs.reset();
		power = .01;
		hasStarted = false;
	}

	/**
	 * Command implemented from PIDCommand This returns the value to be used by the
	 * PID loop. We are returning the angle received from the NavX on the robot.
	 */
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	/**
	 * Command implemented from PIDCommand This is sent the output from the PID loop
	 * for us to use. We are setting the side of the robot that we control in this
	 * PID loop to the output to maintain the angle of 0 to go straight. After we
	 * reach maximum power, we activate the PIDDistance command in order to finish
	 * traveling a certain distance.
	 * 
	 * @param output
	 *            the output of the PIDController within PIDStraightMovement when
	 *            given our current Yaw.
	 */
	@Override
	protected void usePIDOutput(double output) {
		scgPIDControlled.pidWrite(output);
		if (Robot.encLeft.getDistance() >= this.desiredDistance) {
			if (scgNot.get() == 0)
				scgNot.set(0);
		}
		else if (null == pidDistance)
			scgNot.set(power);

		if (this.power < this.maximumSpeed && null == pidDistance) {
			this.power += (Config.PID_STRAIGHT_LINEAR_INCREMENT-.001);
		}
		if (power >= this.maximumSpeed / 4)
			hasStarted = true;
	}

	/**
	 * Command implemented from PIDCommand. When this returns true, the command runs
	 * end() Our method returns true if the robot has traveled close enough to the
	 * certain distance, with our tolerance.
	 * 
	 * @return if the PIDDistance command has finished.
	 */
	@Override
	public boolean isFinished() {
//		if (pidDistance != null && pidDistance.isFinished() && Math.abs(Robot.ahrs.getYaw()) < 3) {
//			pidDistance.end();
//			end();
//			return true;
//		}
		if(Math.abs(Robot.encLeft.getDistance()) >= this.desiredDistance) {
			end();
			return true;
		}
		else if ((Robot.encLeft.getStopped()) && hasStarted) {
			if (null != pidDistance)
				pidDistance.end();
			end();
			return true;
		}
		return false;
	}

	/**
	 * Command implemented from PIDCommand This is called whenever this command is
	 * interrupted by something else. We do not use this for anything purposeful, so
	 * we just call end().
	 */
	@Override
	protected void interrupted() {
		end();
	}

	/**
	 * Command implemented from PIDCommand This is called whenever either
	 * isFinished() returns true or interrupted() runs. We use this to end the PID
	 * loop, reset the encoders' distances, and set the speed of our
	 * SpeedController.
	 */
	@Override
	public void end() {
		free();
		super.end();
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
	}
}
