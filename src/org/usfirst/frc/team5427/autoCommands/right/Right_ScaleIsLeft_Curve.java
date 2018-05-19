package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the class to navigate to the left switch from the center position utilizing arcs.
 * 
 * @author Akshat Jain, Varsha Kumar, Blake Romero
 */
@SameLine
public class Right_ScaleIsLeft_Curve extends AutoPath{
	/**
	* The desired speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0
	*/
	public double speed;
	
	/**
	* The max speed for the robot t travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public static final double MAX_SPEED = .4;
	
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
	* Stores if the robot is on its second curve.
	*/
	public boolean secondCurve;
	
	
	
	
	/**
	* TODO Add speed and rotationValue to config and change to real values.
	*/
	public Right_ScaleIsLeft_Curve()
	{
		speed = Config.PID_STRAIGHT_POWER_LONG;
		firstRotationValue = -0.35;
		secondRotationValue = 0.8;
		hasReachedMiddle = false;
		secondCurve = false;
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
		
		
		
		// switch first curve to forward distance.
		if(!hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) > 74)
		{
//			new Center_SwitchIsLeft_MoveElevatorAuto().start();
			hasReachedMiddle = true;
			setTimeout(3.8);
		}
		//first curve
		if(!hasReachedMiddle)
		{
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.firstRotationValue,false);
		}
		//middle distance
		else if(hasReachedMiddle && !secondCurve) {
			if(!this.isTimedOut()) {
				Robot.driveTrain.drive.tankDrive(this.speed,this.speed);
			}else {
				secondCurve = true;
			}
		}
		//second curve
		else
		{
			SmartDashboard.putNumber("Speed on Curve", speed);
			this.speed/=1.01;
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.secondRotationValue,false);
		}
	}
	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return true if robot is done with second curve and speed is low
	 */
	@Override
	public boolean isFinished() {
		return (speed < 0.1);
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
	}
}