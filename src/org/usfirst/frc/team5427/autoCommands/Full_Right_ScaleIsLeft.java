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

/**
 * This is our autonomous path that starts in the right position and places one
 * cube on the left side of the scale.
 * 
 * @author Blake Romero
 */
@SameLine
public class Full_Right_ScaleIsLeft extends AutoPath {

	/**
	 * The first distance of the path. It travels forward 230 inches at .8 power.
	 */
	private PIDStraightMovement firstDistance;

	/**
	 * The second distance of the path. It travels forward 232 inches at .8 power.
	 */
	private PIDStraightMovement secondDistance;

	/**
	 * The third distance of the path. It travels forward for .7 seconds.
	 */
	private DriveForward thirdDistance;

	/**
	 * The first turn of the path. It turns 88 degrees counterclockwise.
	 */
	private PIDTurn firstAngle;

	/**
	 * The second turn of the path. It turns 115 degrees clockwise.
	 */
	private PIDTurn secondAngle;

	/**
	 * The command that moves the elevator up to its top position.
	 */
	private MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
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
	public static final double timeOut = 15;

	/********** PID VALUES FOR 230 INCHES **********/
	/**
	 * P value for 230 inches.
	 */
	public static final double p1 = 0.007;

	/**
	 * I value for 230 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 230 inches.
	 */
	public static final double d1 = 0.018;
	/*********************************************/

	/********** PID VALUES FOR 232 INCHES **********/
	/**
	 * P value for 232 inches.
	 */
	public static final double p2 = 0.006;

	/**
	 * I value for 232 inches.
	 */
	public static final double i2 = 0.0;

	/**
	 * D value for 232 inches.
	 */
	public static final double d2 = 0.018;

	/*********************************************/

	/**
	 * Creates all of the paths involved in Full_Right_ScaleIsLeft.
	 */
	public Full_Right_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, .8, 230, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -88);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, .8, 232, p1, i1, d1);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 115);
		thirdDistance = new DriveForward(.7);
		moveElevator = new MoveElevatorAuto(2);
	}

	/**
	 * Run once when the command is started. Starts the first portion of the path
	 * and sets the timeout of the path.
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

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && moveElevator.maxHeightReachedTime()) {
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished()) {
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.start();
			moveElevator.start();
		}

		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !(secondDistance.isRunning())) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
		}

		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			firstAngle.start();
		}

		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
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
	 * Run once the isFinished() returns true. Cancels the moving of the elevator,
	 * and, if the path is finished, it shoots out the cube and backs up.
	 */
	@Override
	protected void end() {
		moveElevator.cancel();
		if (isFinished()) {
			new AutoOutGo().start();
			new DriveBackward(2).start();
		}
	}
}
