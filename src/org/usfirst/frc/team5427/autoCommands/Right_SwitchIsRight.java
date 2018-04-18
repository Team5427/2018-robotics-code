package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;


/**
 * 
 * Autonomous function for when the robot is on the right and the switch is also on the right
 * 
 * @author Robotics
 */

public class Right_SwitchIsRight extends AutoPath {
	
	/**
	 * Takes the robot through its first and second distances traveled
	 */
	private PIDStraightMovement firstDistance, secondDistance;
	
	/**
	 * Turns the robot in its first angle
	 */
	private PIDTurn firstAngle;
	
	/**
	 * Moves the elevator
	 */
	private MoveElevatorAuto moveElevator;
	
	/**
	 * Fidgets the robot
	 */
	private Fidget fidget;
	
	/**
	 * the start and current time of the auto path in seconds
	 */
	private double startTime, currentTime;
	

	/**
	 * Time outs
	 */
	public static final double timeOut1 = 15;
	public static final double timeOut2 = 15;

	/**
	 * Values for 154 inches
	 */
	public static final double p1 = 0.0105; //0.0188
	public static final double i1 = 0.0;
	public static final double d1 = 0.008;
	
	/**
	 * Values for 16 inches
	 */
	public static final double p2 = 0.016;
	public static final double i2 = 0.0;
	public static final double d2 = 0.006;
	
	
	/**
	 * Creates all of the PID commands
	 */
	public Right_SwitchIsRight() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 154, p1, i1, d1);
		firstAngle = new PIDTurn(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, -90);
		secondDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 28, p2, i2, d2);
		moveElevator = new MoveElevatorAuto(1); // 1 for switch
	}

	/**
	 * Begins the command
	 */
	public void initialize() {
		startTime = System.nanoTime()/1000000000.;
		fidget.start();		
	}

	
	/**
	 * uses the previous commands being null to check if a certain command needs to
	 * be started or not
	 */
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		
		if (null == fidget && null == firstDistance && null != firstAngle && firstAngle.isFinished() && !(secondDistance.isRunning())) {
			System.out.println("Part 2 Done.");
			firstAngle.cancel();
			firstAngle = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			moveElevator.start();
			secondDistance.start();
		}
		
		else if (null == fidget && null != firstDistance && firstDistance.isFinished() && !(firstAngle.isRunning()) || currentTime - startTime > timeOut1) {
			System.out.println("Part 1 Done.");
			firstDistance.cancel();
			firstDistance = null;
			Robot.ahrs.reset();
			Robot.encLeft.reset();
			firstAngle.start();
		}
		
		else if(null != fidget && fidget.isFinished() && !(firstDistance.isRunning())) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.encLeft.reset();
			firstDistance.start();
		}
	}

	/**
	 * @return whether or not the last distance has finished and the robot has launched the box
	 */
	@Override
	public boolean isFinished() {
		if (firstAngle == null && secondDistance.isFinished() || currentTime - startTime > timeOut2)
			return true;
		return false;
		
	}
	
	/**
	 * Ends autonomous and switches to teleop
	 */
	@Override
	protected void end() {
		super.end();
	}
}
