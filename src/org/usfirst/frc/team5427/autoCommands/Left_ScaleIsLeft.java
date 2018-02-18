package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Left_ScaleIsLeft extends AutoPath {
	private PIDStraightMovement firstDistance;
	private PIDTurn firstAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;

	//Values for 18 inches.
	public static final double p1 = 0.009;
	public static final double i1 = 0.0;
	public static final double d1 = 0.02;
	
	public Left_ScaleIsLeft() {
		// creates all of the PID Commands
		fidget = new Fidget();
//		fidget = null;
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER, 318, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
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
		
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
		if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
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
		if (firstAngle != null && firstAngle.isFinished() && !Robot.intakeSubsystem.setSpeedTime(.3, 2))
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