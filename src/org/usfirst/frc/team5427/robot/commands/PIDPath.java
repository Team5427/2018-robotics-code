package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class PIDPath extends Command {
	private PIDStraightMovement firstDistance, secondDistance, thirdDistance;
	private PIDTurn firstAngle, secondAngle;

	//Values for 60 inches.
	public static final double p1 = 0.1;
	public static final double i1 = 0.0;
	public static final double d1 = 0.55;
	
	//Values for 40 inches.
	public static final double p2 = 0.08;
	public static final double i2 = 0.0;
	public static final double d2 = 0.55;
	
	//Values for 30 inches.
	public static final double p3 = 0.04;
	public static final double i3 = 0.0;
	public static final double d3 = 0.55;
	
	public PIDPath() {
		// creates all of the PID Commands
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 60, p1, i1, d1);// 160
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 25);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 40, p2, i2, d2);// 36
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 35);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 30, p3, i3, d3);// 12
	}

	// begins the command
	public void initialize() {
		firstDistance.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		// If firstDistance, first angle, and secondDistance are all null and
		// SecondAngle isFinished
		// and the thirdDistance Command is not running, run the thirdDistance Command
		if (null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
			System.out.println("Part 4 Done.");
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			thirdDistance.start();
		}
		
		// If firstDistance, first angle are all null and secondDistance isFinished &&
		// not null
		// and the secondAngle Command is not running, run the secondAngle Command
		else if (null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning()) {
			System.out.println("Part 3 Done.");
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			secondAngle.start();
		}
		
		// If firstDistance is null and firstAngle isFinished && not null
		// and the secondDistance Command is not running, run the secondDistance Command
		else if (null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
			System.out.println("Part 2 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			secondDistance.start();
		}
		
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
		else if (null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			firstAngle.start();
		}
	}

	@Override
	protected boolean isFinished() {
		// returns if the last distance has finished
//		if (thirdDistance != null)
//			return thirdDistance.isFinished();
		
		if (firstDistance != null)
			return firstDistance.isFinished();
		return false;
	}
	
	// @Override
	// protected void end() {
	// firstAngle.free();
	// firstDisance
	// }
	
	@Override
	protected void end() {
		super.end();
	}
}
