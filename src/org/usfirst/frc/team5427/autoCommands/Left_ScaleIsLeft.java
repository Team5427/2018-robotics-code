package org.usfirst.frc.team5427.autoCommands;

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
 * This is our autonomous path that starts in the left position
 * and places one cube on the left side of the scale.
 * 
 * @author Blake
 */
@SameLine
public class Left_ScaleIsLeft extends AutoPath {
	
	/**
	 * The first distance of the path. It travels 250 inches forward at our long power.
	 */
	private PIDStraightMovement firstDistance;
	
	/**
	 * The second distance of the path. It travels forward for .7 seconds.
	 */
	DriveForward secondDistance;
	
	/**
	 * The first turn of the path. It turns 47 degrees clockwise.
	 */
	private PIDTurn firstAngle;
	
	/**
	 * The command used at the start of autonomous to drop the arms of the intake down.
	 */
	private Fidget fidget;
	
	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double timeOut = 13;
	
	/**
	 * The command that moves the elevator up to its middle position.
	 */
	private MoveElevatorAuto moveElevator;
	
	/**
	 * The starting time of the autonomous path.
	 */
	private double startTime;
	
	/**
	 * The current time during the autonomous path.
	 */
	private double currentTime;
	
	/**********PID VALUES FOR 250 INCHES**********/
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

	/**
	 * Creates all of the paths involved in Left_ScaleIsLeft.
	 */
	public Left_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 250, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 47);
		secondDistance = new DriveForward(.7);
		moveElevator = new MoveElevatorAuto(2);
	}

	/**
	 * Run once when the command is started. Captures the start time of the path, 
	 * starts the first portion of the path and sets the timeout of it.
	 */
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();
		setTimeout(13.75);
	}
	
	/**
	 * Runs periodically while the command is not finished.
	 * Used also to switch between commands at different points in our path.
	 */
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		
		if(moveElevator.maxHeightReachedTime()&&Robot.tiltUpNext)
		{
			new TiltIntake_TimeOut().start();
		}
		
		if(currentTime-startTime>2.5&&!moveElevator.isRunning())
			moveElevator.start();
		
		if (null == fidget && null == firstDistance && firstAngle ==null&& moveElevator.maxHeightReachedTime() && (!secondDistance.isRunning())) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
		}
		if (null == fidget && null == firstDistance && firstAngle !=null&& firstAngle.isFinished() ) {
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
		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
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
	 * @return	true when the path is finished or the path has timed out and the elevator has reached maximum height.
	 */
	@Override
	public boolean isFinished() {
		if (firstAngle == null && secondDistance.isFinished())
			return true;
		return isTimedOut()&&this.moveElevator.maxHeightReached();
	}

	/**
	 * Run once when isFinished() returns true.
	 * Resets values and shoots out the cube, moving backward at the same time.
	 */
	@Override
	protected void end() {
		Robot.tiltUpNext=false;
		moveElevator.cancel();
		new AutoOutGo().start();
		secondDistance.cancel();
		new DriveBackward(1).start();
		super.end();
	}
}