package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class PIDPath extends Command {
	public PIDDriveTrainSide firstDistance, secondDistance, thirdDistance;
	public PIDTurnCommand firstAngle, secondAngle;
	private int countWait;
	private boolean isDone;
	private boolean isDone1;

	public PIDPath() {
		// creates all of the PID Commands
		firstDistance = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0, 60);// 160
		firstAngle = new PIDTurnCommand(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, 25);
		secondDistance = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0, 40);// 36
		secondAngle = new PIDTurnCommand(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_TURN_P, Config.PID_TURN_I, Config.PID_TURN_D, 35);
		thirdDistance = new PIDDriveTrainSide(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, 0, 30);// 12
		countWait=0;
		isDone = false;
		isDone1 = false;
		
	}

	// begins the command
	public void initialize() {
		firstDistance.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		// TODO Check this, we may get null pointer exceptions if this is wrong
		// If firstDistance, first angle, and secondDistance are all null and
		// SecondAngle isFinished
		// and the thirdDistance Command is not running, run the thirdDistance Command
		if (null == firstDistance && null == firstAngle && null == secondDistance && null != secondAngle && secondAngle.isFinished() && !(thirdDistance.isRunning())) {
			
				secondAngle.cancel();
				secondAngle = null;
				Robot.ahrs.reset();
				thirdDistance.start();
				countWait=0;
			
		}
		// If firstDistance, first angle are all null and secondDistance isFinished &&
		// not null
		// and the secondAngle Command is not running, run the secondAngle Command
		else if ((null == firstDistance && null == firstAngle && null != secondDistance && secondDistance.isFinished() && !secondAngle.isRunning())||isDone1) {
			
			if(!isDone1) {
				isDone1=true;
				secondDistance.cancel();
				secondDistance = null;
			}
			
			countWait++;
			if(countWait==100) {
				
				Robot.ahrs.reset();
				secondAngle.start();
				countWait=0;
				isDone1=false;
			}
			
		}
		// If firstDistance is null and firstAngle isFinished && not null
		// and the secondDistance Command is not running, run the secondDistance Command
		else if (null == firstDistance && null != firstAngle && firstAngle.isFinished() && !secondDistance.isRunning()) {
			
				firstAngle.cancel();
				firstAngle = null;
				Robot.ahrs.reset();
				secondDistance.start();
				countWait=0;
			
		}
		// If firstDistance is NOT null and firstDistance isFinished
		// and the firstAngle Command is not running, run the firstAngle Command
		else if ((null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning()))||isDone) {
//			firstDistance.end();
			//TODO: REPLACE WITH WAIT COMMAND
			if(!isDone) {
				isDone=true;
				firstDistance.cancel();
				firstDistance = null;
			}
			countWait++;
			if(countWait==100) {
				Robot.ahrs.reset();
				firstAngle.start();
				countWait=0;
				isDone=false;
			}
		}
		// OLDER IDEA
		// //if the firstDistance command exists and has finished, and the angle command
		// is not running
		// if(firstDistance!=null&&firstDistance.isFinished()&&!firstAngle.isRunning())
		// {
		// //set the firstDistance command to null and start the firstAngle command
		// firstDistance=null;
		// firstAngle.start();
		// }
	}

	@Override
	protected boolean isFinished() {
		// returns if the last distance has finished
		if (thirdDistance != null)
			return thirdDistance.isFinished();
		return false;
	}
	// @Override
	// protected void end() {
	// firstAngle.free();
	// firstDisance
	// }
}
