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
 * This is our autonomous path that starts in the right position and moves and
 * turns 90 degrees towards the left side of the scale.
 * 
 * @author Blake Romero
 */
@SameLine
public class Right_ScaleIsLeft extends AutoPath {

	/**
	 * The first distance of the path. It travels 224 inches forward at .7 power.
	 */
	private Right_ScaleIsLeft_FirstDistance firstDistance;

	/**
	 * The first turn of the path. It turns 88 degrees counterclockwise.
	 */
	private Right_ScaleIsLeft_FirstAngle firstAngle;
	
	private Right_ScaleIsLeft_SecondDistance secondDistance;
	
	private Right_ScaleIsLeft_SecondAngle secondAngle;
	
	private Right_ScaleIsLeft_ThirdDistanceEncoder thirdDistance;
	
	private Right_ScaleIsLeft_MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;

	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 15;


	/**
	 * Creates all of the paths involved in Right_ScaleIsLeft.
	 */
	public Right_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new Right_ScaleIsLeft_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		firstAngle = new Right_ScaleIsLeft_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondDistance = new Right_ScaleIsLeft_SecondDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		secondAngle = new Right_ScaleIsLeft_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		thirdDistance = new Right_ScaleIsLeft_ThirdDistanceEncoder(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveElevator = new Right_ScaleIsLeft_MoveElevatorAuto();
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
		if (firstAngle == null)
			return true;
		return isTimedOut();
	}

	/**
	 * Run once the isFinished() returns true.
	 */
	@Override
	protected void end() {}

}
