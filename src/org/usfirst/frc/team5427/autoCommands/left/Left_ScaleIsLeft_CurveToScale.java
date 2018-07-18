package org.usfirst.frc.team5427.autoCommands.left;

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
<<<<<<< HEAD
<<<<<<< HEAD
=======
//		System.out.println("CURVING");
>>>>>>> e3766ce5eb7c17580a6faa2b66a9935b95114a2f
=======
>>>>>>> b89c7cf2296a955d609e9c3831f3e487e16c7d06
		
	}
	
	@Override
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	@Override
	public void execute() {
		if(speed > MIN_SPEED)
			this.speed/=1.07;
		
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
