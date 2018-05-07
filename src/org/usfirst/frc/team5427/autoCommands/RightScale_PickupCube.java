package org.usfirst.frc.team5427.autoCommands;

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
	RightScale_DriveForward moveToCube;
	
	/**
	 * Activated while moving forward in order to intake the cube.
	 */
	IntakeActivateIn intakeCube;
	
	public RightScale_PickupCube() {
		resetElevator = new MoveElevatorAuto(3);
		turnToCube = new RightScale_TurnToCube(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		moveToCube = new RightScale_DriveForward();
		intakeCube = new IntakeActivateIn();
	}
	
	@Override
	public void initialize() {
		resetElevator.start();
		turnToCube.start();
	}
	
	@Override
	public void execute() {
		if (null != turnToCube && turnToCube.isFinished() && null != resetElevator && resetElevator.isFinished()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			turnToCube.cancel();
			turnToCube = null;
			resetElevator.cancel();
			resetElevator = null;
			moveToCube.start();
			intakeCube.start();
		}
	}
	
	@Override
	public boolean isFinished() {
		return (null != moveToCube && moveToCube.isFinished());
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
	}
}
