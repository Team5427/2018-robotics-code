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
public class Left_ScaleIsRight extends AutoPath {
	private PIDStraightMovement firstDistance, secondDistance, thirdDistance, fourthDistance;
	private PIDTurn fidgetSpinner, firstAngle, secondAngle, thirdAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	private double startTime, currentTime;
	
	//Times
	public static final double timeOut1 = 0;
	public static final double timeOut2 = 0;
	public static final double timeOut3 = 0;
	
	// Values for 239 inches.
	public static final double p1 = 0.0111;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	
	// Values for 250 inches
	public static final double p2 = 0.0111;
	public static final double i2 = 0.0;
	public static final double d2 = 0.018;
	
	// Values for 75 inches.
	public static final double p3 = 0.025;
	public static final double i3 = 0.0;
	public static final double d3 = 0.01;

	public Left_ScaleIsRight() {
		fidget = new Fidget();
		fidgetSpinner = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -10);
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 1, 239, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 1, 250, p2, i2, d2); // used to be 244
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 1, 15, p3, i3, d3);
		thirdAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -42);
		fourthDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 30, p1, i1, d1);
		moveElevator = new MoveElevatorAuto(2);
	}

	// begins the command
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		currentTime = System.nanoTime()/1000000000;
		// If firstDistance, first angle are all null and secondDistance isFinished &&
		// not null
		// and the secondAngle Command is not running, run the secondAngle Command
		if(moveElevator != null)
			moveElevator.isFinished();
		if (null == fidget && null == firstDistance && firstAngle ==null&& moveElevator.maxHeightReached() && (!fourthDistance.isRunning())) {
			System.out.println("Part 17 Done.");
//			firstAngle.cancel();
//			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			fourthDistance.start();
//			Robot.encRight.reset();
//			secondDistance.start();
//			moveElevator.start();
		}
		if ((null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == secondAngle && null != thirdDistance && thirdDistance.isFinished() && !(thirdAngle.isRunning())) || currentTime - startTime > timeOut3) {
			System.out.println("Part 5 Done.");
			thirdDistance.cancel();
			thirdDistance = null;
			Robot.ahrs.reset();
			thirdAngle.start();
		}
		
		// If firstDistance, first angle, and secondDistance are all null and
		// SecondAngle isFinished
		// and the thirdDistance Command is not running, run the thirdDistance Command
		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
			System.out.println("Part 4 Done.");
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			thirdDistance.start();
		}
		
		else if ((null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning()) || currentTime - startTime > timeOut2) {
			System.out.println("Part 3 Done.");
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			secondAngle.start();
		}
		
		// If firstDistance is null and firstAngle isFinished && not null
		// and the secondDistance Command is not running, run the secondDistance Command
		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
			System.out.println("Part 2 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			secondDistance.start();
		}
		
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
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
		if (thirdDistance == null && thirdAngle.isFinished())
			return true;
		return false;
	}

	@Override
	protected void end() {
		moveElevator.cancel();
		new AutoOutGo().start();
		new DriveBackward(2).start();
		super.end();
	}
}
