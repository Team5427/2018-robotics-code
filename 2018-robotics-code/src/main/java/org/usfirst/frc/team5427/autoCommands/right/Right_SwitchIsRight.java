// package org.usfirst.frc.team5427.autoCommands.right;

// import org.usfirst.frc.team5427.autoCommands.AutoPath;
// import org.usfirst.frc.team5427.robot.Robot;
// import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
// import org.usfirst.frc.team5427.robot.commands.Fidget;
// import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
// import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
// import org.usfirst.frc.team5427.util.SameLine;

// /**
//  * This is our autonomous path that starts in the Left position and places one
//  * cube on the Left side of the scale.
//  * 
//  * @author Akshat Jain
//  */
// @SameLine
// public class Right_SwitchIsRight extends AutoPath {


// 	/**
// 	 * The first distance of the path. It travels 150 inches forward at our long
// 	 * power.
// 	 */
// 	private Right_SwitchIsRight_FirstDistance firstDistance;

	

// 	/**
// 	 * The command used to move the elevator up to the top of its path.
// 	 */
// 	private MoveElevatorAuto moveElevator;
	
// 	private Right_SwitchIsRight_CurveToSwitch curve;

// 	/**
// 	 * The command used at the start of autonomous to drop the arms of the intake
// 	 * down.
// 	 */
// 	private Fidget fidget;

// 	/**
// 	 * The starting time of the autonomous path.
// 	 */
// 	private double startTime;

// 	/**
// 	 * The current time during the autonomous path.
// 	 */
// 	private double currentTime;

// 	/**
// 	 * The time, in seconds, that we manually end our autonomous path.
// 	 */
// 	public static final double timeOut = 15;


// 	/*********************************************/

// 	/**
// 	 * Creates all of the paths involved in Left_ScaleIsLeft.
// 	 */
// 	public Right_SwitchIsRight() {
// 		fidget = new Fidget();
// 		firstDistance = new Right_SwitchIsRight_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		curve = new Right_SwitchIsRight_CurveToSwitch();
// 		moveElevator = new MoveElevatorAuto(1);

// 	}

// 	/**
// 	 * Run once when the command is started. Captures the start time of the path,
// 	 * starts the first portion of the path and sets the timeout of the path.
// 	 */
// 	@Override
// 	public void initialize() {
// 		startTime = System.nanoTime() / 1000000000.;
// 		firstDistance.start();
// 		moveElevator.start();
// 		setTimeout(timeOut);
// 	}

// 	/**
// 	 * Runs periodically while the command is not finished. Used also to switch
// 	 * between commands at different points in our path.
// 	 */
// 	@Override
// 	public void execute() {
// 		currentTime = System.nanoTime() / 1000000000.;

// 		if (moveElevator != null)
// 			moveElevator.isFinished();

// 		if (moveElevator.maxHeightReachedTime() && Robot.tiltUpNext) {
// 			new TiltIntake_TimeOut().start();
// 		}


// 		if(null!=firstDistance && firstDistance.isFinished() && !(curve.isRunning())) {
// 			firstDistance.cancel();
// 			firstDistance = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
			
// 			curve.start();
// 		}
		
// 		if(null!=fidget && fidget.isFinished()) {
// 			fidget.cancel();
// 			fidget = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
			
// 			firstDistance.start();
// 			moveElevator.start();
// 		}
// 	}

// 	/**
// 	 * Runs periodically to check to see if the path can be finished.
// 	 * 
// 	 * @return true when the path has finished or the path has timed out.
// 	 */
// 	@Override
// 	public boolean isFinished() {
// 		if (curve.isFinished())
// 			return true;
// 		return isTimedOut() && this.moveElevator.maxHeightReached();
// 	}

// 	/**
// 	 * Run once the isFinished() returns true. Resets values and shoots out any cube
// 	 * contained in the robot. Drives backwards afterwards.
// 	 */
// 	@Override
// 	protected void end() {
// 		super.end();
// 		Robot.tiltUpNext = false;
// 		moveElevator.cancel();
// 		new AutoOutGo().start();
// 		curve.cancel();
// 	}
// }
