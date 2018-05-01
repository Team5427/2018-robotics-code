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
	* Range from -1.0 to 1.0.
	*/
	public double speed;
	
	/**
	* The max speed for the robot to travel at along the x axis.
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
	
	public Center_SwitchIsLeft_CoastToSwitch coasting;
	
	/**
	* TODO Add speed and rotationValue to config and change to real values.
	*/
	public Center_SwitchIsLeft_Curve()
	{
		speed = 0.1;
		firstRotationValue = -0.4;
		secondRotationValue = 0.45;
		hasReachedMiddle = false;
//		coasting = new Center_SwitchIsLeft_CoastToSwitch();
	}
	@Override
	public void initialize()
	{
		System.out.println("center switch is left");
		Robot.ahrs.reset();
		this.setTimeout(7);
	}
	
	
	@Override
	public void execute()
	{
		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		SmartDashboard.putNumber("Speed", this.speed);

		if(hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) < 17) 
		{
			Robot.driveTrain.drive.stopMotor();
		}
		else {
		if(!hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) > 86)
		{
//			new Center_SwitchIsLeft_MoveElevatorAuto().start();
			hasReachedMiddle = true;
		}
		if(speed < MAX_SPEED)
			this.speed*=1.035;
		if(!hasReachedMiddle)
		{
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.firstRotationValue,false);
		}
		else
		{
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.secondRotationValue,false);
		}
		}
	}
	
	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return false by default but can be modified in each child path.
	 */
	@Override
	public boolean isFinished() {
		return false; //(this.isTimedOut());
	}

	/**
	 * This is run once when the command is finished.
	 */
	@Override
	public void end() {
		System.out.println("Ending Main Curve");
		System.out.println("CE: Auto Path Exists: "+ (Robot.autoPath != null));
		System.out.println("CE: Auto Path is Running: " + (Robot.autoPath.isRunning()));
		System.out.println("CE: Auto Path is Finished: " + (Robot.autoPath.isFinished()));
		System.out.println("CE: Auto Path 2 Exists: " + (Robot.autoPath2 != null));
		System.out.println("CE: Auto Path 2 is Not Running: " + (!Robot.autoPath2.isRunning()) + "\n");
		Robot.driveTrain.drive.stopMotor();
		Robot.ahrs.reset();
		Robot.encLeft.reset();
		super.end();
		System.out.println("CEF: Auto Path Exists: "+ (Robot.autoPath != null));
		System.out.println("CEF: Auto Path is Running: " + (Robot.autoPath.isRunning()));
		System.out.println("CEF: Auto Path is Finished: " + (Robot.autoPath.isFinished()));
		System.out.println("CEF: Auto Path 2 Exists: " + (Robot.autoPath2 != null));
		System.out.println("CEF: Auto Path 2 is Not Running: " + (!Robot.autoPath2.isRunning()) + "\n");
	}
}
