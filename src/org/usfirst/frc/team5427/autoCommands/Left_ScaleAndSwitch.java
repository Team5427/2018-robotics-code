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

public class Left_ScaleAndSwitch extends AutoPath {
	private PIDStraightMovement firstDistance, thirdDistance;
	private DriveForward secondDistance, fourthDistance;
	private PIDTurn firstAngle, secondAngle;
	private MoveElevatorAuto moveElevatorScale, elevatorReset, moveElevatorSwitch;
	private AutoOutGo shootScale, shootSwitch;
	private IntakeActivateIn intake;
	private Fidget fidget;
	private double startTime, currentTime;
	
	public static final double p1 = 0.011;
	public static final double i1 = 0.0;
	public static final double d1 = 0.018;
	
	public static final double p2 = 0.011; //TODO: These vaues are NOT correct. Needs to be tuned for third distance(currently 70 inches)
	public static final double i2 = 0.0;
	public static final double d2 = 0.018;
	
	//Times
	public static final double timeOut1 = 15;

	public Left_ScaleAndSwitch() {
		// Creates all of the PID Commands
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 250, p1, i1, d1);
		secondDistance = new DriveForward(.7);
		thirdDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_LONG, 70, 0, 0, 0); //TODO: find real value for thirdDistance
		fourthDistance = new DriveForward(.7);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 47);
		secondAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 90);
		moveElevatorSwitch = new MoveElevatorAuto(1); // 1 for switch
		moveElevatorScale = new MoveElevatorAuto(2); // 2 for scale
		elevatorReset = new MoveElevatorAuto(3); // 3 to go back down
		intake = new IntakeActivateIn();
		shootScale = new AutoOutGo();
		shootSwitch = new AutoOutGo();
	}

	// Begins the command
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();		
	}

	// Uses the previous commands being null to check if a certain command needs to be started or not. Starts from the bottom of the method
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		
		if(null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null == moveElevatorSwitch)
		{
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			
			fourthDistance.cancel();
			fourthDistance = null;

			new AutoOutGo();
		}
		
		if(null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null == thirdDistance && null == intake && null != moveElevatorSwitch && moveElevatorSwitch.isFinished())
		{
			Robot.ahrs.reset();
			Robot.encLeft.reset();

			moveElevatorSwitch.cancel();
			moveElevatorSwitch = null;
			
			fourthDistance.start();
		}
		
		if(null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null == secondAngle && null != thirdDistance && thirdDistance.isFinished() && null != intake)
		{
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			
			thirdDistance.cancel();
			thirdDistance = null;
			
			intake.cancel();
			intake = null;

			moveElevatorSwitch.start();
		}
		
		// If all previous commands are null and secondDistance isFinished && not null, run the thirdDistance Command
		if(null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null == shootScale && null != secondAngle && secondAngle.isFinished())
		{
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			
			secondAngle.cancel();
			secondAngle = null;
			
			thirdDistance.start();
			intake.start();
		}
		if(null == fidget && null == firstDistance && null == firstAngle && null == secondDistance && null != shootScale && shootScale.isFinished())
		{
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			
			shootScale.cancel();
			shootScale = null;
			
			secondAngle.start();
			elevatorReset.start();
			new TiltIntake_TimeOut().start();
		}
		if(null == fidget && null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && moveElevatorScale.maxHeightReachedTime())
		{
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
		if (null == fidget && null == firstDistance && firstAngle != null && firstAngle.isFinished() ) {
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
		}
		if(moveElevatorScale != null)
		{
			moveElevatorScale.isFinished();
		}
		if(moveElevatorScale.maxHeightReachedTime()&&Robot.tiltUpNext)
		{
			new TiltIntake_TimeOut().start(); //TODO make var
		}
		if(currentTime-startTime>2.5&&!moveElevatorScale.isRunning())
		{
			moveElevatorScale.start();
		}
		else if (null == fidget && null != firstDistance && firstDistance.isFinished()) {
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

	@Override
	public boolean isFinished() {
		return isTimedOut();
		
//		if (firstDistance != null)
//			return firstDistance.isFinished();
//		return false;
	}
	
	@Override
	protected void end() {
		super.end();
	}
}
