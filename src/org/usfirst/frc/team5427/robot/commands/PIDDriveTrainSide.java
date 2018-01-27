package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.Log;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * This class is adapted from the WPILib PIDCommand Class. 
 * This Class is made to make the robot drive straight after ramping up power to 
 * a desired power. It works by setting one side of the drive train to the desired power 
 * and varying the speed of the other side of the drive train to maintain the robot
 * driving exactly straight forward (a YAW of 0)
 * 
 * 
 * In theory, this class can be used in any case where two SpeedControllerGroup objects
 * work together to accomplish the same task.
 */

public class PIDDriveTrainSide extends PIDCommand{
	

	  
	  private SpeedControllerGroup scgPIDControlled;
	  private SpeedControllerGroup scgConstant;
	  private double power;
	  private double increment;
	  
	  private double toGoalTime;
	  private double startTime;
	  //increment every other iteration, tried it but did not make significant diff, may come back
	 // private boolean flipFlop;
	 
	 
	  /**
	   * Constructor for this class
	   * @param scgPIDControlled - the SpeedControllerGroup for the side whose power 
	   * 							will be controlled via the PID Loop
	   * @param scgConstant - the SpeedControllerGroup for teh side whose power will be
	   * 					set to a constant value
	   * @param p - the P value for the PID Loop
	   * @param i - the I value for the PID Loop
	   * @param d - the D value for the PID Loop
	   * @param setpoint - the Set Point for the PID Loop
	   */
	  public PIDDriveTrainSide(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double p, double i, double d, double setpoint) {
		  super(p,i,d);
	    Log.init("PIDDriveTrainRight created");
//	    m_controller = new PIDController(p, i, d, m_source, m_output);
	    super.setSetpoint(setpoint);
	    this.scgPIDControlled=scgPIDControlled;
	    this.scgConstant = scgConstant;
	    
	    resetOurValues();
	    super.setSetpoint(setpoint);
	    initialize();
	    
	  }

	

	  @Override
	  //begins the PID loop (enables)
	  protected void initialize() {
		  Log.init("Initializing");
		  super.getPIDController().enable();
	  }
	  
	  //Ends (disables) the PID loop and stops the motors of the SpeedControllerGroups
	  @Override
	  protected void end() {
		  Log.info("Ending PID");
		super.getPIDController().disable();
	    scgPIDControlled.set(0);	
	    scgConstant.set(0);
	    resetOurValues();
	    super.getPIDController().reset();
	  }

	  //Code to run when this command is interrupted
	  @Override
	  protected void interrupted() {
	    end();
	    Log.info("Interrupted");
	  }
	  

	  @Override
	  protected boolean isFinished()
	  {
//			if(/*robot encoder value is larger than desiredDistance - Config.getCoastDistance()*/)
//			if(0>=Config.getCoastingDistance(desiredPower))//TODO Create Encoders in Robot and use their value here and use 		  	
		  //parameter desiredDistance

		  	//If the robot is disabled
		  	if(RobotState.isDisabled())
		  	{
		  		 end();
				return true;
		  	}
			return false;
	  }
	 
	  public void setPower(double power) {
		  this.power = power;
	  }
	  
	  /**
	   * reutrns the input of the PID LOOP (AKA AHRS Yaw)
	   */
	  protected double returnPIDInput()
	  {
		  return Robot.ahrs.getYaw(); 
	  }

	  /**
	   * method to set motor values using the parameter "output"
	   * This method is automatically called by PIDCommand
	   */
	  @Override
	  protected void usePIDOutput(double output) {
		  SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		
		  //setting right side to pidOutput
		  scgPIDControlled.set(output); 
		 
		  //if current power is less than the goal, increment the power
		  if(this.power<Config.PID_STRAIGHT_POWER) {
			  this.power+=increment;
		  }
		  //else if it is equal to goal, print the time it took, and iterations
		  else {
			  if(toGoalTime==0) {
				  toGoalTime=System.nanoTime()/1000000000.0 - this.startTime;
				  Log.info("TIME TO REACH PIDPOWER: " + toGoalTime);
				  Log.info("Iterations: " + this.power/this.increment);
			  }
				  
		  }
		 
		  scgConstant.set(power);
		  SmartDashboard.putNumber("PID output", output);
	  }
	  
	  @Override
	  public void free() {
		  Log.info("running free()");
		  super.free();
		  super.getPIDController().disable();
		  resetOurValues();
		  scgPIDControlled.set(0);	
		  scgConstant.set(0);
		  super.getPIDController().reset();
	  }
	  /**
	   * resets the values of certain 
	   */
	  public void resetOurValues() {
		  	this.power = 0;
		    this.scgPIDControlled.set(0);
		    this.scgConstant.set(0);
		    this.increment=.01;//TODO move to Config
		    this.startTime = System.nanoTime()/1000000000;
		    this.toGoalTime = 0;
			super.getPIDController().reset();


	  }

}
