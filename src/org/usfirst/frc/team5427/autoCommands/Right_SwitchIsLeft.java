package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Right_SwitchIsLeft extends AutoPath {
	private PIDStraightMovement firstDistance, secondDistance;
	private PIDTurn firstAngle, secondAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;

	//Values for 243 inches.
	public static final double p1 = 0.0115;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	//Values for 172 inches.
	public static final double p2 = 0.022;
	public static final double i2 = 0.0;
	public static final double d2 = 0.012;
	
	public Right_SwitchIsLeft() {
		// creates all of the PID Commands
		fidget = new Fidget();
//		fidget = null;
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 243, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 172, p2, i2, d2);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		moveElevator = new MoveElevatorAuto(1); // 1 for switch
	}

	// begins the command
	public void initialize() {
		fidget.start();		
//		firstDistance.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		
		// If firstDistance, first angle are all null and secondDistance isFinished &&
		// not null
		// and the secondAngle Command is not running, run the secondAngle Command
		if (null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning()) {
			System.out.println("Part 3 Done.");
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			secondAngle.start();
		}
		
		// If firstDistance is null and firstAngle isFinished && not null
		// and the secondDistance Command is not running, run the secondDistance Command
		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
			System.out.println("Part 2 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			secondDistance.start();
		}
		
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			firstAngle.start();
		}
		
		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			firstDistance.start();
			moveElevator.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (secondDistance == null && secondAngle.isFinished())
			return true;
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
		super.end();
	}
}
