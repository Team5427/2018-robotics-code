package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDCoasting extends PIDCommand{

	SpeedControllerGroup scgPIDControlled, scgConstant;
	double desiredDistance;
	
	public PIDCoasting(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double desiredDistance, double p, double i, double d) {
		super(p, i, d);
		this.desiredDistance = desiredDistance;
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		super.getPIDController().setOutputRange(-0.02, 0.02);
		super.getPIDController().setSetpoint(desiredDistance);
	}

	protected void initialize() {
		this.scgConstant.set(Config.PID_STRAIGHT_COAST_POWER);
		super.getPIDController().enable();
	}
	
	@Override
	protected double returnPIDInput() {
		return (Robot.encLeft.getDistance()+Robot.encRight.getDistance())/2.0;
	}

	@Override
	protected void usePIDOutput(double output) {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
