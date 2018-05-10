package org.usfirst.frc.team5427.autoCommands.right;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.DriveBackward;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;

public class RightScale_SecondOnSwitch extends AutoPath{
	/**
	 * Moves the elevator up to be able to place it on the switch.
	 */
	MoveElevatorAuto elevatorToSwitch;
	
	/**
	 * Moves the robot forward into the switch to place the cube on it.
	 */
	RightScale_DriveToSwitch driveToSwitch;
	
	/**
	 * Timeout to simulate the use of the elevator.
	 */
	private double elevatorTimeout = 2.0;
	
	public RightScale_SecondOnSwitch() {
		elevatorToSwitch = new MoveElevatorAuto(1);
		driveToSwitch = new RightScale_DriveToSwitch();
	}
	
	@Override
	public void initialize() {
		this.setTimeout(elevatorTimeout);
//		elevatorToSwitch.start();
	}
	
	@Override
	public void execute() {
//		if(null != elevatorToSwitch && elevatorToSwitch.isFinished()) {
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
//			elevatorToSwitch.cancel();
//			elevatorToSwitch = null;
//			driveToSwitch.start();
//		}
		if(this.isTimedOut() && null != driveToSwitch && !driveToSwitch.isRunning()) {
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			this.setTimeout(100);
			driveToSwitch.start();
		}
	}
	
	@Override
	public boolean isFinished() {
		return (null != driveToSwitch && driveToSwitch.isFinished());
	}
	
	@Override
	public void end() {
		driveToSwitch.cancel();
		driveToSwitch = null;
		new AutoOutGo().start();
		super.end();
		new DriveBackward(1).start();
	}
}

