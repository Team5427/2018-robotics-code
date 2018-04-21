package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This is our autonomous path that starts in the right position and places one
 * cube on the right side of the switch.
 * 
 * @author Blake Romero
 */

public class Right_SwitchIsRight extends AutoPath {

	/**
	 * The first distance of the path. It travels 154 inches forward at our short
	 * power.
	 */
	private PIDStraightMovement firstDistance;

	/**
	 * The second distance of the path. It travels 16 inches forward at our short
	 * power.
	 */
	private PIDStraightMovement secondDistance;

	/**
	 * The first turn of the path. It turns 90 degrees clockwise.
	 */
	private PIDTurn firstAngle;

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
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 15;

	/********** PID VALUES FOR 154 INCHES **********/
	/**
	 * P value for 154 inches.
	 */
	public static final double p1 = 0.0105;

	/**
	 * I value for 154 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 154 inches.
	 */
	public static final double d1 = 0.008;
	/*********************************************/

	/********** PID VALUES FOR 16 INCHES **********/
	/**
	 * P value for 16 inches.
	 */
	public static final double p2 = 0.013;

	/**
	 * I value for 16 inches.
	 */
	public static final double i2 = 0.0;

	/**
	 * D value for 16 inches.
	 */
	public static final double d2 = 0.006;

	/*********************************************/

	/**
	 * Creates all of the path involved in Right_SwitchIsRight
	 */
	public Right_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 154, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 28, p2, i2, d2);
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

		if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !(secondDistance.isRunning())) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevator.start();
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
		if (firstAngle == null && secondDistance.isFinished())
			return true;
		return false;

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
