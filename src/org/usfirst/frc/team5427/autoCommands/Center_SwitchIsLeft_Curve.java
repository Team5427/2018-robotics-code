package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the class to navigate to the left switch from the center position utilizing arcs.
 * 
 * @author Varsha Kumar, Blake Romero, Akshat Jain
 */
@SameLine
public class Center_SwitchIsLeft_Curve extends AutoPath{
	/**
	* The desired speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0
	*/
	public double speed;
	
	/**
	* The max speed for the robot t travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public static final double MAX_SPEED = .5;
	
	/**
	* The value to input into the DifferentialDrive method arcadeDrive in order to determine how much the robot should curve during the first segment of its path.
	* Range from -1.0 to 1.0.
	*/
	public double firstRotationValue;
	
	/**
	* The value to input into the DifferentialDrive method arcadeDrive in order to determine how much the robot should curve during the second segment of its path.
	* Range from -1.0 to 1.0.
	*/
	public double secondRotationValue;
	
	/**
	* Stores if the robot has reached the middle of its path.
	*/
	public boolean hasReachedMiddle;
	
	/**
	* TODO Add speed and rotationValue to config and change to real values.
	*/
	public Center_SwitchIsLeft_Curve()
	{
		speed = 0.1;
		firstRotationValue = -0.4;
		secondRotationValue = 0.45;
		hasReachedMiddle = false;
	}
	@Override
	public void initialize()
	{
		Robot.ahrs.reset();
	}
	
	/**
	 * Increments speed every iteration exponentially
	 * by a factor of 1.035. Runs the first curve at the speed
	 * and the rotation value 0.4 to the left. When 86 degrees 
	 * is reached, switches to second curve and raises elevator.
	 */
	@Override
	public void execute()
	{
		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		SmartDashboard.putNumber("Speed", this.speed);

		if(!hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) > 86)
		{
			new Center_SwitchIsLeft_MoveElevatorAuto().start();
			hasReachedMiddle = true;
		}
		if(!hasReachedMiddle)
		{
			if(speed < MAX_SPEED)
				this.speed*=1.035;
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.firstRotationValue,false);
		}
		else if(Math.abs(Robot.ahrs.getYaw()) > 17)
		{
			if(speed < MAX_SPEED)
				this.speed/=1.04;
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.secondRotationValue,false);
		}
	}
	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return true if robot is on its second curve and within 17 degrees of straight.
	 */
	@Override
	public boolean isFinished() {
		return (hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) < 17);
	}

	/**
	 * Stops drive train (coasts). Resets ahrs and encoder, starts second cube auto.
	 */
	@Override
	protected void end() {
		Robot.driveTrain.drive.stopMotor();
		Robot.ahrs.reset();
		Robot.encLeft.reset();
		super.end();
		new Center_SwitchIsLeft_SecondCube().start();
	}
}
