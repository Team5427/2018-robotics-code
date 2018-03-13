package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Right_ScaleIsRight extends AutoPath {
	private PIDStraightMovement firstDistance,secondDistance;
	private PIDTurn fidgetSpinner,firstAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	
	//the start and current time of the auto path in seconds
	private double startTime, currentTime;
	
	//Times TODO: test for times
	public static final double timeOut1 = 13;

	//Values for 18 inches.
	public static final double p1 = 0.011;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	public static final double p2 = 0.02;
	public static final double i2 = 0.0;
	public static final double d2 = 0.0;
	
	
	public Right_ScaleIsRight() {
		// creates all of the PID Commands
		requires(Robot.driveTrain);
		fidget = new Fidget();
//		fidget = null;
		fidgetSpinner = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -18.5);
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 287, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -85);
		moveElevator = new MoveElevatorAuto(2); // 1 for switch
//		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 16, p2, i2, d2);
	}

	// begins the command
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();		
//		firstDistance.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		if (null == fidget && null == firstDistance && firstAngle !=null&& firstAngle.isFinished() && !(moveElevator.isRunning())) {
			System.out.println("Part 1 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
//			secondDistance.start();
			moveElevator.start();
		}
		
		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstAngle.start();
		}
		else if(null == fidget && null != fidgetSpinner && fidgetSpinner.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Prelim Angle Done.");
			fidgetSpinner.cancel();
			fidgetSpinner = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstDistance.start();
		}
		else if(null != fidget && fidget.isFinished() && !(fidgetSpinner.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			fidgetSpinner.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (firstAngle == null && moveElevator.isFinished()) {
			return true;
		}
		return false;
		
//		if (firstDistance != null)
//			return firstDistance.isFinished();
//		return false;
	}
	
	// @Override
	// protected void end() {
	// firstAngle.free();
	// firstDisance
	// }
	
	@Override
	protected void end() {
		new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 10, p2, i2, d2).start();
		super.end();
	}
}
