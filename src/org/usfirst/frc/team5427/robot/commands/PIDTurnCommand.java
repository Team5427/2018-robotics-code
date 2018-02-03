package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.Log;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command will cause the robot to turn to an exact degree value using the PID Loop
 * TODO add a ramping function
 * @author Ethan, Varsha
 */

@SameLine
public class PIDTurnCommand extends PIDCommand{
	
	//two SpeedControllerGroup objects to be controlled by this PID Loop
	private SpeedControllerGroup scgRight, scgLeft;
	double setPoint;
	
	public PIDTurnCommand(SpeedControllerGroup scgRight, SpeedControllerGroup scgLeft, double p, double i, double d, double setPoint) {
		super(p,i,d);
		System.out.println("TURN INIT");
		this.scgRight = scgRight;
		this.scgLeft = scgLeft;
		this.setPoint = setPoint;
		//lets the PID Loop the range of the input (ahrs)
		super.setInputRange(-180, 180);
	
		super.setSetpoint(setPoint);
		scgRight.set(0.1);
		scgLeft.set(0.1);
	}
	
	//begins the PID loop (enables)
	  public void initialize() {
		  Log.init("Initializing");
		  System.out.println("INITIALIZE");
	    super.getPIDController().enable();
	  }
	  
	  //Ends (disables) the PID loop and stops the motors of the SpeedControllerGroups
	  public void end() {
		  Log.init("Ending PIDTurn");
		  System.out.println("ENDING PIDTURN");
		    super.getPIDController().disable();
		    super.getPIDController().free();
		    scgRight.set(0);	
		    scgLeft.set(0);
	  }

	  //Code to run when this command is interrupted
	  public void interrupted() {
	    end();
	  }
	
	

	public boolean isFinished() {
//		 System.out.println(Math.abs(getCurrentAngle()-super.getSetpoint())+" IS FINISHED "+super.getSetpoint());
//		return Math.abs(getCurrentAngle()-super.getSetpoint())<Config.PID_TURN_TOLERANCE;
		return false;
	}
	

	public double getCurrentAngle() {
		return Robot.ahrs.getYaw();
	}

	//return what the PID loop is supposed to read from (feedback value)
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	//set motor values with "output"
	@Override
	protected void usePIDOutput(double output) {
		// TODO check if the negative signs are corresponding with the correct values

		SmartDashboard.putNumber("Yaw", getCurrentAngle());
		SmartDashboard.putNumber("Raw Yaw", getCurrentAngle());
		SmartDashboard.putNumber("PID Output", output);
		
		scgRight.pidWrite(output);
		scgLeft.set(output);
		if(isFinished()) {
			end();
		}
	}
}
