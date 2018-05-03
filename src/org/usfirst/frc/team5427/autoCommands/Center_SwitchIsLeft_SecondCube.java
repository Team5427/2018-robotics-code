package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoInGo;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.DriveForward;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Picks up the second cube and places it for Center_SwitchIsLeft.
 * 
 * @author Akshat Jain, Blake Romero, Andrew Li
 */
@SameLine
public class Center_SwitchIsLeft_SecondCube extends AutoPath {
	/**
	 * Moves the robot backwards from the switch while moving the elevator down.
	 */
	public DriveBackward backOffFromSwitch;
	
	/**
	 * Moves the robot backwards from the cube.
	 */
	public DriveBackward backOffFromCube;
	
	/**
	 * Moves the robot forwards towards the switch while moving the elevator up.
	 */
	public DriveForward moveForwardToSwitch;
	
	/**
	 * Moves the robot forwards towards the cube.
	 */
	public DriveForward moveForwardToCube;
	
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
	public Center_SwitchIsLeft_FirstAngle angleToCube;
	/**
	 * Curves the robot away from the cube it intends to pick up.
	 */
	public Center_SwitchIsLeft_SecondAngle angleToSwitch;

	public Center_SwitchIsLeft_SecondCube() {
		backOffFromSwitch = new DriveBackward(1);
		backOffFromCube = new DriveBackward(.5);
		elevatorDown = new MoveElevatorAuto(4);
		angleToCube = new Center_SwitchIsLeft_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		intakeCube = new AutoInGo();
		angleToSwitch = new Center_SwitchIsLeft_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveForwardToSwitch = new DriveForward(1);
		moveForwardToCube = new DriveForward(0.5);
		elevatorUp = new MoveElevatorAuto(1);
	}

	@Override
	public void initialize() {
		backOffFromSwitch.start();
		elevatorDown.start();
	}

	@Override
	public void execute() {
		if(null == backOffFromSwitch && null == angleToCube && null== moveForwardToCube && null == backOffFromCube && null != angleToSwitch && angleToSwitch.isFinished()) {
			angleToSwitch.cancel();
			angleToSwitch = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveForwardToSwitch.start();
			
		}
		else if(null == backOffFromSwitch && null == angleToCube && null== moveForwardToCube && null!= backOffFromCube && backOffFromCube.isFinished()) {
			backOffFromCube.cancel();
			backOffFromCube = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			backOffFromCube.start();
			angleToSwitch.start();
			elevatorUp.start();
		}
		else if(null == backOffFromSwitch && null == angleToCube && null!= moveForwardToCube && moveForwardToCube.isFinished()) {
			moveForwardToCube.cancel();
			moveForwardToCube = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			backOffFromCube.start();
			intakeCube.cancel();
		}
		else if(null == backOffFromSwitch && null != angleToCube && angleToCube.isFinished()){
			angleToCube.cancel();
			angleToCube = null;
			intakeCube.start();
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveForwardToCube.start();
		}
		else if(null != backOffFromSwitch && backOffFromSwitch.isFinished()) {
			backOffFromSwitch.cancel();
			backOffFromSwitch = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			angleToCube.start();
		}
		
	}

	@Override
	public boolean isFinished() {
		return (null != moveForwardToSwitch && moveForwardToSwitch.isRunning() && moveForwardToSwitch.isFinished());
	}
	
	@Override
	public void end() {
		new AutoOutGo().start();
		super.end();
	}
}
