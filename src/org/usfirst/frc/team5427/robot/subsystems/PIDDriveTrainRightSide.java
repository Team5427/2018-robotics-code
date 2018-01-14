/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.util.SameLine;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**@author Varsha
 * Subsystem to control speed of right side of DriveTrain using a PID Controller
 */
@SameLine
public class PIDDriveTrainRightSide extends PIDSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public AHRS ahrs;
	public static DriveTrain driveTrain;
	public SpeedControllerGroup spgRight;

	public PIDDriveTrainRightSide(double p, double i, double d,double setpoint, SpeedControllerGroup motorGroup, AHRS ahrs) {
		super(p, i, d);
		this.setSetpoint(setpoint);
		this.setInputRange(-180.0f,  180.0);
		this.setOutputRange(-1.0, 1.0);
		this.setAbsoluteTolerance(1.0f);
		spgRight = motorGroup;
		getPIDController().setContinuous(true);	
		this.ahrs = ahrs;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		driveTrain = Robot.driveTrain;
		
	}
	
	/**Source: WPILib
	   * Returns the input for the pid loop.
	   * <p>It returns the input for the pid loop, so if this command was based off of a gyro, then it
	   * should return the angle of the gyro.
	   * <p>All subclasses of {@link PIDCommand} must override this method.
	   * <p>This method will be called in a different thread then the {@link Scheduler} thread.
	   *
	   * @return the value the pid loop should use as input
	   */
	@Override
	public double returnPIDInput() {
		return ahrs.pidGet();
	}

	/**Source: WPILib
	   * Uses the value that the pid loop calculated. The calculated value is the "output" parameter.
	   * This method is a good time to set motor values, maybe something along the lines of
	   * <code>driveline.tankDrive(output, -output)</code>
	   * <p>All subclasses of {@link PIDCommand} must override this method.
	   * <p>This method will be called in a different thread then the {@link Scheduler} thread.
	   * @param output the value the pid loop calculated
	   */
	@Override
	protected void usePIDOutput(double output) {
		spgRight.pidWrite(output);
	}
}
