package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command will cause the robot to turn to an exact degree value using the
 * PID Loop TODO add a ramping function
 * 
 * @author Akshat Jain
 */
@SameLine
public class Right_ScaleIsRight_FirstAngle extends PIDCommand {
	// two SpeedControllerGroup objects to be controlled by this PID Loop
	private SpeedControllerGroup scgRight, scgLeft;
	private double time;
	private Timer timer;
	private boolean hasStarted;
	
	private double setPoint = -51;
	
	public Right_ScaleIsRight_FirstAngle(SpeedControllerGroup scgRight, SpeedControllerGroup scgLeft) {
		super(Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, Config.PID_UPDATE_PERIOD);
		this.scgRight = scgRight;
		this.scgLeft = scgLeft;
		
		// lets the PID Loop the range of the input (ahrs)
		super.setInputRange(-180, 180);
		super.setSetpoint(setPoint);
	
		scgRight.set(0.1);
		scgLeft.set(0.1);
		this.setTimeout(1.5);//TODO change to TIME
		timer = new Timer();
		if(Math.abs(Robot.ahrs.getYaw())>=Math.abs(this.getSetpoint())/2)
			hasStarted=true;
	}
	
	@Override
	protected void execute() {
		
	}

	// begins the PID loop (enables)
	public void initialize() {
		Robot.ahrs.reset();
		time = 0;
		super.getPIDController().enable();
	}

	// Ends (disables) the PID loop and stops the motors of the
	// SpeedControllerGroups
	public void end() {
		super.free();
		// super.getPIDController().disable();
		// super.getPIDController().free();
		scgRight.set(0);
		scgLeft.set(0);
		super.end();
	}

	// Code to run when this command is interrupted
	public void interrupted() {
		end();
	}

	// judge range by what the angle is right now, ex: 91 instead of 90, we want to
	// see if it flatlines
	@Override
	public boolean isFinished() {
		double tolerance = Math.abs(Math.abs(getCurrentAngle()) - Math.abs(super.getSetpoint()));
		boolean inRange = tolerance < Config.PID_TURN_TOLERANCE;
		if (inRange) {
//			System.out.println("In Range.");
			if (timer.get() == 0) {
				timer.start();
			}
			if (timer.get() > .5) {
				return true;
			}
		} else {
			timer.reset();
		}
		if(Robot.encLeft.getStopped()&&hasStarted)//TODO moves on if enc is stopped
		{
			return true;
		}
		return this.isTimedOut();
	}

	public double getCurrentAngle() {
		return Robot.ahrs.getYaw();
	}

	// return what the PID loop is supposed to read from (feedback value)
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	// set motor values with "output"
	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Yaw", getCurrentAngle());
		SmartDashboard.putNumber("Raw Yaw", getCurrentAngle());
		SmartDashboard.putNumber("PID Output", output);
		
//		System.out.println("Turn Command Running.");
		scgRight.pidWrite(output);
		
		scgLeft.pidWrite(output);
		
		SmartDashboard.putNumber("fl", Robot.motor_pwm_frontLeft.get());
		SmartDashboard.putNumber("rl", Robot.motor_pwm_rearLeft.get());
		SmartDashboard.putNumber("fr", Robot.motor_pwm_frontRight.get());
		SmartDashboard.putNumber("rr", Robot.motor_pwm_rearRight.get());
	}
}
