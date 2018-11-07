package org.usfirst.frc.team5427.robot.subsystems;

import org.usfirst.frc.team5427.robot.Robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class UltrasonicPID extends PIDSubsystem
{
	public Ultrasonic ultra;
	
	public SpeedControllerGroup scgControlled;
	
	public UltrasonicPID(int ping, int echo)
	{
		super(0.1, 0, 0.01); //random PID values
		ultra = new Ultrasonic(ping, echo);
		ultra.setAutomaticMode(true);
		
		scgControlled = Robot.driveTrain.drive_Right;
		
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
		scgControlled.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand()
	{
	}
}
