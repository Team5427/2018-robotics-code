package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class Left_ScaleIsLeft_CurveToScale extends Command {
	
	public static final double MIN_SPEED = .3;
	
	private double speed;
	
	private double rotationValue;
	
	private double angle;
	
	public Left_ScaleIsLeft_CurveToScale() {
		
		speed = Config.PID_STRAIGHT_POWER_LONG;
		rotationValue = .3;
		angle = 14;
		System.out.println("CURVING");
		
	}
	
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	public void execute() {
		if(speed > MIN_SPEED)
			this.speed/=1.07;
		
		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue, false);
	}
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.ahrs.getYaw()) > angle ;
	}
	
	public void end() {
		Robot.driveTrain.drive.stopMotor();
	}
	

}
