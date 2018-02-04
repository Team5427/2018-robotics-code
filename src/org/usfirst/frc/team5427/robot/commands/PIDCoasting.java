package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Author - Blake
@SameLine
public class PIDCoasting extends PIDCommand {

	SpeedControllerGroup scgPIDControlled;
	PIDDriveTrainSide pidStraight;
	double desiredDistance;

	public PIDCoasting(SpeedControllerGroup scgPIDControlled, double desiredDistance, PIDDriveTrainSide pidStraight) {
		super(Config.PID_STRAIGHT_COAST_P, Config.PID_STRAIGHT_COAST_I, Config.PID_STRAIGHT_COAST_D);
		this.desiredDistance = desiredDistance;
		this.scgPIDControlled = scgPIDControlled;
		this.pidStraight = pidStraight;
		free();
		super.setSetpoint(desiredDistance);
		super.getPIDController().setOutputRange(-0.025, 0.025);
	}

	@Override
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
	}

	@Override
	protected boolean isFinished() {
		//TODO range like turn
		
		return Math.abs(returnPIDInput()-desiredDistance)<Config.PID_TURN_TOLERANCE;
	}

	@Override
	protected void end() {
		super.getPIDController().disable();
		free();
		super.getPIDController().reset();
	}

	@Override
	public void free() {
		super.free();
		super.getPIDController().disable();
		scgPIDControlled.set(0);
		super.getPIDController().reset();
	}

}
