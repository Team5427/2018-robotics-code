package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class Right_ScaleIsRight_CurveToScale extends Command {
	

	/**
	 * Lowest speed robot will go to.
	 */
	public static final double MIN_SPEED = .3;
	
	/*
	 * Speed of robot, being sent to curvature drive.
	 */
	private double speed;
	
	/*
	 * rotation value of curvature drive.
	 */
	private double rotationValue;
	
	/*
	 * angle that curve ends and robot stops.
	 */
	private double angle;
	
	/*
	 * Sets speed to .7 as left off by first distance.
	 * Sets rotation value .4 to left.
	 * Sets angle to aim for at 40 degrees.
	 */
	public Right_ScaleIsRight_CurveToScale() {
		
		speed = Config.PID_STRAIGHT_POWER_LONG;
		rotationValue = -.6;
		angle = 75;
		
	}
	
	/*
	 * Resets ahrs
	 * @see edu.wpi.first.wpilibj.command.Command#initialize()
	 */
	@Override
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	/*
	 * Decrements speed by a factor of 1.03 until MinSpeed is reached every iteration.
	 * Drives robot on a curve at respective speed and rotation value.
	 * @see edu.wpi.first.wpilibj.command.Command#execute()
	 */
	@Override
	public void execute() {
		if(speed > MIN_SPEED)
			this.speed/=1.03;
		
		Robot.driveTrain.drive.curvatureDrive(this.speed, this.rotationValue, false);
	}
	
	/*
	 * @return when yaw is more than angle to be aimed for.
	 */
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.ahrs.getYaw()) > angle ;
	}
	
	/*
	 * Stops motor.
	 */
	@Override
	public void end() {
		Robot.driveTrain.drive.stopMotor();
	}
	

}
