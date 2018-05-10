package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;

public class RightScale_SecondOnScale extends AutoPath {
	/**
	 * Rotates the robot 90 degrees clockwise to face the scale.
	 */
	RightScale_TurnToScale turnToScale;

	/**
	 * Moves the robot forward towards the scale.
	 */
	RightScale_DriveToScale driveToScale;

	/**
	 * Raises the elevator up to the scale position.
	 */
	MoveElevatorAuto elevatorToScale;

	public RightScale_SecondOnScale() {
		turnToScale = new RightScale_TurnToScale(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
		driveToScale = new RightScale_DriveToScale();
//		elevatorToScale = new MoveElevatorAuto(2);
	}
	
	@Override
	public void initialize() {
//		elevatorToScale.start();
		turnToScale.start();
	}
	
	@Override
	public void execute() {
		if (null != turnToScale && turnToScale.isFinished()){
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			turnToScale.cancel();
			turnToScale = null;
//			elevatorToScale.cancel();
//			elevatorToScale = null;
			driveToScale.start();
		}
	}
	
	@Override
	public boolean isFinished() {
		return (driveToScale != null && driveToScale.isFinished());
	}
	
	@Override
	public void end() {
		new AutoOutGo().start();
		driveToScale.cancel();
		new DriveBackward(1).start();
		super.end();
	}
}
