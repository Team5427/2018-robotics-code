package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is our autonomous path that starts in the left position, moves towards
 * the scale, and turns 90 degrees.
 * 
 * @author Blake Romero, Andrew Li
 */
@SameLine
public class Left_ScaleIsRight extends AutoPath {

	/**
	 * The first distance of the path. It travels 224 inches forward at .7 power.
	 */
	private Left_ScaleIsRight_FirstDistance firstDistance;

	/**
	 * The first turn of the path. It turns 85 degrees clockwise.
	 */
	private Left_ScaleIsRight_FirstAngle firstAngle;
	
	private Left_ScaleIsRight_SecondDistance secondDistance;
	
	private Left_ScaleIsRight_SecondAngle secondAngle;
	
	private Left_ScaleIsRight_ThirdDistanceEncoder thirdDistance;
	
	private Left_ScaleIsRight_MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;

	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 15;

	/********** PID VALUES FOR 224 INCHES **********/
	/**
	 * P value for 224 inches.
	 */
	public static final double p1 = 0.0111;

	/**
	 * I value for 224 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 224 inches.
	 */
	public static final double d1 = 0.018;

	/*********************************************/

	/**
	 * Creates all of the paths involved in Left_ScaleIsRight.
	 */
	public Left_ScaleIsRight() {
		fidget = new Fidget();
		firstDistance = new Left_ScaleIsRight_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		firstAngle = new Left_ScaleIsRight_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondDistance = new Left_ScaleIsRight_SecondDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondAngle = new Left_ScaleIsRight_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		thirdDistance = new Left_ScaleIsRight_ThirdDistanceEncoder(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveElevator = new Left_ScaleIsRight_MoveElevatorAuto();
	}

	/**
	 * Run once when the command is started. Starts the first portion of the path
	 * and sets the timeout of the path.
	 */
	public void initialize() {
		fidget = null;
		
		firstDistance.start();
		setTimeout(timeOut);
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		SmartDashboard.putNumber("Motor Value", Robot.driveTrain.drive_Right.get());
		
		if (moveElevator != null)
			moveElevator.isFinished();

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
			secondAngle.cancel();
			secondAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.start();
			
		}

		else if ((null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning())) {
			secondDistance.cancel();
			secondDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevator.start();
			secondAngle.start();
			new TiltIntake_TimeOut().start();
		}

		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
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
	 * @return true when the path has finished or the path has timed out.
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
		moveElevator.cancel();
		new AutoOutGo().start();
		new DriveBackward(2).start();
		super.end();
	}

}
