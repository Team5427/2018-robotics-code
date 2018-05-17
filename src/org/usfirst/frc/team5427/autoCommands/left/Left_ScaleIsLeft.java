package org.usfirst.frc.team5427.autoCommands.left;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is our autonomous path that starts in the Left position and places one
 * cube on the Left side of the scale.
 * 
 * @author Akshat Jain
 */
@SameLine
public class Left_ScaleIsLeft extends AutoPath {


	/**
	 * The first distance of the path. It travels 250 inches forward at our long
	 * power.
	 */
	private Left_ScaleIsLeft_FirstDistance firstDistance;

	/**
	 * The second distance of the path. It travels forward for .7 seconds.
	 */

//	private Left_ScaleIsLeft_DriveForward secondDistance;

	/**
	 * The first turn of the path. It turns 51 degrees counterclockwise.
	 */
	private Left_ScaleIsLeft_FirstAngle firstAngle;

	/**
	 * The command used to move the elevator up to the top of its path.
	 */
	private Left_ScaleIsLeft_MoveElevatorAuto moveElevator;
	
	private Left_ScaleIsLeft_CurveToScale curve;

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


	/*********************************************/

	/**
	 * Creates all of the paths involved in Left_ScaleIsLeft.
	 */
	public Left_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new Left_ScaleIsLeft_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		curve = new Left_ScaleIsLeft_CurveToScale();
//		firstAngle = new Left_ScaleIsLeft_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
//		secondDistance = new Left_ScaleIsLeft_DriveForward();
//		moveElevator = new Left_ScaleIsLeft_MoveElevatorAuto();
	}

	/**
	 * Run once when the command is started. Captures the start time of the path,
	 * starts the first portion of the path and sets the timeout of the path.
	 */
	public void initialize() {
		startTime = System.nanoTime() / 1000000000.;
		firstDistance.start();
		setTimeout(timeOut);
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		currentTime = System.nanoTime() / 1000000000.;
//
//		if (moveElevator != null)
//			moveElevator.isFinished();
//
//		if (moveElevator.maxHeightReachedTime() && Robot.tiltUpNext) {
//			new TiltIntake_TimeOut().start();
//		}
//
//		if (currentTime - startTime > 2.5 && !moveElevator.isRunning())
//			moveElevator.start();

//		if (null == fidget && null == firstDistance && firstAngle == null && moveElevator.maxHeightReachedTime() && (!secondDistance.isRunning())) {
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
//			secondDistance.start();
//		}
//
//		if (null == fidget && null == firstDistance && firstAngle != null && firstAngle.isFinished()) {
//			firstAngle.cancel();
//			firstAngle = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
//		}
//
//		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
//			firstDistance.cancel();
//			firstDistance = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
//			firstAngle.start();
//		}

//		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
//			fidget.cancel();
//			fidget = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
//			firstDistance.start();
//		}
		
		if(null!=firstDistance && firstDistance.isFinished() && !(curve.isRunning())) {
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			curve.start();
		}
	}

	/**
	 * Runs periodically to check to see if the path can be finished.
	 * 
	 * @return true when the path has finished or the path has timed out.
	 */
	@Override
	public boolean isFinished() {
//		if (firstAngle == null && secondDistance.isFinished())
//			return true;
		if (curve.isFinished())
			return true;
		return isTimedOut() && this.moveElevator.maxHeightReached();
	}

	/**
	 * Run once the isFinished() returns true. Resets values and shoots out any cube
	 * contained in the robot. Drives backwards afterwards.
	 */
	@Override
	protected void end() {
		Robot.tiltUpNext = false;
//		moveElevator.cancel();
//		new AutoOutGo().start();
//		secondDistance.cancel();
		curve.cancel();
//		new DriveBackward(5).start();
		super.end();
	}
}