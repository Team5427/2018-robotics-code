package org.usfirst.frc.team5427.autoCommands;

import edu.wpi.first.wpilibj.command.Command;

public class Center_SwitchIsLeft_CoastToSwitch extends Command{

	/**
	 * The time that the robot is allowed to coast towards the switch.
	 */
	public double timeout = 1.5;
	
	@Override
	public void initialize()
	{
		this.setTimeout(this.timeout);
	}
	
	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}
}