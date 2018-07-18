package org.usfirst.frc.team5427.util;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * @author Abinav
 */
/**
 * This class is a substitute for RobotDrive, DifferentialDrive, or any other
 * Wpilib provided classes that extends RobotDriveBase. The class drives the
 * robot based on joystick position calculations. This class is a 5427 created
 * class.
 */
public class FalconDrive extends RobotDriveBase implements Sendable, MotorSafety {
	private SpeedController frontRight, frontLeft, rearRight, rearLeft;
	private SpeedControllerGroup left, right;
	private boolean safetyEnable;

	// Constructor if using SpeedControllers individually
	public FalconDrive(SpeedController frontRight, SpeedController frontLeft, SpeedController rearRight, SpeedController rearLeft) {
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.rearLeft = rearLeft;
		this.rearRight = rearRight;
	}

	// Constructor if using SpeedControllerGroups
	public FalconDrive(SpeedControllerGroup left, SpeedControllerGroup right) {
		this.left = left;
		this.right = right;
	}

	public void arcadeDrive(double y, double z) {// zRotation and xSpeed parameters
		z *= .6;
		y *= 1;
		/**
		 * This variable will be equal to the speed of the right side + the speed of the
		 * left side. It will be used in a systems of equations in order to calculate
		 * the right side.
		 */
		double v = (1 - Math.abs(z)) * y + y;
		/**
		 * This variable will be equal to the speed of the right side - the speed of the
		 * left side. It will be used in a systems of equations in order to calculate
		 * the left side.
		 */
		double w = (1 - Math.abs(y)) * z + z;
		/**
		 * Since v = R + L, and w = R - L we add the two variables together in order to
		 * get 2R + 0L, which we divide by two in order to get R.
		 */
		setRightSpeed((v + w) / 2);
		frontRight.set((v - w) / 2);
		rearRight.set((v - w) / 2);
		right.set(-(v - w) / 2);
		/*
		 * Since v = R + L, and w = R - L, we subtract the two variables in order to get
		 * 0R + 2L, which we then divide by two in order to get L.
		 */
		setLeftSpeed(-(v - w) / 2);
		frontLeft.set(-(v - w) / 2);
		rearLeft.set(-(v - w) / 2);
		left.set(-(v - w) / 2);
	}

	public void setLeftSpeed(double speed) {
		frontLeft.set(speed);
		rearLeft.set(speed);
		left.set(speed);
	}

	public void setRightSpeed(double speed) {
		frontRight.set(speed);
		rearRight.set(speed);
		right.set(speed);
	}

	@Override
	public void setExpiration(double timeout) {}

	@Override
	public double getExpiration() {
		return 0;
	}

	@Override
	public boolean isAlive() {
		return false;
	}

	@Override
	public void stopMotor() {}

	@Override
	public void setSafetyEnabled(boolean enabled) {
		safetyEnable = enabled;
	}

	@Override
	public boolean isSafetyEnabled() {
		return safetyEnable;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		// TODO Auto-generated method stub

	}

}
