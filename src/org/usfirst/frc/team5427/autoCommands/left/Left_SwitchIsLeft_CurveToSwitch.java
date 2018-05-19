package org.usfirst.frc.team5427.autoCommands.left;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class Left_SwitchIsLeft_CurveToSwitch extends Command {
	
	public static final double MIN_SPEED = .3;
	
	private double speed;
	
	private double rotationValue;
	
	private double angle;
	
	public Left_SwitchIsLeft_CurveToSwitch() {
		
		speed = Config.PID_STRAIGHT_POWER_LONG;
		rotationValue = .8;
		angle = 90;
//		System.out.println("CURVING");
		
	}
	
	@Override
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	@Override
	public void execute() {
		if(speed > MIN_SPEED)
			this.speed/=1.15;
		
		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue, false);
	}
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.ahrs.getYaw()) > angle ;
	}
	
	@Override
	public void end() {
		Robot.driveTrain.drive.stopMotor();
	}
	

}
