/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.autoCommands.center;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;

/**
 * This command is used to move forward and backward rapidly at the start of
 * autonomous in order to drop the arms of the intake downward.
 * 
 * @author Blake Romero
 */
public class FidgetCL extends AutoPath {

	/**
	 * Stores whether the forward portion of the fidget is completed.
	 */
	private boolean forwardDone;

	/**
	 * Fidget requires the drive train subsystem. Sets the timeout of the command to
	 * .1 seconds.
	 */
	public FidgetCL() {
		requires(Robot.driveTrain);
		this.forwardDone = false;
		setTimeout(0.1);
	}

	/**
	 * Called once when the command is started but is not used for anything.
	 */
	@Override
	protected void initialize() {}

	/**
	 * Called periodically while the command is not finished. Moves the robot
	 * forward at .5 speed for .1 seconds, and then backward at .3 for .6 seconds.
	 */
	@Override
	protected void execute() {
		if (!forwardDone) {
			Robot.driveTrain.drive_Left.set(0.5);
			Robot.driveTrain.drive_Right.set(-0.5);
		}
		else if (forwardDone) {
			Robot.driveTrain.drive_Left.set(-0.3);
			Robot.driveTrain.drive_Right.set(0.3);
		}
	}

	/**
	 * Called periodically while the command running to see if it is finished.
	 * 
	 * @return if the robot is done moving forward and backward.
	 */
	@Override
	public boolean isFinished() {
		if (this.isTimedOut() && !forwardDone) {
			forwardDone = true;
			setTimeout(0.6);
		}
		else if (this.isTimedOut() && forwardDone) { return true; }
		return false;
	}

	/**
	 * Called once when the command is finished. Sets the velocity of the drive
	 * train to 0 power.
	 */
	@Override
	public void end() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
		super.end();
		new Center_SwitchIsLeft_Curve().start();
	}

	/**
	 * Called once when the command is interrupted. Sets the velocity of the drive
	 * train to 0 power.
	 */
	@Override
	protected void interrupted() {
		Robot.driveTrain.drive_Left.set(0);
		Robot.driveTrain.drive_Right.set(0);
	}
}
