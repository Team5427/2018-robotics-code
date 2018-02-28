package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

@SameLine
public class Center_SwitchIsRight extends AutoPath {
	private PIDStraightMovement firstDistance;
	private MoveElevatorAuto moveElevator;
	private Fidget fidget;
	
	// Values for 88 inches.
	public static final double p1 = 0.03;
	public static final double i1 = 0.0;
	public static final double d1 = 0.12;

	public Center_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 88, p1, i1, d1);
		moveElevator = new MoveElevatorAuto(1);
	}

	// begins the command
	public void initialize() {
		fidget.start();
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			Robot.encRight.reset();
			firstDistance.start();
			moveElevator.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (fidget == null && firstDistance.isFinished())
			return true;
		return false;
	}

	@Override
	protected void end() {
		super.end();
	}
}
