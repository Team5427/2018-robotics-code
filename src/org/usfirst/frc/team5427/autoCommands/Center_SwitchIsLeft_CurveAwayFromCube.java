package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Center_SwitchIsLeft_CurveAwayFromCube extends Command{
	/**
	* The desired speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public double speed;
	
	/**
	* The max speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public static final double MAX_SPEED = -.5;
	
	/**
	* The value to input into the DifferentialDrive method arcadeDrive in order to determine how much the robot should curve during its path.
	* Range from -1.0 to 1.0.
	*/
	public double rotationValue;
	
	public Center_SwitchIsLeft_CurveAwayFromCube() {
		speed = -0.3;
		rotationValue = -0.4;
	}
	
	@Override
	public void initialize() {
		Robot.ahrs.reset();
		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue,false);
	}

	@Override
	public void execute() {
		System.out.println("Curving Away From Cube");
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.ahrs.getYaw()<-40;
	}
	
	@Override
	public void end() {
		System.out.println("Ending Curve Away From Cube");
		Robot.driveTrain.drive.stopMotor();
	}
}
