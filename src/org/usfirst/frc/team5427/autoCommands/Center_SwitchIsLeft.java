package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

/**
 * This is our autonomous path that starts in the center position and places one
 * cube on the left side of the switch.
 * 
 * @author Blake Romero
 */
@SameLine
public class Center_SwitchIsLeft extends AutoPath {

	/**
	 * The first distance of the path. It travels forward 18 inches at our short
	 * power.
	 */
	private PIDStraightMovement firstDistance;

	/**
	 * The second distance of the path. It travels forward 110 inches at our long
	 * power.
	 */
	private PIDStraightMovement secondDistance;

	/**
	 * The third distance of the path. It travels forward 78 inches at .2 power
	 * higher than our long power.
	 */
	private PIDStraightMovement thirdDistance;

	/**
	 * The first turn of the path. It turns 85 degrees counterclockwise.
	 */
	private PIDTurn firstAngle;

	/**
	 * The second turn of the path. It turns 85 degrees clockwise.
	 */
	private PIDTurn secondAngle;

	/**
	 * The command that moves the elevator up to its middle position.
	 */
	private MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;

	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 13;

	/********** PID VALUES FOR 18 INCHES **********/
	/**
	 * P value for 18 inches.
	 */
	public static final double p1 = 0.02;

	/**
	 * I value for 18 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 18 inches.
	 */
	public static final double d1 = 0.0;
	/*********************************************/

	/********** PID VALUES FOR 110 INCHES **********/
	/**
	 * P value for 110 inches.
	 */
	public static final double p2 = 0.042;

	/**
	 * I value for 110 inches.
	 */
	public static final double i2 = 0.0;

	/**
	 * D value for 110 inches.
	 */
	public static final double d2 = 0.08;
	/*********************************************/

	/********** PID VALUES FOR 78 INCHES **********/
	/**
	 * P value for 78 inches.
	 */
	public static final double p3 = 0.025;

	/**
	 * I value for 78 inches.
	 */
	public static final double i3 = 0.0;

	/**
	 * D value for 78 inches.
	 */
	public static final double d3 = 0.06;

	/*********************************************/

	/**
	 * Creates all of the paths involved in Center_SwitchIsLeft.
	 */
	public Center_SwitchIsLeft() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 18, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -85);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 110, p2, i2, d2);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 85);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG + .2, 78, p3, i3, d3);
		moveElevator = new MoveElevatorAuto(1);
	}

	/**
	 * Run once when the command is started. Starts the first portion of the path
	 * and sets the timeout of it.
	 */
	public void initialize() {
		fidget.start();
		setTimeout(timeOut);
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		if (moveElevator != null)
			moveElevator.isFinished();

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevator.start();
			thirdDistance.start();
		}

		else if ((null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && ((secondDistance.isFinished() && !secondAngle.isRunning())))) {
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.start();
		}

		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
		}

		else if ((null == fidget && null != firstDistance && (firstDistance.isFinished()) && !(firstAngle.isRunning()))) {
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			firstAngle.start();
		}

		else if ((null != fidget && fidget.isFinished() && !(firstDistance.isRunning()))) {
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			firstDistance.start();
		}
	}

	/**
	 * Runs periodically to check to see if the path can be finished.
	 * 
	 * @return true when the path is finished or the path has timed out.
	 */
	@Override
	public boolean isFinished() {
		if (secondAngle == null && thirdDistance.isFinished())
			return true;
		return isTimedOut();
	}

	/**
	 * Run once when isFinished() returns true. Utilizes the end() of AutoPath to
	 * shoot out the box.
	 */
	@Override
	protected void end() {
		super.end();
	}
}