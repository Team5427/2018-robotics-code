package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command turns the robot 90 degrees clockwise.
 * Used in the Right_SwitchIsLeft command.
 * 
 * @author Andrew Li, Akshat Jain
 */
@SameLine
public class Right_SwitchIsLeft_FirstAngle extends PIDCommand {
	/**
	 * Two SpeedControllerGroup objects to be controlled by this PID Loop
	 */
	private SpeedControllerGroup scgRight, scgLeft;

	/**
	 * The setpoint in degrees the PID loops wants to turn to
	 */
	double setPoint;

	/**
	 * Times the command for the isFinished method
	 */
	private Timer timer;

	/**
	 * Whether or not the turn has started
	 */
	private boolean hasStarted;

	/**
	 * Creates a PID loop that turns a specified amount of degrees This constructor
	 * uses the PID turn values in config The timeout is set to 1.5 Sets the
	 * starting speed to 0.1 for the speed ramp-up
	 * 
	 * @param scgRight
	 *            the speed controller group on the right side of the robot
	 * @param scgLeft
	 *            the speed controller group on the left side of the robot
	 */
	public Right_SwitchIsLeft_FirstAngle(SpeedControllerGroup scgRight, SpeedControllerGroup scgLeft) {
		super(Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, Config.PID_UPDATE_PERIOD);
		this.scgRight = scgRight;
		this.scgLeft = scgLeft;
		setPoint = -90;

		super.setInputRange(-180, 180);
		super.setSetpoint(setPoint);

		scgRight.set(0.1);
		scgLeft.set(0.1);

		this.setTimeout(1.5);
		timer = new Timer();

		if (Math.abs(Robot.ahrs.getYaw()) >= Math.abs(this.getSetpoint()) / 2)
			hasStarted = true;
	}

	/**
	 * Resets the ahrs and enables the PID loop
	 */
	public void initialize() {
		Robot.ahrs.reset();
		super.getPIDController().enable();
	}

	/**
	 * Sets the left and right motors to 0 and ends the PID loop
	 */
	public void end() {
		super.free();
		scgRight.set(0);
		scgLeft.set(0);
		super.end();
	}

	/**
	 * Ends the command when this command is interrupted
	 */
	public void interrupted() {
		end();
	}

	/**
	 * Stops the PID loop when it no longer needs to run
	 * 
	 * @return true if the robot has been within the setpoint for at least half a
	 *         second, if the timeout is reached, or if the encoder has stopped
	 */
	@Override
	public boolean isFinished() {
		double tolerance = Math.abs(Math.abs(getCurrentAngle()) - Math.abs(super.getSetpoint()));
		boolean inRange = tolerance < Config.PID_TURN_TOLERANCE;
		if (inRange) {
			if (timer.get() == 0) {
				timer.start();
			}
			if (timer.get() > .5) { return true; }
		}
		else {
			timer.reset();
		}
		if (Robot.encLeft.getStopped() && hasStarted) { return true; }
		return this.isTimedOut();
	}

	/**
	 * Returns the current angle measured by the ahrs
	 * 
	 * @return the angle measured by the ahrs in degrees
	 */
	public double getCurrentAngle() {
		return Robot.ahrs.getYaw();
	}

	/**
	 * Returns what the PID loop is supposed to read
	 * 
	 * @return the input from the PID loop
	 */
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	/**
	 * Sets the motor values to the output from the PID loop
	 * 
	 * @param output
	 *            the output from the PID loop to be used in the motors
	 */
	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Yaw", getCurrentAngle());
		SmartDashboard.putNumber("Raw Yaw", getCurrentAngle());
		SmartDashboard.putNumber("PID Output", output);

		scgRight.pidWrite(output);
		scgLeft.pidWrite(output);

		SmartDashboard.putNumber("Front Left", Robot.motor_pwm_frontLeft.get());
		SmartDashboard.putNumber("Rear Left", Robot.motor_pwm_rearLeft.get());
		SmartDashboard.putNumber("Front Right", Robot.motor_pwm_frontRight.get());
		SmartDashboard.putNumber("Rear Right", Robot.motor_pwm_rearRight.get());
	}
}
