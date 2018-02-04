package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDPath extends PIDCommand
{
	private SpeedControllerGroup scgRight, scgLeft;
	
	public PIDPath(SpeedControllerGroup scgRight, SpeedControllerGroup scgLeft, double p, double i, double d)
	{
		super(p,i,d);
		this.scgRight = scgRight;
		this.scgLeft = scgLeft;
		//lets the PID Loop the range of the input (ahrs)
		super.setInputRange(-180, 180);
		scgRight.set(0.3);
		scgLeft.set(0.3);
	}


	//begins the PID loop (enables)
	  public void initialize() {
		  System.out.println("INITIALIZE");
	    super.getPIDController().enable();
	  }
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
