package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoInGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Picks up the second cube and places it for Center_SwitchIsLeft.
 * 
 * @author Blake Romero, Andrew Li
 */
@SameLine
public class Center_SwitchIsLeft_SecondCube extends AutoPath {
	/**
	 * Moves the robot backwards from the switch while moving the elevator down.
	 */
	public DriveBackward backOff;
	/**
	 * Moves the robot forwards towards the switch while moving the elevator up.
	 */
	public DriveForward moveForward;
	/**
	 * Pulls the second cube into the robot using the intake.
	 */
	public AutoInGo intakeCube;
	/**
	 * Moves the elevator down to the lowest position.
	 */
	public MoveElevatorAuto elevatorDown;
	/**
	 * Moves the elevator up to the middle position.
	 */
	public MoveElevatorAuto elevatorUp;
	/**
	 * Curves the robot towards the cube it intends to pick up.
	 */
	public Center_SwitchIsLeft_CurveToCube curveToCube;
	/**
	 * Curves the robot away from the cube it intends to pick up.
	 */
	public Center_SwitchIsLeft_CurveAwayFromCube curveBack;

	public Center_SwitchIsLeft_SecondCube() {
		backOff = new DriveBackward(1);
		elevatorDown = new MoveElevatorAuto(4);
		curveToCube = new Center_SwitchIsLeft_CurveToCube();
		intakeCube = new AutoInGo();
		curveBack = new Center_SwitchIsLeft_CurveAwayFromCube();
		moveForward = new DriveForward(1);
		elevatorUp = new MoveElevatorAuto(1);
	}

	@Override
	public void initialize() {
		backOff.start();
//		elevatorDown.start();
	}

	@Override
	public void execute() {
		if(null == backOff && null == curveToCube && null != curveBack && curveBack.isFinished()) {
			curveBack.cancel();
			curveBack = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveForward.start();
//			elevatorUp.start();
		}
		else if(null == backOff && null != curveToCube && curveToCube.isFinished()){
			curveToCube.cancel();
			curveToCube = null;
//			intakeCube.cancel();
//			intakeCube = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			curveBack.start();
		}
		else if(null != backOff && backOff.isFinished()) {
			backOff.cancel();
			backOff = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			curveToCube.start();
//			intakeCube.start();
		}
		
	}

	@Override
	public boolean isFinished() {
		return (null != moveForward && moveForward.isRunning() && moveForward.isFinished());
	}
	
	@Override
	public void end() {
		super.end();
	}
}
