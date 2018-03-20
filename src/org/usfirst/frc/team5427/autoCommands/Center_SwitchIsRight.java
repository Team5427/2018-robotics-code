package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

@SameLine
public class Center_SwitchIsRight extends AutoPath {
	private PIDStraightMovement firstDistance,secondDistance;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	
	//the start and current time of the auto path in seconds
	private double startTime, currentTime;
	
	//Times TODO: test for times
//	public static final double timeOut1 = 15;
	
	// Values for 72 inches.
	public static final double p1 = 0.025; //.06
	public static final double i1 = 0.0;
	public static final double d1 = 0.12;//0.06; //.05
	
	//Values for 16 inches.
	public static final double p2 = 0.016;
	public static final double i2 = 0.0;
	public static final double d2 = 0.006;


	public Center_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 86, p1, i1, d1);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 16, p2, i2, d2);
		moveElevator = new MoveElevatorAuto(1);
		setTimeout(13.5);
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
		
		if (null != firstDistance && firstDistance.isFinished() && !(secondDistance.isRunning())) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			secondDistance.start();
			moveElevator.start();
		}
		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstDistance.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (firstDistance == null && secondDistance.isFinished())
			return true;
		return isTimedOut();
	}

	@Override
	protected void end() {
		super.end();
	}
}
