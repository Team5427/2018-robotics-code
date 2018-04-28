package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Right_ScaleIsRight_CurveToScale extends Command {
	
	public static final double MAX_SPEED = .3;
	
	private double speed;
	
	private double rotationValue;
	
	private double angle;
	
	public Right_ScaleIsRight_CurveToScale() {
		
		speed = .2;
		rotationValue = .4;
		angle = 40;
		
	}
	
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	public void execute() {
		if(speed < MAX_SPEED)
			this.speed*=1.035;
		
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
