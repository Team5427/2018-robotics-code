package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
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
 * This is our autonomous path that starts in the left position and places one
 * cube on the left side of the scale and the switch.
 * 
 * @author Andrew Li, Kipp Corman
 */
public class Left_ScaleAndSwitch extends AutoPath {

	/**
	 * The first distance of the path. It travels 250 inches forward at our long
	 * power.
	 */
	private PIDStraightMovement firstDistance;

	/**
	 * The second distance of the path. It travels forward for .7 seconds.
	 */
	private DriveForward secondDistance;

	/**
	 * The third distance of the path. It travels 70 inches forward at our long
	 * power.
	 */
	private PIDStraightMovement thirdDistance;

	/**
	 * The fourth distance of the path. It travels forward for .7 seconds.
	 */
	private DriveForward fourthDistance;

	/**
	 * The first turn of the path. It turns 47 degrees clockwise.
	 */
	private PIDTurn firstAngle;

	/**
	 * The second turn of the path. It turns 90 degrees clockwise.
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
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 15;

	/********** PID VALUES FOR 250 INCHES **********/
	/**
	 * P value for 250 inches.
	 */
	public static final double p1 = 0.0111;

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
	 * Creates all of the paths involved in Left_ScaleAndSwitch.
	 */
	public Left_ScaleAndSwitch() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 250, p1, i1, d1);
		secondDistance = new DriveForward(.7);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 70, 0, 0, 0); // TODO: find real value for thirdDistance
		fourthDistance = new DriveForward(.7);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 47);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
		moveElevatorSwitch = new MoveElevatorAuto(1);
		moveElevatorScale = new MoveElevatorAuto(2);
		elevatorReset = new MoveElevatorAuto(3);
		intake = new IntakeActivateIn();
		shootScale = new AutoOutGo();
		shootSwitch = new AutoOutGo();
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

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null == moveElevatorSwitch) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			fourthDistance.cancel();
			fourthDistance = null;
			new AutoOutGo();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null != moveElevatorSwitch && moveElevatorSwitch.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevatorSwitch.cancel();
			moveElevatorSwitch = null;
			fourthDistance.start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null != thirdDistance && thirdDistance.isFinished() && null != intake) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			thirdDistance.cancel();
			thirdDistance = null;
			intake.cancel();
			intake = null;
			moveElevatorSwitch.start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null != secondAngle && secondAngle.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondAngle.cancel();
			secondAngle = null;
			thirdDistance.start();
			intake.start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != shootScale && shootScale.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			shootScale.cancel();
			shootScale = null;
			secondAngle.start();
			elevatorReset.start();
			new TiltIntake_TimeOut().start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && moveElevatorScale.maxHeightReachedTime()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.cancel();
			secondDistance = null;
			shootScale.start();
		}

		if (null == fidget && null == firstDistance && null == firstAngle && moveElevatorScale.maxHeightReachedTime() && (!secondDistance.isRunning())) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
		}

		if (null == fidget && null == firstDistance && firstAngle != null && firstAngle.isFinished()) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
		}

		if (moveElevatorScale != null) {
			moveElevatorScale.isFinished();
		}

		if (moveElevatorScale.maxHeightReachedTime() && Robot.tiltUpNext) {
			new TiltIntake_TimeOut().start();
		}

		if (moveElevatorScale != null && !moveElevatorScale.isRunning()) {
			moveElevatorScale.start();
		}

		if (null == fidget && null != firstDistance && firstDistance.isFinished()) {
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
