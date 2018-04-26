package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is the class to navigate to the left switch utilizing arcs
 * 
 * @author V
 */
@SameLine
public class SplineSwitch extends AutoPath{

	//use the command for DifferentialDrive
	/*public void arcadeDrive(double xSpeed,
            double zRotation)
	Arcade drive method for differential drive platform. The calculated values will be squared to decrease sensitivity at low speeds.
	Parameters:
	xSpeed - The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
	zRotation - The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.*/
	
	
	@Override
	public void initialize()
	{
		
	}
	
	
	@Override
	public void execute()
	{
		
	}
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
	protected void end() {
//		new AutoOutGo().start();
		super.end();
	}
}
