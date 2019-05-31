package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.DriveForward;

public class Delayed_Baseline extends AutoPath{
	
	public double waitTime = 5;
	
	public DriveForward drive;
	
	public Delayed_Baseline(double waitTime) {
		requires(Robot.driveTrain);
		this.waitTime = waitTime;
		this.drive = new DriveForward(4);
	}
	
	@Override
	public void initialize() {
		setTimeout(this.waitTime);
	}
	
	@Override
	public void execute() {
		if(this.isTimedOut() && drive != null && !drive.isRunning()) {
			drive.start();
		}
	}
	
	@Override
	public boolean isFinished() {
		return (drive != null && drive.isFinished());
	}
	
	@Override
	public void end() {
		drive.cancel();
		drive = null;
		Robot.driveTrain.drive.stopMotor();
	}
}
