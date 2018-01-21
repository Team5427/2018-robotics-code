package org.usfirst.frc.team5427.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * This class defines a {@link Command} which interacts heavily with a PID loop.
 *
 * <p> It provides some convenience methods to run an internal {@link PIDController} . It will also
 * start and stop said {@link PIDController} when the {@link PIDCommand} is first initialized and
 * ended/interrupted. </p>
 */
public class PIDDriveTrainSide extends PIDCommand{
	
		  /**
	   * The internal {@link PIDController}.
	   */
	  private final PIDController m_controller;
	  /**
	   * An output which calls {@link PIDCommand#usePIDOutput(double)}.
	   */
	  private final PIDOutput m_output = this::usePIDOutput;
	  /**
	   * A source which calls {@link PIDCommand#returnPIDInput()}.
	   */
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
	  private SpeedControllerGroup scgContant;
	 
	  /**
	   * Instantiates a {@link PIDCommand} that will use the given p, i and d values. It will also space
	   * the time between PID loop calculations to be equal to the given period.
	   *
	   * @param name   the name
	   * @param p      the proportional value
	   * @param i      the integral value
	   * @param d      the derivative value
	   * @param period the time (in seconds) between calculations
	   */
	  @SuppressWarnings("ParameterName")
	  public PIDDriveTrainSide(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgContant, double p, double i, double d, double period) {
	    super(p,i,d,period);
	    m_controller = new PIDController(p, i, d, m_source, m_output, period);
	    this.scgPIDControlled=scgPIDControlled;
	    this.scgContant = scgContant;
	  }

	 
	  /**
	   * Returns the {@link PIDController} used by this {@link PIDCommand}. Use this if you would like
	   * to fine tune the pid loop.
	   *
	   * @return the {@link PIDController} used by this {@link PIDCommand}
	   */
	  protected PIDController getPIDController() {
	    return m_controller;
	  }

	  public void initialize() {
	    m_controller.enable();
	  }
	  
	  public void end() {
	    m_controller.disable();
	    scgPIDControlled.set(0);	    
	  }

	  public void interrupted() {
	    end();
	  }

	  
	  

	  /**
	   * Sets the maximum and minimum values expected from the input and setpoint.
	   *
	   * @param minimumInput the minimum value expected from the input and setpoint
	   * @param maximumInput the maximum value expected from the input and setpoint
	   */
	  protected void setInputRange(double minimumInput, double maximumInput) {
	    m_controller.setInputRange(minimumInput, maximumInput);
	  }

	  /**
	   * This is where you put the AHRS Angle 
	   * Returns the input for the pid loop.
	   *
	   * <p>It returns the input for the pid loop, so if this command was based off of a gyro, then it
	   * should return the angle of the gyro.
	   *
	   * <p>All subclasses of {@link PIDCommand} must override this method.
	   *
	   * <p>This method will be called in a different thread then the {@link Scheduler} thread.
	   *
	   * @return the value the pid loop should use as input
	   */
	  protected double returnPIDInput()
	  {
		  //TODO implement this
		  return Robot.ahrs.getYaw();//TODO make this 
	  }

	  /**
	   * Uses the value that the pid loop calculated. The calculated value is the "output" parameter.
	   * This method is a good time to set motor values, maybe something along the lines of
	   * <code>driveline.tankDrive(output, -output)</code>
	   *
	   * <p>All subclasses of {@link PIDCommand} must override this method.
	   *
	   * <p>This method will be called in a different thread then the {@link Scheduler} thread.
	   *
	   * @param output the value the pid loop calculated
	   */
	  @Override
	  protected void usePIDOutput(double output) {
		  scgPIDControlled.set(output);
		  scgContant.set(.5);
	  }


	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
