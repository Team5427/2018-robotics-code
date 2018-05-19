package org.usfirst.frc.team5427.autoCommands.center;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.command.Command;

public class Center_SwitchIsLeft_Coast extends Command {
	
	/*
	 * Speed of robot, being sent to curvature drive.
	 */
	private double speed;
	
	/*
	 * Sets speed to .7 as left off by first distance.
	 * Sets rotation value .4 to left.
	 * Sets angle to aim for at 40 degrees.
	 */
	public Center_SwitchIsLeft_Coast() {
		
		speed = 0.1;		
	}
	
	/*
	 * Resets ahrs
	 * @see edu.wpi.first.wpilibj.command.Command#initialize()
	 */
	public void initialize() {
		Robot.ahrs.reset();
	}
	
	/*
	 * Decrements speed by a factor of 1.03 until MinSpeed is reached every iteration.
	 * Drives robot on a curve at respective speed and rotation value.
	 * @see edu.wpi.first.wpilibj.command.Command#execute()
	 */
	public void execute() {
		this.speed/=1.128;
		Robot.driveTrain.drive.tankDrive(this.speed, this.speed);
	}
	
	/*
	 * @return when yaw is more than angle to be aimed for.
	 */
	@Override
	protected boolean isFinished() {
		return this.speed < 0.01;
	}
	
	/*
	 * Stops motor.
	 */
	public void end() {
		Robot.driveTrain.drive.stopMotor();
	}
	

}
