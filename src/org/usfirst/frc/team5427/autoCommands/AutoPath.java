package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.command.Command;

@SameLine
public class AutoPath extends Command {
	
	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		super.end();
		new AutoOutGo().start();
	}
}
