package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This is our autonomous path that starts in the center position
 * and places one cube on the right side of the switch.
 * 
 * @author Blake
 */
@SameLine
public class Center_SwitchIsRight extends AutoPath {
	
	/**
	 * The first distance of the path. It travels forward 110 inches at our short power.
	 */
	private PIDStraightMovement firstDistance;
	
	/**
	 * The command that moves the elevator up to its middle position.
	 */
	private MoveElevatorAuto moveElevator;
	
	/**
	 * The command used at the start of autonomous to drop the arms of the intake down.
	 */
	private Fidget fidget;
	
	/**
	 * The starting time of the autonomous path.
	 */
	private double startTime;
	
	/**
	 * The current time during the autonomous path.
	 */
	private double currentTime;
	
	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 13;
	
	/**********PID VALUES FOR 110 INCHES**********/
	/**
	 * P value for 110 inches.
	 */
	public static final double p1 = 0.025;
	
	/**
	 * I value for 110 inches.
	 */
	public static final double i1 = 0.0;
	
	/**
	 * D value for 110 inches.
	 */
	public static final double d1 = 0.12;
	/*********************************************/
	
	/**
	 * Stores whether or not the elevator has finished moving to its position.
	 */
	public boolean elevIsDone;

	/**
	 * Creates all of the paths involved in Center_SwitchIsRight and sets the timeout of the path.
	 */
	public Center_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 110, p1, i1, d1); //106
		moveElevator = new MoveElevatorAuto(1);
		elevIsDone = false;
		setTimeout(timeOut);
	}

	/**
	 * Run once when the command is started. Captures the starting time of the path, starts the first portion of it,
	 * and states that the elevator has not finished moving to its position.
	 */
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();
		elevIsDone= false;
	}

	/**
	 * Runs periodically while the command is not finished.
	 * Used also to switch between commands at different points in our path.
	 */
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		
		if(currentTime-startTime>0.8&&!moveElevator.isRunning()&&!moveElevator.isFinished()) {
			moveElevator.start();
		}

		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			fidget.cancel();
			fidget = null;
			Robot.encLeft.reset();
			firstDistance.start();
		}
	}

	/**
	 * Runs periodically to check to see if the path can be finished.
	 * 
	 * @return	true when the path is finished or the path has timed out.
	 */
	@Override
	public boolean isFinished() {
		if (firstDistance.isFinished())
			return true;
		return isTimedOut();
	}

	/**
	 * Run once when isFinished() returns true.
	 * Utilizes the end() of AutoPath to shoot out the box.
	 */
	@Override
	protected void end() {
		super.end();
	}
}
