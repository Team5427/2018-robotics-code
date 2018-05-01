package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is the parent for all of our autonomous paths.
 * 
 * @author Blake Romero
 */
@SameLine
public class AutoPath extends Command {

	/**
	 * This is run periodically to check to see if the command is finished.
	 * 
	 * @return false by default but can be modified in each child path.
	 */
	@Override
	public boolean isFinished() {
		return false;
	}

	/**
	 * This is run once when the command is finished.
	 */
	@Override
	public void end() {
//		new AutoOutGo().start();
		super.end();
	}
}
