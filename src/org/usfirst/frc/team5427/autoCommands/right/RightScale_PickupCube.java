package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateIn;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;

public class RightScale_PickupCube extends AutoPath{
	/**
	 * Moves the elevator back down to the resting position.
	 */
	MoveElevatorAuto resetElevator;
	
	/**
	 * Rotates the robot 90 degrees counterclockwise to face the cube.
	 */
	RightScale_TurnToCube turnToCube;
	
	/**
	 * Drives forward into the cube's position.
	 */
	RightScale_DriveToCube moveToCube;
	
	/**
	 * Drives backward away from the scale.
	 */
	RightScale_DriveBackward backOff;
	
	/**
	 * Activated while moving forward in order to intake the cube.
	 */
	IntakeActivateIn intakeCube;
	
	public RightScale_PickupCube() {
		backOff = new RightScale_DriveBackward();
		resetElevator = new MoveElevatorAuto(3);
		turnToCube = new RightScale_TurnToCube(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveToCube = new RightScale_DriveToCube();
		intakeCube = new IntakeActivateIn();
		resetElevator = null;
		intakeCube = null;
	}
	
	@Override
	public void initialize() {
		resetElevator.start();
		backOff.start();
	}
	
	@Override
	public void execute() {
		if (null == backOff && null != turnToCube && turnToCube.isFinished() && !moveToCube.isRunning() && null == resetElevator) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			turnToCube.cancel();
			turnToCube = null;
			resetElevator.cancel();
			resetElevator = null;
			moveToCube.start();
			intakeCube.start();
		}
		
		else if(null != backOff && backOff.isFinished() && !turnToCube.isRunning()) {
			Robot.ahrs.reset();
			backOff.cancel();
			backOff = null;
			turnToCube.start();
		}
		
	} 
	
	@Override
	public boolean isFinished() {
		return (null == turnToCube && null != moveToCube && moveToCube.isFinished());
	}
	
	@Override
	public void end() {
		moveToCube.cancel();
		moveToCube = null;
		intakeCube.cancel();
		intakeCube = null;
		Robot.ahrs.reset();
		Robot.encLeft.reset();
		Robot.driveTrain.drive.stopMotor();
//		new RightScale_SecondOnScale().start();
//		new RightScale_SecondOnSwitch().start();
	}
}
