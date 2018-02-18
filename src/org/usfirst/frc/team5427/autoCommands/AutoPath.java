package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.commands.AutoOutGo;

import edu.wpi.first.wpilibj.command.Command;

public class AutoPath extends Command{

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end()
	{
		super.end();
		new AutoOutGo().start();
	}
}
