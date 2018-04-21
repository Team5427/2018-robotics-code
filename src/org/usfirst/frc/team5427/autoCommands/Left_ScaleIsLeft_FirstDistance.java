package org.usfirst.frc.team5427.autoCommands;

import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.PIDDistance;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The first distance to travel in the Left_ScaleIsLeft auto command
 * 
 * @author Andrew Li
 */
public class Left_ScaleIsLeft_FirstDistance extends PIDCommand {

	// This command is created once we have reached the maximum power to control the
	// distance that we travel.
	private PIDDistance pidDistance;
	// This SpeedControllerGroup is the side of the robot that this command
	// controls.
	private boolean hasStarted;
	private SpeedControllerGroup scgPIDControlled;
	// This SpeedControllerGroup is the side of the robot that is controlled by a
	// constant value and PIDDistance.
	private SpeedControllerGroup scgConstant;
	// This is the maximum speed that the robot will travel at.
	private double maximumSpeed;
	// This is the distance that we want to travel.
	private double desiredDistance;
	// This is the power that scgPIDControlled is set to.
	private double power;
	// These are the p, i, and d values for the PID Controller in PIDDistance.
	private double p, i, d;
	private double startTime;
	private double endTime;
	private double startDistance;
	private double endDistance;
	
	
	/**
	 * Constructor for PIDStraightMovement
	 * 
	 * @param scgPIDControlled
	 *            - This receives the side of the robot that we are controlling with
	 *            this PIDCommand.
	 * @param scgConstant
	 *            - This receives the side of the robot that we will control with
	 *            the PIDDistance command.
	 * @param maximumSpeed
	 *            - This receives the maximum speed that the robot will travel at.
	 * @param desiredDistance
	 *            - This receives the distance that we want to travel.
	 * @param p, i, d 
	 *            - These receive the P, I, and D values for the PID Controller
	 *              in PIDDistance.
	 */
	public Left_ScaleIsLeft_FirstDistance(SpeedControllerGroup scgPIDControlled, SpeedControllerGroup scgConstant, double maximumSpeed, double desiredDistance, double p, double i, double d) {
		super(Config.PID_STRAIGHT_P, Config.PID_STRAIGHT_I, Config.PID_STRAIGHT_D, Config.PID_UPDATE_PERIOD);
		this.scgPIDControlled = scgPIDControlled;
		this.scgConstant = scgConstant;
		this.maximumSpeed = maximumSpeed;
		this.desiredDistance = desiredDistance;
		this.p = p;
		this.i = i;
		this.d = d;
//		this.getPIDController().setOutputRange(-maximumSpeed, maximumSpeed);
//		this.getPIDController().setAbsoluteTolerance(.1);
		this.setInterruptible(true);
		this.getPIDController().setSetpoint(0);
//		super.setSetpoint(0);
		setSetpoint(0);
		this.power = .01;
		hasStarted = false;
		// scgConstant.set(0);
		// scgPIDControlled.set(0);
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
