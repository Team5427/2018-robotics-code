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
		this.getPIDController().setOutputRange(-0.02, 0.02);
		this.getPIDController().setSetpoint(desiredDistance);
	}

	@Override
	protected double returnPIDInput() {
		return (Robot.encLeft.getDistance()+Robot.encRight.getDistance())/2.0;
	}

	@Override
	protected void usePIDOutput(double output) {
		scgPIDControlled.set(output);
		scgConstant.set(Config.PID_STRAIGHT_COAST_POWER);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
