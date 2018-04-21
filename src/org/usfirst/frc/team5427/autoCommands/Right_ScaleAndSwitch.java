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
	private PIDStraightMovement firstDistance;

	/**
	 * The third distance of the path. It travels 70 inches forward at our long
	 * power.
	 */
	private PIDStraightMovement thirdDistance;

	/**
	 * The second distance of the path. It travels forward for .7 seconds.
	 */
	private DriveForward secondDistance;

	/**
	 * The fourth distance of the path. It travels forward for .7 seconds.
	 */
	private DriveForward fourthDistance;

	/**
	 * Command that moves the robot backward. It travels backward for .5 seconds.
	 */
	private DriveBackward moveBack;

	/**
	 * The first turn of the path. It turns 51 degrees counterclockwise.
	 */
	private PIDTurn firstAngle;

	/**
	 * The second turn of the path. It turns 90 degrees counterclockwise.
	 */
	private PIDTurn secondAngle;

	/**
	 * The command used to move the elevator up to the top of its path.
	 */
	private MoveElevatorAuto moveElevatorScale;

	/**
	 * The command used to reset the elevator back to its default position.
	 */
	private MoveElevatorAuto elevatorReset;

	/**
	 * The command used to move the elevator up to the middle of its path.
	 */
	private MoveElevatorAuto moveElevatorSwitch;

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

	/********** PID VALUES FOR 250 INCHES **********/
	/**
	 * P value for 250 inches.
	 */
	public static final double p1 = 0.011;

	/**
	 * I value for 250 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 250 inches.
	 */
	public static final double d1 = 0.018;
	/*********************************************/

	/********** PID VALUES FOR 70 INCHES **********/
	/**
	 * P value for 70 inches.
	 */
	public static final double p2 = 0.011;

	/**
	 * I value for 70 inches.
	 */
	public static final double i2 = 0.0;

	/**
	 * D value for 70 inches.
	 */
	public static final double d2 = 0.018;

	/*********************************************/

	/**
	 * Creates all of the paths involved in Right_ScaleAndSwitch.
	 */
	public Right_ScaleAndSwitch() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 250, p1, i1, d1);
		secondDistance = new DriveForward(.7);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 70, 0, 0, 0); // TODO: find real value for thirdDistance
		fourthDistance = new DriveForward(.7);
		moveBack = new DriveBackward(.5);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -51);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		moveElevatorSwitch = new MoveElevatorAuto(1);
		moveElevatorScale = new MoveElevatorAuto(2);
		elevatorReset = new MoveElevatorAuto(3);
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

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null == moveElevatorSwitch) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			fourthDistance.cancel();
			fourthDistance = null;
			shootSwitch.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null != moveElevatorSwitch && moveElevatorSwitch.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevatorSwitch.cancel();
			moveElevatorSwitch = null;
			fourthDistance.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null != thirdDistance && thirdDistance.isFinished() && null != intake) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.cancel();
			thirdDistance = null;
			intake.cancel();
			intake = null;
			moveElevatorSwitch.start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null != elevatorReset && elevatorReset.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			elevatorReset.cancel();
			elevatorReset = null;
			thirdDistance.start();
			intake.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == moveBack && null != secondAngle && secondAngle.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.cancel();
			secondAngle = null;
			elevatorReset.start();
			new TiltIntake_TimeOut().start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null != moveBack && moveBack.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveBack.cancel();
			moveBack = null;
			secondAngle.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != shootScale && shootScale.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			shootScale.cancel();
			shootScale = null;
			moveElevatorScale.cancel();
			moveElevatorScale = null;
			moveBack.start();
		}

		else if (null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && moveElevatorScale.maxHeightReachedTime()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.cancel();
			secondDistance = null;
			shootScale.start();
		}

		else if (null == fidget && null == firstDistance && firstAngle == null && moveElevatorScale.maxHeightReachedTime() && (!secondDistance.isRunning())) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
		}

		else if (null == fidget && null == firstDistance && firstAngle != null && firstAngle.isFinished()) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
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
