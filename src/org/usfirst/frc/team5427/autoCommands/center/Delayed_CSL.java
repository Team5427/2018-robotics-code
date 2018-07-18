package org.usfirst.frc.team5427.autoCommands.center;

import org.usfirst.frc.team5427.autoCommands.AutoPath;
import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.DriveForward;

public class Delayed_CSL extends AutoPath{
	
	public double waitTime = 5;
	
	public FidgetCL drive;
	
	public Delayed_CSL(double waitTime) {
		requires(Robot.driveTrain);
		this.waitTime = waitTime;
		this.drive = new FidgetCL();
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
