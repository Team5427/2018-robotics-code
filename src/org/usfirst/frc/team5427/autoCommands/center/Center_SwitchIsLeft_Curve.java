package org.usfirst.frc.team5427.autoCommands.center;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.util.SameLine;

/**
 * This is the class to navigate to the left switch from the center position
 * utilizing arcs.
 * 
 * @author Akshat Jain, Varsha Kumar, Blake Romero
 */
@SameLine
public class Center_SwitchIsLeft_Curve extends AutoPath {
	/**
	 * The desired speed for the robot to travel at along the x axis. Range from
	 * -1.0 to 1.0
	 */
	public double speed;

	/**
	 * The max speed for the robot t travel at along the x axis. Range from -1.0 to
	 * 1.0.
	 */
	public static final double MAX_SPEED = .45;

	/**
	 * The value to input into the DifferentialDrive method arcadeDrive in order to
	 * determine how much the robot should curve during the first segment of its
	 * path. Range from -1.0 to 1.0.
	 */
	public double firstRotationValue;

	/**
	 * The value to input into the DifferentialDrive method arcadeDrive in order to
	 * determine how much the robot should curve during the second segment of its
	 * path. Range from -1.0 to 1.0.
	 */
	public double secondRotationValue;

	/**
	 * Stores if the robot has reached the middle of its path.
	 */
	public boolean hasReachedMiddle;

	/**
	 * Stores if the robot is coasting.
	 */
	public boolean isCoasting;

//	public Fidget fidget;
	public boolean transition = false;

	/**
	 * TODO Add speed and rotationValue to config and change to real values.
	 */
	public Center_SwitchIsLeft_Curve() {
		requires(Robot.driveTrain);
		speed = 0.1;
		firstRotationValue = -0.65;
		secondRotationValue = 0.86;
		hasReachedMiddle = false;
		isCoasting = false;
//		fidget=null;
//		fidget = new Fidget();

	}

	@Override
	public void initialize() {
//		fidget.start();
		Robot.ahrs.reset();
	}

	/**
	 * Increments speed every iteration exponentially by a factor of 1.035. Runs the
	 * first curve at the speed and the rotation value 0.4 to the left. When 86
	 * degrees is reached, switches to second curve and raises elevator.
	 */
	@Override
	public void execute() {
//		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
//		SmartDashboard.putNumber("Speed", this.speed);
////		if (fidget == null || fidget.isFinished()) {
////			if(fidget!=null) {
////				fidget.cancel();
////				fidget = null;
////			}
//		if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
//			fidget.cancel();
//			fidget = null;
//			Robot.encLeft.reset();
//			firstDistance.start();
//		}
		
		
		{
			// switch curves
			if (!hasReachedMiddle && Math.abs(Robot.ahrs.getYaw()) > 86) {
				new MoveElevatorAuto(1).start();
				hasReachedMiddle = true;
			}
			// first curve
			if (!hasReachedMiddle) {
				if (speed < MAX_SPEED)
					this.speed *= 1.035;
				Robot.driveTrain.drive.curvatureDrive(this.speed, this.firstRotationValue, false);
			}
			// second curve
			else {
				if (Math.abs(Robot.ahrs.getYaw()) > 8) {
					if (speed > 0.1)
						this.speed /= 1.01;
					Robot.driveTrain.drive.curvatureDrive(this.speed, this.secondRotationValue, false);
				}
				// slow down towards switch
				else {
					isCoasting = true;
					this.speed /= 1.07;
					Robot.driveTrain.drive.tankDrive(this.speed, this.speed);
				}
			}
		} 

	}

	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return true if robot is done with second curve and speed is low
	 */
	@Override
	public boolean isFinished() {
//		System.out.println("Motor speed value: "+Robot.motor_pwm_frontLeft.get());
		return (isCoasting && speed < 0.01);
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
		new AutoOutGo().start();
		// new Center_SwitchIsLeft_SecondCube().start();
	}
	
	@Override
	protected void interrupted() {
	}
}