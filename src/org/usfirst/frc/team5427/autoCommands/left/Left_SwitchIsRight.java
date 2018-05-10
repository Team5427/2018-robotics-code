package org.usfirst.frc.team5427.autoCommands.left;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This is our autonomous path that starts in the left position and places one
 * cube on the right side of the switch.
 * 
 * @author Blake Romero, Andrew Li
 */
@SameLine
public class Left_SwitchIsRight extends AutoPath {

	/**
	 * The first distance of the path. It travels 25 inches forward at our short
	 * power.
	 */
	private Left_SwitchIsRight_FirstDistance firstDistance;

	/**
	 * The second distance of the path. It travels 171 inches forward at our long
	 * power.
	 */
	private Left_SwitchIsRight_SecondDistance secondDistance;

	/**
	 * The third distance of the path. It travels 75 inches forward at our short
	 * power.
	 */
	private Left_SwitchIsRight_ThirdDistance thirdDistance;

	/**
	 * The first turn of the path. It turns 80 degrees clockwise.
	 */
	private Left_SwitchIsRight_FirstAngle firstAngle;

	/**
	 * The second turn of the path. It turns 80 degrees counterclockwise.
	 */
	private Left_SwitchIsRight_SecondAngle secondAngle;

	/**
	 * The command that moves the elevator up to its switch position.
	 */
	private Left_SwitchIsRight_MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;

	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 15;

	/********** PID VALUES FOR 25 INCHES **********/
	/**
	 * P value for 25 inches.
	 */
	public static final double p1 = 0.02;

	/**
	 * I value for 25 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 25 inches.
	 */
	public static final double d1 = 0.0;
	/*********************************************/

	/********** PID VALUES FOR 171 INCHES **********/
	/**
	 * P value for 171 inches.
	 */
	public static final double p2 = 0.031;

	/**
	 * I value for 171 inches.
	 */
	public static final double i2 = 0.0;

	/**
	 * D value for 171 inches.
	 */
	public static final double d2 = 0.08;
	/*********************************************/

	/********** PID VALUES FOR 75 INCHES **********/
	/**
	 * P value for 75 inches.
	 */
	public static final double p3 = 0.025;

	/**
	 * I value for 75 inches.
	 */
	public static final double i3 = 0.0;

	/**
	 * D value for 75 inches.
	 */
	public static final double d3 = 0.06;

	/*********************************************/

	/**
	 * Creates all of the paths involved in Left_SwitchIsRight.
	 */
	public Left_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new Left_SwitchIsRight_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		firstAngle = new Left_SwitchIsRight_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondDistance = new Left_SwitchIsRight_SecondDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondAngle = new Left_SwitchIsRight_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		thirdDistance = new Left_SwitchIsRight_ThirdDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveElevator = new Left_SwitchIsRight_MoveElevatorAuto();
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

		else if ((null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning()))) {
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
		if (secondAngle == null && (thirdDistance.isFinished()))
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
