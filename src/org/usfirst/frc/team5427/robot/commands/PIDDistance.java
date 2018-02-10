package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDistance extends PIDCommand {
	SpeedControllerGroup scgPIDControlled, scgConstant;
	double desiredDistance;
	private Timer timer;

	public PIDDistance(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double desiredDistance) {
		super(Config.PID_STRAIGHT_DISTANCE_P, Config.PID_STRAIGHT_DISTANCE_I, Config.PID_STRAIGHT_DISTANCE_D);
		this.desiredDistance = desiredDistance;
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		super.getPIDController().setOutputRange(-0.3, 0.3);
		super.getPIDController().setSetpoint(desiredDistance);
		timer = new Timer();
	}

	protected void initialize() {
		super.getPIDController().enable();
	}

	@Override
	protected double returnPIDInput() {
		return (Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2.0;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("PID Output Coasting", output);
//		if (desiredDistance > (Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2.0) {
//			this.scgConstant.set(Config.PID_STRAIGHT_COAST_POWER);
//		}
//		else if (desiredDistance < (Math.abs(Robot.encLeft.getDistance()) + Math.abs(Robot.encRight.getDistance())) / 2.0) {
//			this.scgConstant.set(-Config.PID_STRAIGHT_COAST_POWER);
//		}
		this.scgPIDControlled.pidWrite(output);
	}

	@Override
	protected boolean isFinished() {
		// TODO range like turn
		double tolerance = Math.abs(returnPIDInput() - super.getSetpoint());
		boolean inRange = tolerance < Config.PID_STRAIGHT_TOLERANCE;
		if (inRange) {
			if (timer.get() == 0)
				timer.start();
			if (timer.get() > 2) {
				return true;
			}
		}
		else {
			timer.reset();
		}
		return false;
		// return
		// Math.abs(returnPIDInput()-desiredDistance)<Config.PID_STRAIGHT_TOLERANCE;
	}

	@Override
	public void free() {
		super.free();
		super.getPIDController().disable();
		scgPIDControlled.set(0);
		scgConstant.set(0);
		super.getPIDController().reset();
	}
}
