package org.usfirst.frc.team5427.robot.OurClasses;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Ultrasonic;


public class SteelUltrasonic extends Ultrasonic {
	
	DigitalOutput digitalOutput;
	DigitalInput digitalInput;
	long millisPerCall;
	double pingTime=10 * 1e-6;
	
	
	private long lastCallTime = System.currentTimeMillis() - millisPerCall;
	
	private double lastDistanceInches = 0;
	
	public SteelUltrasonic(DigitalOutput digitalOutput, DigitalInput digitalInput, int millisPerCall)
	{
		super(digitalOutput,digitalInput);
		this.digitalOutput=digitalOutput;
		this.digitalInput=digitalInput;

		this.millisPerCall=millisPerCall;
	}
	
	public SteelUltrasonic(int digitalOutput, int digitalInput, int millisPerCall)
	{
		super(digitalOutput,digitalInput);
		this.millisPerCall=millisPerCall;
		this.digitalOutput=new DigitalOutput(digitalOutput);
		this.digitalInput=new DigitalInput(digitalInput);

	}
	
	//periodically pings
	
	

	public void pingPeriodically()
	{
		digitalOutput.pulse(pingTime);
		try
		{
			Thread.sleep(millisPerCall);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
	}
	
	@Override
	public double getRangeInches() {
		
		if (System.currentTimeMillis() - lastCallTime > millisPerCall) {
			ping();
			lastDistanceInches = super.getRangeInches();
			lastCallTime = System.currentTimeMillis();
		}
		
		return lastDistanceInches;
	}

}
