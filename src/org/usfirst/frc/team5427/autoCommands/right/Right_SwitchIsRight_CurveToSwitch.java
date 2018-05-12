package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class Right_SwitchIsRight_CurveToSwitch extends Command {
	
	public static final double MIN_SPEED = .3;
	
	private double speed;
	
	private double rotationValue;
	
	private double angle;
	
	public Right_SwitchIsRight_CurveToSwitch() {
		
		speed = Config.PID_STRAIGHT_POWER_LONG;
		rotationValue = -.83;
		angle = 90;
		System.out.println("CURVING");
		
	}
	
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	public void execute() {
		if(speed > MIN_SPEED)
			this.speed/=1.15;
		
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
