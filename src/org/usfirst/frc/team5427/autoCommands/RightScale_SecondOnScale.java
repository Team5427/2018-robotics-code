package org.usfirst.frc.team5427.autoCommands;

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
	
}
