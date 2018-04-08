package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SameLine
public class Full_Right_ScaleIsLeft extends AutoPath {
	private PIDStraightMovement firstDistance, secondDistance;
	private PIDTurn firstAngle,secondAngle, thirdAngle;
	private DriveForward thirdDistance;
	private Fidget fidget;
	private MoveElevatorAuto moveElevator;
	private double startTime, currentTime;
	
	//Times
	public static final double timeOut1 = 20;
	public static final double timeOut2 = 20;
	public static final double timeOut3 = 20;
	
	// Values for 239 inches.
	public static final double p1 = 0.007;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	
	// Values for 250 inches
	public static final double p2 = 0.006;//.0111
	public static final double i2 = 0.0;
	public static final double d2 = 0.018;
	
	// Values for 75 inches.
	public static final double p3 = 0.025;
	public static final double i3 = 0.0;
	public static final double d3 = 0.01;

	public Full_Right_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, .8, 230, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -88);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, .8, 232, p1, i1, d1);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 115);
		thirdDistance = new DriveForward(.7);
		moveElevator=new MoveElevatorAuto(2);
		//thirdAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -42);
		//fourthDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 30, p1, i1, d1);
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
	
		
		// If firstDistance, first angle, and secondDistance are all null and
		// SecondAngle isFinished
		// and the thirdDistance Command is not running, run the thirdDistance Command
		
		// If firstDistance is null and firstAngle isFinished && not null
		// and the secondDistance Command is not running, run the secondDistance Command
		if (null == fidget && null == firstDistance && null == firstAngle &&null==secondDistance&& null!=secondAngle&& secondAngle.isFinished()&&moveElevator.maxHeightReachedTime()) {
			System.out.println("Part 2 Done.");
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.start();
//			Robot.encRight.reset();
		}
		else if (null == fidget && null == firstDistance && null == firstAngle &&null!=secondDistance&& secondDistance.isFinished() ) {
			System.out.println("Part 2 Done.");
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.start();
			moveElevator.start();
//			Robot.encRight.reset();
		}
		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() &&!(secondDistance.isRunning())) {
			System.out.println("Part 2 Done.");
			SmartDashboard.putBoolean("yes", true);
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
//			Robot.encRight.reset();
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
		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstDistance.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (thirdDistance.isFinished())
			return true;
		return false;
	}

	@Override
	protected void end() {
//		firstAngle.cancel();
		if(isFinished())
		{
			new AutoOutGo().start();
			new DriveBackward(2).start();
		}
//		super.end();
	}

}
