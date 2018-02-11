package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class PIDPath extends Command {
	private PIDStraightMovement firstDistance, secondDistance, thirdDistance;
	private PIDTurn firstAngle, secondAngle;

	public PIDPath() {
		// creates all of the PID Commands
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0.3, 60);// 160
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 25);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0.3, 40);// 36
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 35);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0.3, 30);// 12
	}

	// begins the command
	public void initialize() {
		firstDistance.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
//		// If firstDistance, first angle, and secondDistance are all null and
//		// SecondAngle isFinished
//		// and the thirdDistance Command is not running, run the thirdDistance Command
//		if (null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
//			secondAngle.cancel();
//			secondAngle = null;
//			Robot.ahrs.reset();
//			thirdDistance.start();
//		}
//		
//		// If firstDistance, first angle are all null and secondDistance isFinished &&
//		// not null
//		// and the secondAngle Command is not running, run the secondAngle Command
//		else if (null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning()) {
//			secondDistance.cancel();
//			secondDistance = null;
//			Robot.ahrs.reset();
//			secondAngle.start();
//		}
//		
//		// If firstDistance is null and firstAngle isFinished && not null
//		// and the secondDistance Command is not running, run the secondDistance Command
//		else if (null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
//			firstAngle.cancel();
//			firstAngle = null;
//			Robot.ahrs.reset();
//			secondDistance.start();
//		}
//		
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
//		else if (null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
//			firstDistance.cancel();
//			firstDistance = null;
//			Robot.ahrs.reset();
//			firstAngle.start();
//		}
		if (null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
//			firstAngle.start();
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
