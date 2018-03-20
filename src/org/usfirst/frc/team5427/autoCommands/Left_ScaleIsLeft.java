package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

@SameLine
public class Left_ScaleIsLeft extends AutoPath {
	private PIDStraightMovement firstDistance, secondDistance;
	private PIDTurn fidgetSpinner,firstAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	private double startTime, currentTime;
	
	//Time
	public static final double timeOut1 = 0;
	
	// Values for 18 inches.
	public static final double p1 = 0.011;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	public static final double p2 = 0.2;
	public static final double i2 = 0.0;
	public static final double d2 = 0.1;

	public Left_ScaleIsLeft() {
		fidget = new Fidget();
		fidgetSpinner = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -10);
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG+.1, 250, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 42);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 30, p1, i1, d1);
		moveElevator = new MoveElevatorAuto(2);
		setTimeout(13.75);
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

		if(moveElevator != null)
			moveElevator.isFinished();
		if (null == fidget && null == firstDistance && firstAngle ==null&& moveElevator.maxHeightReached() && (!secondDistance.isRunning())) {
			System.out.println("Part 17 Done.");
//			firstAngle.cancel();
//			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
//			Robot.encRight.reset();
//			secondDistance.start();
//			moveElevator.start();
		}
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
//			new MoveElevatorAuto(1).start();
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
		if (firstAngle == null && secondDistance.isFinished())
			return true;
		return isTimedOut();
	}

	@Override
	protected void end() {
		moveElevator.cancel();
		new AutoOutGo().start();
		new DriveBackward(2).start();
		super.end();
	}
}
