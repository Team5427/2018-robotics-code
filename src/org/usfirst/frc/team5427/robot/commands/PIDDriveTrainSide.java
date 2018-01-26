package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.Log;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
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
	

	  //PIDController to make PID calculations
	  private final PIDController m_controller;
	  
	  private final PIDOutput m_output = this::usePIDOutput;
	  private final PIDSource m_source = new PIDSource() {
	    public void setPIDSourceType(PIDSourceType pidSource) {
	    }

	    public PIDSourceType getPIDSourceType() {
	      return PIDSourceType.kDisplacement;
	    }

	    public double pidGet() {
	      return returnPIDInput();
	    }
	  };

	  private SpeedControllerGroup scgPIDControlled;
	  private SpeedControllerGroup scgConstant;
	  private double power;
	  private double increment;
	  
	  private double p;
	 
	 
//	  @SuppressWarnings("ParameterName")
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
		  this.p=p;
	    Log.init("PIDDriveTrainRight created");
	    m_controller = new PIDController(p, i, d, m_source, m_output);
	    super.setSetpoint(setpoint);
	    this.scgPIDControlled=scgPIDControlled;
	    this.scgConstant = scgConstant;
	    this.power = 0;
	    this.scgPIDControlled.set(-this.power);
	    this.scgConstant.set(this.power);
	    this.increment=.075;//TODO move to Config
	
	   
	    super.setSetpoint(setpoint);
	    initialize();
	    
	  }

	  protected PIDController getPIDController() {
	    return m_controller;
	  }

	  //begins the PID loop (enables)
	  public void initialize() {
		  Log.init("Initializing");
	    m_controller.enable();
	  }
	  
	  //Ends (disables) the PID loop and stops the motors of the SpeedControllerGroups
	  public void end() {
		  Log.init("Ending PID");
	    m_controller.disable();
	    scgPIDControlled.set(0);	
	    scgConstant.set(0);
	  }

	  //Code to run when this command is interrupted
	  public void interrupted() {
	    end();
	  }

	  protected void setInputRange(double minimumInput, double maximumInput) {
	    m_controller.setInputRange(minimumInput, maximumInput);
	  }
	  public void setPower(double power) {
		  this.power = power;
	  }
	  
	  //TODO uncomment and utilize this method
	  public void incrementPower() {
//		  if(power<Config.PID_STRAIGHT_POWER)
//			  this.power+=Config.PID_STRAIGHT_INCREMENT;
	  }

	  protected double returnPIDInput()
	  {
		  //TODO implement this
		  return Robot.ahrs.getYaw();//TODO make this 
	  }

	  /**
	   * method to set motor values using the parameter "output"
	   * This method is automatically called by PIDCommand
	   */
	  @Override
	  protected void usePIDOutput(double output) {
		  SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		
		  scgPIDControlled.set(output);
		  //.05(initial) greater than .01
//		  if(increment>.01) {
//			  increment-=Config.PID_STRAIGHT_INCREMENT_DECREMENT;
//		  }
		  if(Config.PID_STRAIGHT_P==p&&this.power<Config.PID_STRAIGHT_POWER) {
			  Log.info("INCREMENTING");
			  this.power+=increment;
		  }
		  if(Config.PID_TURN_P==p)
			  scgConstant.set(power);
		  
		
		  SmartDashboard.putNumber("RightSpeed", output);
	  }


	@Override
	protected boolean isFinished() {
		return false;
	}

}
