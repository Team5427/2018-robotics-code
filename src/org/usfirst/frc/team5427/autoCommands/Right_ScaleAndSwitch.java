package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateIn;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.util.Config;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is our autonomous path that starts in the right position and places one
 * cube on the right side of the scale and switch.
 * 
 * @author Andrew Li, Kipp Corman
 */
public class Right_ScaleAndSwitch extends AutoPath {
	
	/**
	 * The first distance of the path. It travels 250 inches forward at our long
	 * power.
	 */
	private Right_ScaleAndSwitch_FirstDistance firstDistance;
	
	/**
	 * The first turn of the path. It turns 51 degrees counterclockwise.
	 */
	private Right_ScaleAndSwitch_FirstAngle firstAngle;	

	/**
	 * The second distance of the path. It travels forward for .7 seconds.
	 */
	private Right_ScaleAndSwitch_DriveForward secondDistance;
	
	private Right_ScaleAndSwitch_CurveToScale scaleCurve;
	/**
	 * The fifth distance of the path. It moves backwards for .5 seconds.
	 */
	private Right_ScaleAndSwitch_DriveBackward moveBack;
	
	/**
	 * The first turn of the path. It turns 90 degrees counterclockwise.
	 */
	private Right_ScaleAndSwitch_SecondAngle secondAngle;
	
	/**
	 * The first distance of the path. It travels 70 inches forward at our long
	 * power.
	 */
	private Right_ScaleAndSwitch_ThirdDistance thirdDistance;
	
	/**
	 * The fourth distance of the path. It travels forward for .7 seconds.
	 */
	private Right_ScaleAndSwitch_DriveForward2 fourthDistance;
	
	/**
	 * The command used to move the elevator up to the top of its path.
	 */
	private Right_ScaleAndSwitch_MoveElevatorAuto moveElevatorScale;

	/**
	 * The command used to reset the elevator back to its default position.
	 */
	private Right_ScaleAndSwitch_MoveElevatorAuto elevatorReset;

	/**
	 * The command used to move the elevator up to the middle of its path.
	 */
	private Right_ScaleAndSwitch_MoveElevatorAuto moveElevatorSwitch;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;
	
	/**
	 * The command used to shoot the cube onto the scale.
	 */
	private AutoOutGo shootScale;

	/**
	 * The command used to shoot the cube onto the switch.
	 */
	private AutoOutGo shootSwitch;

	/**
	 * The command used to intake a cube into the robot.
	 */
	private IntakeActivateIn intake;

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
	 * Creates all of the paths involved in Right_ScaleAndSwitch.
	 */
	public Right_ScaleAndSwitch() {
		fidget = new Fidget();
		firstDistance = new Right_ScaleAndSwitch_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		scaleCurve = new Right_ScaleAndSwitch_CurveToScale();
		moveBack = new Right_ScaleAndSwitch_DriveBackward();
		secondAngle = new Right_ScaleAndSwitch_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		thirdDistance = new Right_ScaleAndSwitch_ThirdDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		fourthDistance = new Right_ScaleAndSwitch_DriveForward2();
		
		moveElevatorSwitch = new Right_ScaleAndSwitch_MoveElevatorAuto(1);
		moveElevatorScale = new Right_ScaleAndSwitch_MoveElevatorAuto(2);
		elevatorReset = new Right_ScaleAndSwitch_MoveElevatorAuto(3);
		intake = new IntakeActivateIn();
		shootScale = new AutoOutGo();
		shootSwitch = new AutoOutGo();
	}

	/**
	 * Run once when the command is started. Captures the start time of the path,
	 * starts the first portion of the path and sets the timeout of the path.
	 */
	public void initialize() {
		startTime = System.nanoTime() / 1000000000.;
		fidget.start();
		setTimeout(timeOut);
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		currentTime = System.nanoTime() / 1000000000.;

		if (null == fidget && null == firstDistance && null == scaleCurve && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null == moveElevatorSwitch) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			fourthDistance.cancel();
			fourthDistance = null;
			shootSwitch.start();
		}

		else if (null == fidget && null == firstDistance && null == scaleCurve && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null != moveElevatorSwitch && moveElevatorSwitch.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevatorSwitch.cancel();
			moveElevatorSwitch = null;
			fourthDistance.start();
		}

		else if (null == fidget && null == firstDistance && null == scaleCurve && null == shootScale && null == secondAngle && null != thirdDistance && thirdDistance.isFinished() && null != intake) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.cancel();
			thirdDistance = null;
			intake.cancel();
			intake = null;
			moveElevatorSwitch.start();
		}

		if (null == fidget && null == firstDistance && null == scaleCurve && null == secondAngle && null != elevatorReset && elevatorReset.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			elevatorReset.cancel();
			elevatorReset = null;
			thirdDistance.start();
			intake.start();
		}

		else if (null == fidget && null == firstDistance && null == scaleCurve && null == shootScale && null == moveBack && null != secondAngle && secondAngle.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.cancel();
			secondAngle = null;
			elevatorReset.start();
			new TiltIntake_TimeOut().start();
		}

		else if (null == fidget && null == firstDistance && null == scaleCurve && null == shootScale && null != moveBack && moveBack.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveBack.cancel();
			moveBack = null;
			secondAngle.start();
		}

		else if (null == fidget && null == firstDistance && null == scaleCurve && null != shootScale && shootScale.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			shootScale.cancel();
			shootScale = null;
			moveElevatorScale.cancel();
			moveElevatorScale = null;
			moveBack.start();
		}

		else if (null == fidget && null == firstDistance && null != scaleCurve && scaleCurve.isFinished() && moveElevatorScale.maxHeightReachedTime()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			scaleCurve.cancel();
			scaleCurve = null;
			shootScale.start();
		}

		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(scaleCurve.isRunning())) {
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			scaleCurve.start();
		}

		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			firstDistance.start();
		}

		if (null != moveElevatorScale && moveElevatorScale.maxHeightReachedTime() && Robot.tiltUpNext) {
			new TiltIntake_TimeOut().start();
		}

		if (currentTime - startTime > 2.5 && null != moveElevatorScale && !moveElevatorScale.isRunning())
			moveElevatorScale.start();
	}

	/**
	 * Runs periodically to check to see if the path can be finished.
	 * 
	 * @return true when the path has timed out.
	 */
	@Override
	public boolean isFinished() {
		return isTimedOut();
	}

	/**
	 * Run once the isFinished() returns true. Utilizes the default ending of
	 * AutoPath to shoot out any cubes in the robot.
	 */
	@Override
	protected void end() {
		super.end();
	}
}
