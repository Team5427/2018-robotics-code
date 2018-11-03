package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class UltrasonicPID extends PIDSubsystem
{
	public Ultrasonic ultra;
	
	public SpeedControllerGroup scgLeft;
	public SpeedControllerGroup scgRight;
	
	
	public UltrasonicPID(int ping, int echo)
	{
		super(0.1, 0, 0.01);
		ultra = new Ultrasonic(ping, echo);
		ultra.setAutomaticMode(true);
		
		scgLeft = Robot.driveTrain.drive_Right;
		scgRight = Robot.driveTrain.drive_Left;
		
		
		this.setOutputRange(-1, 1);
	}

	@Override
	protected double returnPIDInput()
	{
		return ultra.getRangeInches();
	}

	@Override
	protected void usePIDOutput(double output)
	{
		scgRight.pidWrite(-output);
		scgLeft.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand()
	{
	}
}
