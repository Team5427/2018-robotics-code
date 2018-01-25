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


public class PIDDriveTrainSide extends PIDCommand{
	

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
	 
	 
	  @SuppressWarnings("ParameterName")
	  public PIDDriveTrainSide(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double p, double i, double d, double setpoint) {
	    super(p,i,d);
	    Log.init("PIDDriveTrainRight created");
	    m_controller = new PIDController(p, i, d, m_source, m_output);
	    this.scgPIDControlled=scgPIDControlled;
	    this.scgConstant = scgConstant;
	    this.power = 0;
	    this.scgPIDControlled.set(-this.power);
	    this.scgConstant.set(this.power);
	
	   
	    super.setSetpoint(setpoint);
	    initialize();
	    
	  }

	  protected PIDController getPIDController() {
	    return m_controller;
	  }

	  public void initialize() {
		  Log.init("Initializing");
	    m_controller.enable();
	  }
	  
	  public void end() {
		  Log.init("Ending PID");
	    m_controller.disable();
	    scgPIDControlled.set(0);	
	    scgConstant.set(0);
	  }

	  public void interrupted() {
	    end();
	  }

	  protected void setInputRange(double minimumInput, double maximumInput) {
	    m_controller.setInputRange(minimumInput, maximumInput);
	  }
	  public void setPower(double power) {
		  this.power = power;
	  }
	  public void incrementPower() {
		  if(power<Config.PID_STRAIGHT_POWER)
			  this.power+=Config.PID_STRAIGHT_INCREMENT;
	  }

	  protected double returnPIDInput()
	  {
		  //TODO implement this
		  return Robot.ahrs.getYaw();//TODO make this 
	  }

	  @Override
	  protected void usePIDOutput(double output) {
		  SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
		
		  scgPIDControlled.set(output);
		  scgConstant.set(power);
		  
		  SmartDashboard.putNumber("RightSpeed", output);
	  }


	@Override
	protected boolean isFinished() {
		return false;
	}

}
