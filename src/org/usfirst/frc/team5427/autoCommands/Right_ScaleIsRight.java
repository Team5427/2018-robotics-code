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

@SameLine
public class Right_ScaleIsRight extends AutoPath {
	private PIDStraightMovement firstDistance;
	DriveForward secondDistance;
	private PIDTurn firstAngle;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	private double startTime, currentTime;
	
	//Time
	public static final double timeOut1 = 0;
	
	// Values for 18 inches.
	public static final double p1 = 0.011;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;

	
	
	public Right_ScaleIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 250, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -51);
		secondDistance = new DriveForward(1);
		moveElevator = new MoveElevatorAuto(2);
		
		setTimeout(13.75);
	}

	// begins the command
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		if(moveElevator.maxHeightReachedTime()&&Robot.tiltUpNext)
		{
			SmartDashboard.putBoolean("YES WE ARE IN THE IF", true);
			new TiltIntake_TimeOut().start(); //TODO make var
		}
		//starts elevator raising when we are 2.5 sec. into auto
		if(currentTime-startTime>2.5&&!moveElevator.isRunning())
			moveElevator.start();
		if (null == fidget && null == firstDistance && firstAngle ==null&& moveElevator.maxHeightReachedTime() && (!secondDistance.isRunning())) {
			System.out.println("Part 17 Done.");
//			firstAngle.cancel();
//			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			secondDistance.start();
//			Robot.encRight.reset();
//			secondDistance.start();
//			moveElevator.start();
		}
		if (null == fidget && null == firstDistance && firstAngle !=null&& firstAngle.isFinished() ) {
			System.out.println("Part 1 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
//			secondDistance.start();
//			&& !(moveElevator.isRunning())
		}
		
		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning())) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstAngle.start();
		}
		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
//			Robot.encRight.reset();
//			new MoveElevatorAuto(1).start();
			firstDistance.start();
		}
//		else if(null != fidget && fidget.isFinished() && !(fidgetSpinner.isRunning())) {
//			System.out.println("Fidget Done.");
//			fidget.cancel();
//			fidget = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
////			Robot.encRight.reset();
//			fidgetSpinner.start();
//		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (firstAngle == null && secondDistance.isFinished())
			return true;
		return isTimedOut()&&this.moveElevator.maxHeightReached();
	}

	@Override
	protected void end() {
		Robot.tiltUpNext=false;

//		Robot.tiltUpNext=false;
		moveElevator.cancel();
		new AutoOutGo().start(); 
		secondDistance.cancel();
		new DriveBackward(1).start();
		super.end();
	}
}
