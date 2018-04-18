package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This is the base for all of our autonomous paths.
 * @author Blake
 */
@SameLine
public class AutoPath extends Command {
	
	/**
	 * This is run periodically to check to see if
	 * the command is finished.
	 */
	@Override
	public boolean isFinished() {
		return false;
	}

	/**
	 * This is run once when the command's is finished
	 * returns true.
	 */
	@Override
	protected void end() {
		new AutoOutGo().start();
		super.end();
		
	}
}
