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
	private Right_ScaleIsLeft_FirstDistance_Curve firstDistance;
	
	/**
	 * The first distance of the path. It travels 224 inches forward at .7 power.
	 */
	private Right_ScaleIsLeft_Curve curve;

	private Right_ScaleIsLeft_MoveElevatorAuto moveElevator;

	/**
	 * The command used at the start of autonomous to drop the arms of the intake
	 * down.
	 */
	private Fidget fidget;

	/**
	 * The time, in seconds, that we manually end our autonomous path.
	 */
	public static final double elevatorTimeout = 6;


	/**
	 * Creates all of the paths involved in Right_ScaleIsLeft.
	 */
	public Right_ScaleIsLeft() {
		fidget = new Fidget();
		firstDistance = new Right_ScaleIsLeft_FirstDistance_Curve(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveElevator = new Right_ScaleIsLeft_MoveElevatorAuto();
		curve = new Right_ScaleIsLeft_Curve();
	}

	/**
	 * Run once when the command is started. Starts the first portion of the path
	 * and sets the timeout of the path.
	 */
	public void initialize() {
		Robot.encLeft.reset();
		firstDistance.start();
		setTimeout(elevatorTimeout);
	}

	/**
	 * Runs periodically while the command is not finished. Used also to switch
	 * between commands at different points in our path.
	 */
	public void execute() {
		SmartDashboard.putNumber("Motor Value", Robot.driveTrain.drive_Right.get());
		
		if(this.isTimedOut() && !moveElevator.isRunning()) {
			moveElevator.start();
		}
		if(moveElevator.maxHeightReachedTime() && Robot.tiltUpNext) {
			new TiltIntake_TimeOut().start();
		}
		if ( null != firstDistance && firstDistance.isFinished()) {
			System.out.print("Curve starting");
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
		return curve.isFinished();
	}

	/**
	 * Run once the isFinished() returns true.
	 */
	@Override
	protected void end() {
		Robot.tiltUpNext = false;
		moveElevator.cancel();
		new AutoOutGo().start();
		curve.cancel();
		
	}

}
