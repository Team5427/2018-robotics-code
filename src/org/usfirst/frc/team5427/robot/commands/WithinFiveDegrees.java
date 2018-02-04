package org.usfirst.frc.team5427.robot.commands;

/**
 * This class will cause the robot to get within five degrees of the target angle 
 * so that the PID Loop can take over afterwards. We may soon find this comand unnecessary
 * 
 */

public class WithinFiveDegrees {
	private double startAngle;
	private double endAngle;
	private double currentAngle;
	
	public WithinFiveDegrees(double startAngle, double endAngle, double currentAngle) {
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		this.currentAngle = currentAngle;
	}
	
	public final static double TOLERANCE = .5;

	public boolean isFinished() {
		return Math.abs(currentAngle-endAngle)<TOLERANCE;
	}
	
	public double getStartAngle() {
		return startAngle;
	}

	public double getEndAngle() {
		return endAngle;
	}

	public double getCurrentAngle() {
		return currentAngle;
	}
	
	public void execute()
	{
		//TODO Add code
	}
}
