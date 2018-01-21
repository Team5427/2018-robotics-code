package org.usfirst.frc.team5427.robot.OurClasses;

import org.usfirst.frc.team5427.util.SameLine;

/**
 * @author Ethan
 */

@SameLine
public class PIDAction {
	double startAngle;
	double endAngle;
	double currentAngle;
	
	public PIDAction(double startAngle, double endAngle, double currentAngle) {
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
}
