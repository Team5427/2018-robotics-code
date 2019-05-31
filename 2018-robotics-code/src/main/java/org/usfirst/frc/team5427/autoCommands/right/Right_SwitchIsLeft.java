// package org.usfirst.frc.team5427.autoCommands.right;

// import org.usfirst.frc.team5427.autoCommands.AutoPath;
// import org.usfirst.frc.team5427.robot.Robot;
// import org.usfirst.frc.team5427.robot.commands.Fidget;
// import org.usfirst.frc.team5427.util.SameLine;

// /**
//  * This is our autonomous path that starts in the right position and places one
//  * cube on the left side of the switch.
//  * 
//  * @author Blake Romero
//  */
// @SameLine
// public class Right_SwitchIsLeft extends AutoPath {

// 	/**
// 	 * The first distance of the path. It travels 18 inches forward at our short
// 	 * power.
// 	 */
// 	private Right_SwitchIsLeft_FirstDistance firstDistance;

// 	/**
// 	 * The second distance of the path. It travels 176 inches forward at our long
// 	 * power.
// 	 */
// 	private Right_SwitchIsLeft_SecondDistance secondDistance;

// 	/**
// 	 * The third distance of the path. It travels 70 inches forward at our short
// 	 * power.
// 	 */
// 	private Right_SwitchIsLeft_ThirdDistance thirdDistance;

// 	/**
// 	 * The first turn of the path. It turns 90 degrees clockwise.
// 	 */
// 	private Right_SwitchIsLeft_FirstAngle firstAngle;

// 	/**
// 	 * The second turn of the path. It turns 90 degrees counterclockwise.
// 	 */
// 	private Right_SwitchIsLeft_SecondAngle secondAngle;

// 	/**
// 	 * The command that moves the elevator up to its top position.
// 	 */
// 	private Right_SwitchIsLeft_MoveElevatorAuto moveElevator;

// 	/**
// 	 * The command used at the start of autonomous to drop the arms of the intake
// 	 * down.
// 	 */
// 	private Fidget fidget;

// 	/**
// 	 * The time, in seconds, that we manually end our autonomous path.
// 	 */
// 	public static final double timeOut = 15;





// 	/**
// 	 * Creates all of the paths involved in Right_SwitchIsLeft.
// 	 */
// 	public Right_SwitchIsLeft() {
// 		fidget = new Fidget();
// 		firstDistance = new Right_SwitchIsLeft_FirstDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		firstAngle = new Right_SwitchIsLeft_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		secondDistance = new Right_SwitchIsLeft_SecondDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		secondAngle = new Right_SwitchIsLeft_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		thirdDistance = new Right_SwitchIsLeft_ThirdDistance(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		moveElevator = new Right_SwitchIsLeft_MoveElevatorAuto();
// 	}

// 	/**
// 	 * Run once when the command is started. Starts the first portion of the path
// 	 * and sets the timeout of it.
// 	 */
// 	@Override
// 	public void initialize() {
// 		fidget.start();
// 		setTimeout(timeOut);
// 	}

// 	/**
// 	 * Runs periodically while the command is not finished. Used also to switch
// 	 * between commands at different points in our path.
// 	 */
// 	@Override
// 	public void execute() {

// 		if (moveElevator != null)
// 			moveElevator.isFinished();

// 		if (null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
// 			secondAngle.cancel();
// 			secondAngle = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			moveElevator.start();
// 			thirdDistance.start();
// 		}

// 		else if ((null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && ((secondDistance.isFinished() && !secondAngle.isRunning())))) {
// 			secondDistance.cancel();
// 			secondDistance = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			secondAngle.start();
// 		}

// 		else if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
// 			firstAngle.cancel();
// 			firstAngle = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			secondDistance.start();
// 		}

// 		else if ((null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning()))) {
// 			firstDistance.cancel();
// 			firstDistance = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			firstAngle.start();
// 		}

// 		else if ((null != fidget && fidget.isFinished() && !(firstDistance.isRunning()))) {
// 			fidget.cancel();
// 			fidget = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			firstDistance.start();
// 		}
// 	}

// 	/**
// 	 * Runs periodically to check to see if the path can be finished.
// 	 * 
// 	 * @return true when the path is finished or the path has timed out.
// 	 */
// 	@Override
// 	public boolean isFinished() {
// 		if (secondAngle == null && (thirdDistance.isFinished()))
// 			return true;
// 		return isTimedOut();
// 	}

// 	/**
// 	 * Run once when isFinished() returns true. Utilizes the end() of AutoPath to
// 	 * shoot out the box.
// 	 */
// 	@Override
// 	protected void end() {
// 		super.end();
// 	}
// }
