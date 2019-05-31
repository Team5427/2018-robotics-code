// package org.usfirst.frc.team5427.autoCommands.center;

// import org.usfirst.frc.team5427.autoCommands.AutoPath;
// import org.usfirst.frc.team5427.robot.Robot;
// import org.usfirst.frc.team5427.robot.commands.AutoInGo;
// import org.usfirst.frc.team5427.robot.commands.AutoOutGo;
// import org.usfirst.frc.team5427.robot.commands.MoveElevatorAuto;
// import org.usfirst.frc.team5427.util.SameLine;

// /**
//  * Picks up the second cube and places it for Center_SwitchIsLeft.
//  * 
//  * @author Blake Romero, Andrew Li, Akshat Jain
//  */
// @SameLine
// public class Center_SwitchIsLeft_SecondCube extends AutoPath {
// 	/**
// 	 * Moves the robot backwards from the switch while moving the elevator down.
// 	 */
// 	public Center_SwitchIsLeft_Drive backOffFromSwitch;
	
// 	/**
// 	 * Moves the robot backwards from the cube.
// 	 */
// 	public Center_SwitchIsLeft_Drive backOffFromCube;
	
// 	/**
// 	 * Moves the robot forwards towards the switch while moving the elevator up.
// 	 */
// 	public Center_SwitchIsLeft_Drive moveForwardToSwitch;
	
// 	/**
// 	 * Moves the robot forwards towards the cube.
// 	 */
// 	public Center_SwitchIsLeft_Drive moveForwardToCube;
	
// 	/**
// 	 * Pulls the second cube into the robot using the intake.
// 	 */
// 	public AutoInGo intakeCube;
// 	/**
// 	 * Moves the elevator down to the lowest position.
// 	 */
// 	public MoveElevatorAuto elevatorDown;
// 	/**
// 	 * Moves the elevator up to the middle position.
// 	 */
// 	public MoveElevatorAuto elevatorUp;
// 	/**
// 	 * Curves the robot towards the cube it intends to pick up.
// 	 */
// 	public Center_SwitchIsLeft_FirstAngle angleToCube;
// 	/**
// 	 * Curves the robot away from the cube it intends to pick up.
// 	 */
// 	public Center_SwitchIsLeft_SecondAngle angleToSwitch;

// 	public Center_SwitchIsLeft_SecondCube() {
// 		backOffFromSwitch = new Center_SwitchIsLeft_Drive(1,0.33);
// 		backOffFromCube = new Center_SwitchIsLeft_Drive(1.2,0.25);
// 		elevatorDown = new MoveElevatorAuto(4);
// 		angleToCube = new Center_SwitchIsLeft_FirstAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		intakeCube = new AutoInGo();
// 		angleToSwitch = new Center_SwitchIsLeft_SecondAngle(Robot.driveTrain.drive_Right, Robot.driveTrain.drive_Left);
// 		moveForwardToSwitch = new Center_SwitchIsLeft_Drive(0.7,-0.33);
// 		moveForwardToCube = new Center_SwitchIsLeft_Drive(1.4,-0.25);
// 		elevatorUp = new MoveElevatorAuto(1);
// 	}

// 	@Override
// 	public void initialize() {
// 		backOffFromSwitch.start();
// 		elevatorDown.start();
// 	}

// 	@Override
// 	public void execute() {
// 		if(null == backOffFromSwitch && null == angleToCube && null== moveForwardToCube && null == backOffFromCube && null != angleToSwitch && angleToSwitch.isFinished()) {
// 			angleToSwitch.cancel();
// 			angleToSwitch = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			moveForwardToSwitch.start();
			
// 		}
// 		else if(null == backOffFromSwitch && null == angleToCube && null== moveForwardToCube && null!= backOffFromCube && backOffFromCube.isFinished()) {
// 			backOffFromCube.cancel();
// 			backOffFromCube = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			angleToSwitch.start();
// 			elevatorUp.start();
// 		}
// 		else if(null == backOffFromSwitch && null == angleToCube && null!= moveForwardToCube && moveForwardToCube.isFinished()) {
// 			moveForwardToCube.cancel();
// 			moveForwardToCube = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			backOffFromCube.start();
// 			intakeCube.cancel();
// 		}
// 		else if(null == backOffFromSwitch && null != angleToCube && angleToCube.isFinished()){
// 			angleToCube.cancel();
// 			angleToCube = null;
// 			intakeCube.start();
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			moveForwardToCube.start();
// 		}
// 		else if(null != backOffFromSwitch && backOffFromSwitch.isFinished()) {
// 			backOffFromSwitch.cancel();
// 			backOffFromSwitch = null;
// 			Robot.ahrs.reset();
// 			Robot.encLeft.reset();
// 			angleToCube.start();
// 		}
		
// 	}

// 	@Override
// 	public boolean isFinished() {
// 		return (null != moveForwardToSwitch && moveForwardToSwitch.isRunning() && moveForwardToSwitch.isFinished());
// 	}
	
// 	@Override
// 	public void end() {
// 		new AutoOutGo().start();
// 		super.end();
// 	}
// }
