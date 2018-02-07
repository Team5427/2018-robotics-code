package org.usfirst.frc.team5427.robot.commands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
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
	private double time;
	
	public PIDTurnCommand(SpeedControllerGroup scgRight, SpeedControllerGroup scgLeft, double p, double i, double d, double setPoint) {
		super(p,i,d);
		System.out.println("TURN INIT");
		this.scgRight = scgRight;
		this.scgLeft = scgLeft;
		this.setPoint = setPoint;
		//lets the PID Loop the range of the input (ahrs)
		super.setInputRange(-180, 180);
		
		time =0;
		super.setSetpoint(setPoint);
		scgRight.set(0.1);
		scgLeft.set(0.1);

	}
	
	//begins the PID loop (enables)
	  public void initialize() {
		  Robot.ahrs.reset();
		  System.out.println("INITIALIZE");
		  time =0;
		  super.getPIDController().enable();
	  }
	  
	  //Ends (disables) the PID loop and stops the motors of the SpeedControllerGroups
	  public void end() {

		  
//		  Log.init("Ending PIDTurn");
		  	System.out.println("ENDING PIDTURN");
		    super.free();
//		    super.getPIDController().disable();
//		    super.getPIDController().free();

		    scgRight.set(0);	
		    scgLeft.set(0);

		  	super.end();

	  }

	  //Code to run when this command is interrupted
	  public void interrupted() {
	    end();
	  }
	
	
//judge range by what the angle is right now, ex: 91 instead of  90, we want to see if it flatlines
	public boolean isFinished() {
		 System.out.println(Math.abs(getCurrentAngle()-super.getSetpoint())+" IS FINISHED "+super.getSetpoint());
		
		boolean range =  Math.abs(Math.abs(getCurrentAngle())-Math.abs(super.getSetpoint()))<Config.PID_TURN_TOLERANCE;
		System.out.println("CurAngle: " +getCurrentAngle());
		System.out.println("Setpt: " +super.getSetpoint());
		if(range && time==0) {
			System.out.println("started time"+range);
			time = Timer.getFPGATimestamp();
		}
		if(!range) {
			System.out.println("out of range RANGE: "+Math.abs(Math.abs(getCurrentAngle())-Math.abs(super.getSetpoint())));
			if(time!=0)
				time =0;
		}
		
		if(range && time!=0) {
			System.out.println("within range"+range+" "+ Math.abs(Math.abs(getCurrentAngle())-Math.abs(super.getSetpoint())));
			time = Timer.getFPGATimestamp()-time;
			if(time>=3) {
				System.out.print("returned true");
				return true;
			}
		}
		
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
//		if(isFinished()) {
//			end();
//		}

	}
}
