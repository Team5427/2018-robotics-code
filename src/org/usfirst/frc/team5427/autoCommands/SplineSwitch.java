package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is the class to navigate to the left switch from the center position utilizing arcs.
 * 
 * @author Varsha Kumar, Blake Romero
 */
@SameLine
public class SplineSwitch extends AutoPath{
	/**
	* The desired speed for the robot to travel at along the x axis.
	* Range from -1.0 to 1.0.
	*/
	public double speed;
	
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
	public SplineSwitch()
	{
		speed = 0.3;
		firstRotationValue = -0.53;
		secondRotationValue = 0.53;
		hasReachedMiddle = false;
	}
	@Override
	public void initialize()
	{
		System.out.println("center switch is left");
		Robot.ahrs.reset();
	}
	
	
	@Override
	public void execute()
	{
		if(!hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) > 88)
		{
			hasReachedMiddle = true;
		}
		if(!hasReachedMiddle)
		{
			System.out.println("trying to drive first rotation");
			Robot.driveTrain.drive_Left.set(.5);
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.firstRotationValue,false);
			
		}
		else
		{
			//arcade drive turns in place
			Robot.driveTrain.drive.curvatureDrive(this.speed, this.secondRotationValue,false);
		}
	}
	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return false by default but can be modified in each child path.
	 */
	@Override
	public boolean isFinished() {
		return (hasReachedMiddle && Robot.ahrs.getYaw() < -2);
	}

	/**
	 * This is run once when the command is finished.
	 */
	@Override
	protected void end() {
		Robot.driveTrain.drive.stopMotor();
		super.end();
	}
}
