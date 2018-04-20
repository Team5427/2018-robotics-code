package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This class was mainly made because Team 118 wanted us to add a delay to our
 * auto to stay out of their way. It did not work.
 * 
 * @author Blake Romero
 */
@SameLine
public class BaseLine_With_Delay extends AutoPath {

	/**
	 * The first distance of this autonomous path.
	 */
	private PIDStraightMovement firstDistance;

	/**
	 * The command used to move the elevator up during the autonomous path.
	 */
	private MoveElevatorAuto moveElevator;

	/**
	 * The command used to cause the arms of the intake to fall down at the start of
	 * autonomous.
	 */
	private Fidget fidget;

	/**
	 * The time taken at the start of the autonomous path.
	 */
	private double startTime;

	/**
	 * The time taken periodically throughout the command to determine the amount of
	 * time passed.
	 */
	private double currentTime;

	/********** PID VALUES FOR 110 INCHES **********/
	/**
	 * P value for 110 inches.
	 */
	public static final double p1 = 0.025;

	/**
	 * I value for 110 inches.
	 */
	public static final double i1 = 0.0;

	/**
	 * D value for 110 inches.
	 */
	public static final double d1 = 0.12;
	/*********************************************/

	/**
	 * Stores whether the elevator is done moving upward.
	 */
	public boolean elevIsDone;

	/**
	 * Constructor used to create the fidget, firstDistance, and moveElevator. It
	 * sets the timeOut to 13.5 seconds.
	 */
	public BaseLine_With_Delay() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 110, p1, i1, d1); // 106
		moveElevator = new MoveElevatorAuto(1);
		elevIsDone = false;
		setTimeout(13.5);
	}

	/**
	 * Method called once the command is started. We use it to wait 10 seconds then
	 * start the first step of autonomous.
	 */
	public void initialize() {
		try {
			wait(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startTime = System.nanoTime() / 1000000000.;
		fidget.start();
		elevIsDone = false;
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		currentTime = System.nanoTime() / 1000000000.;

		if (moveElevator != null) moveElevator.isFinished();

		if (currentTime - startTime > 0.8 && !moveElevator.isRunning() && !moveElevator.isFinished()) {
			moveElevator.start();
		}

		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning()) && currentTime - startTime > 10) {
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
		if (firstDistance.isFinished()) return true;
		return isTimedOut();
	}

	/**
	 * Run once isFinished() returns true. Typically used in an auto path to shoot
	 * out the box at the end of it.
	 */
	@Override
	protected void end() {}
}
