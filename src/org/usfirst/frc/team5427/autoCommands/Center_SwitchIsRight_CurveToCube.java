package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Center_SwitchIsRight_CurveToCube extends Command{
	/**
	* The desired speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public double speed;
	
	/**
	* The max speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public static final double MAX_SPEED = .5;
	
	/**
	* The value to input into the DifferentialDrive method arcadeDrive in order to determine how much the robot should curve during its path.
	* Range from -1.0 to 1.0.
	*/
	public double rotationValue;
	
	public Center_SwitchIsRight_CurveToCube() {
		speed = 0.1;
		rotationValue = -0.7;
	}
	
	@Override
	public void initialize() {
		Robot.ahrs.reset();
	}

	@Override
	public void execute() {
		if(speed < MAX_SPEED)
			this.speed*=1.035;
		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue,false);
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.ahrs.getYaw()>40;
	}
	
	@Override
	public void end() {
		Robot.driveTrain.drive.stopMotor();
	}
}
