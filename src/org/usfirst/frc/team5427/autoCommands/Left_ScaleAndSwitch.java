package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Left_ScaleAndSwitch extends AutoPath {
	private PIDStraightMovement firstDistance, secondDistance, thirdDistance;
	private PIDTurn firstAngle, secondAngle;
	private MoveElevatorAuto moveElevatorScale, moveElevatorSwitch;
	private Fidget fidget;
	private double startTime, currentTime;

	//Times
	public static final double timeOut1 = 15;

	public Left_ScaleAndSwitch() {
		// creates all of the PID Commands
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 154, 0, 0, 0);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 16, 0, 0, 0);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 16, 0, 0, 0);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
		moveElevatorSwitch = new MoveElevatorAuto(1); // 1 for switch
		moveElevatorScale = new MoveElevatorAuto(2); // 2 for scale
	}

	// begins the command
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();		
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevatorScale != null)
			moveElevatorScale.isFinished();
		if(moveElevatorSwitch != null)
			moveElevatorSwitch.isFinished();
		
		// TODO finish the code below this ------
		
//		// If firstDistance is null and firstAngle isFinished && not null
//		// and the secondDistance Command is not running, run the secondDistance Command
//		if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
//			System.out.println("Part 2 Done.");
//			firstAngle.cancel();
//			firstAngle = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
////			Robot.encRight.reset();
//			moveElevatorScale.start();
//			secondDistance.start();
//		}
//		
//		// If firstDistance is NOT null and firstDistance isFinished
//		// and the firstAngle Command is not running, run the firstAngle Command
//		else if ((null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) || currentTime - startTime > timeOut1) {
//			System.out.println("Part 1 Done.");
//			firstDistance.cancel();
//			firstDistance = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
////			Robot.encRight.reset();
//			firstAngle.start();
//		}
//		
//		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
//			System.out.println("Fidget Done.");
//			fidget.cancel();
//			fidget = null;
//			Robot.encLeft.reset();
////			Robot.encRight.reset();
//			firstDistance.start();
//		}
	}
//
//	@Override
//	public boolean isFinished() {
//		// returns if the last distance has finished and the robot has shot the box
//		if (firstAngle == null && secondDistance.isFinished())
//			return true;
//		return false; 
//		
////		if (firstDistance != null)
////			return firstDistance.isFinished();
////		return false;
//	}
//	
//	// @Override
//	// protected void end() {
//	// firstAngle.free();
//	// firstDisance
//	// }
//	
	@Override
	protected void end() {
		super.end();
	}
}
