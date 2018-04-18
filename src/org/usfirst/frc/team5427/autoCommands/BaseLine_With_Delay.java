package org.usfirst.frc.team5427.autoCommands;

import org.usfirst.frc.team5427.robot.Robot;
import org.usfirst.frc.team5427.robot.commands.Fidget;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
import org.usfirst.frc.team5427.robot.commands.PIDStraightMovement;
import org.usfirst.frc.team5427.robot.commands.PIDTurn;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This class was mainly made because Team 118 wanted
 * us to add a delay to our auto to stay out of their way.
 * It did not work.
 * @author Blake
 */
@SameLine
public class BaseLine_With_Delay extends AutoPath {
	
	/**
	 * The first distance of this autonomous path.
	 */
	private PIDStraightMovement firstDistance;
	
	/**
	 * The command used to move the elevator up during the
	 * autonomous path.
	 */
	private MoveElevatorAuto moveElevator;
	
	/**
	 * The command used to cause the arms of the intake to fall
	 * down at the start of autonomous.
	 */
	private Fidget fidget;
	
	/**
	 * The time taken at the start of the autonomous path.
	 */
	private double startTime;
	
	/**
	 * The time taken periodically throughout the command
	 * to determine the amount of time passed.
	 */
	private double currentTime;
	
	/**
	 * P value for 110 inches.
	 */
	public static final double p1 = 0.025;
	
	/**
	 * I value for 110 inches.
	 */
	public static final double i1 = 0.0;
	
	/**
	 * D value for 110 inches.
	 */
	public static final double d1 = 0.12;
	
	
	/**
	 * Stores whether the elevator is done moving upward.
	 */
	public boolean elevIsDone;

	/**
	 * Constructor used to create the fidget, firstDistance, and moveElevator.
	 * It sets the timeOut to 13.5 seconds.
	 */
	public BaseLine_With_Delay() {
		fidget = new Fidget();
		firstDistance = new PIDStraightMovement(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left, Config.PID_STRAIGHT_POWER_SHORT, 110, p1, i1, d1); //106
		moveElevator = new MoveElevatorAuto(1);
		
		elevIsDone = false;
		setTimeout(13.5);
	}

	/**
	 * Method called once the command is started.
	 * We use it to wait 10 seconds then start the first step of autonomous.
	 */
	public void initialize() {
		try {
			wait(10);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startTime = System.nanoTime()/1000000000.;
		fidget.start();
		elevIsDone= false;
	}

	// uses the previous commands being null to check if a certain command needs to
	// be started or not
	public void execute() {
		currentTime = System.nanoTime()/1000000000.;

		if(moveElevator != null)
			moveElevator.isFinished();
		//starts elevator raising when we are 0.5 sec. into auto
		if(currentTime-startTime>0.8&&!moveElevator.isRunning()&&!moveElevator.isFinished()) {
			moveElevator.start();
		}

		
		
//		if (null != firstDistance && firstDistance.isFinished() && !(secondDistance.isRunning())) {
//			System.out.println("Part 1 Done.");
//			firstDistance.cancel();
//			firstDistance = null;
//			Robot.ahrs.reset();
//			Robot.encLeft.reset();
////			Robot.encRight.reset();
//			secondDistance.start();
//			moveElevator.start();
//		}
		else if (null != fidget && fidget.isFinished() && !(firstDistance.isRunning())&&currentTime-startTime>10) {
			System.out.println("Fidget Done.");
			fidget.cancel();
			fidget = null;
			Robot.encLeft.reset();
//			Robot.encRight.reset();
			firstDistance.start();
		}
	}

	@Override
	public boolean isFinished() {
		// returns if the last distance has finished and the robot has shot the box
		if (firstDistance.isFinished())
			return true;
		return isTimedOut();
	}

	@Override
	protected void end() {
//		super.end();
	}
}
