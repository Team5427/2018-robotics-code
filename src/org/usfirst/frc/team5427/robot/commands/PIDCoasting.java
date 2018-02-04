package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDCoasting extends PIDCommand{

	SpeedControllerGroup scgPIDControlled, scgConstant;
	double desiredDistance;
	
	public PIDCoasting(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double desiredDistance) {
		super(Config.PID_STRAIGHT_COAST_P,Config.PID_STRAIGHT_COAST_I,Config.PID_STRAIGHT_COAST_D);
		this.desiredDistance = desiredDistance;
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		super.getPIDController().setOutputRange(-0.02, 0.02);
		super.getPIDController().setSetpoint(desiredDistance);
	}

	protected void initialize() {
		super.getPIDController().enable();
	}
	
	@Override
	protected double returnPIDInput() {
		return (Math.abs(Robot.encLeft.getDistance())+Math.abs(Robot.encRight.getDistance()))/2.0;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("PID Output Coasting", output);
		 if (desiredDistance > (Math.abs(Robot.encLeft.getDistance()) +
		 Math.abs(Robot.encRight.getDistance())) / 2.0) {
			 this.pidStraight.setPower(Config.PID_STRAIGHT_COAST_POWER);
		 }
		 else if (desiredDistance <
		 (Math.abs(Robot.encLeft.getDistance())+Math.abs(Robot.encRight.getDistance()))/2.0)
		 {
			 this.pidStraight.setPower(-Config.PID_STRAIGHT_COAST_POWER);
		 }
		this.scgPIDControlled.pidWrite(output);

		System.out.println("Running PIDCoasting");
		
	}

	@Override
	protected boolean isFinished() {
		//TODO range like turn
		
		return Math.abs(returnPIDInput()-desiredDistance)<Config.PID_TURN_TOLERANCE;
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
