// package org.usfirst.frc.team5427.autoCommands.right;

// import org.usfirst.frc.team5427.autoCommands.AutoPath;
// //import org.usfirst.frc.team5427.autoCommands.left.LeftScale_PickupCube;
// import org.usfirst.frc.team5427.robot.Robot;
// import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
// import org.usfirst.frc.team5427.robot.commands.DriveBackward;
// import org.usfirst.frc.team5427.robot.commands.Fidget;
// import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
// import org.usfirst.frc.team5427.util.SameLine;

// /**
//  * This is our autonomous path that starts in the right position and moves and
//  * turns 90 degrees towards the left side of the scale.
//  * 
//  * @author Blake Romero
//  */
// @SameLine
// public class Right_ScaleIsLeft extends AutoPath {

// 	/**
// 	 * The first distance of the path. It travels 224 inches forward at .7 power.
// 	 */
// 	private Right_ScaleIsLeft_FirstDistance_Curve firstDistance;
	
// 	private Right_ScaleIsLeft_Curve curve;
// 	private Right_ScaleIsLeft_MoveElevatorAuto moveElevator;

// 	/**
// 	 * The command used at the start of autonomous to drop the arms of the intake
// 	 * down.
// 	 */
// 	private Fidget fidget;

// 	/**
// 	 * 
// 	 * The time, in seconds, that we manually end our autonomous path.
// 	 */
// 	public static final double elevatorTimeout = 6;


// 	/**
// 	 * Creates all of the paths involved in Right_ScaleIsLeft.
// 	 */
// 	public Right_ScaleIsLeft() {
// 		requires(Robot.driveTrain);
// //		fidget = new Fidget();
// 		fidget=null;
// 		firstDistance = new Right_ScaleIsLeft_FirstDistance_Curve(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		moveElevator = new Right_ScaleIsLeft_MoveElevatorAuto();
// 		curve = new Right_ScaleIsLeft_Curve();
// 	}

// 	/**
// 	 * Run once when the command is started. Starts the first portion of the path
// 	 * and sets the timeout of the path.
// 	 */
// 	@Override
// 	public void initialize() {
// 		Robot.encLeft.reset();
// 		firstDistance.start(); 
// //		fidget.start();
// 		setTimeout(elevatorTimeout);
// 	}

// 	/**
// 	 * Runs periodically while the command is not finished. Used also to switch
// 	 * between commands at different points in our path.
// 	 */
// 	@Override
// 	public void execute() {
// //		SmartDashboard.putNumber("Motor Value", Robot.driveTrain.drive_Right.get());
		
// 		if(this.isTimedOut() && !moveElevator.isRunning()) {
// 			moveElevator.start();
// 		}
// 		if(this.isTimedOut() && Robot.tiltUpNext) {
// 			new TiltIntake_TimeOut().start();
// 		}
		
// 		if (null == fidget && null != firstDistance && firstDistance.isFinished()) {
// 			firstDistance.cancel();
// 			firstDistance = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			curve.start(); 
// 		}
// //		if(null !=fidget && fidget.isFinished()) {
// //			fidget.cancel();
// //			fidget = null;
// //			Robot.ahrs.reset();
// //			Robot.encLeft.reset();
// //			firstDistance.start(); 
// //		}
// 	}

// 	/**
// 	 * Runs periodically to check to see if the path can be finished.
// 	 * 
// 	 * @return true when the path has finished or the path has timed out.
// 	 */
// 	@Override
// 	public boolean isFinished() {
// 		return curve.isFinished();
// 	}

// 	/**
// 	 * Run once the isFinished() returns true.
// 	 */
// 	@Override
// 	protected void end() {
// 		moveElevator.cancel();
// 		new AutoOutGo().start();
// 		curve.cancel();
// 		new DriveBackward(1).start();
// //		new LeftScale_PickupCube().start();
// 	}

// }
