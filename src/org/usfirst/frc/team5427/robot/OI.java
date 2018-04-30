/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot;

import org.usfirst.frc.team5427.robot.commands.IntakeActivateIn;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateOut;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateOutSlow;
import org.usfirst.frc.team5427.robot.commands.ManualMoveElevatorDown;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorFull;
import org.usfirst.frc.team5427.robot.commands.TiltIntakeDown;
import org.usfirst.frc.team5427.robot.commands.TiltIntakeUp;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 * This file creates the joy stick and assigns functions to its buttons.
 * 
 * @author Varsha Kumar
 */
@SameLine
public class OI {

	/**
	 * This is the joystick that we use to control the robot's movements and
	 * actions.
	 */
	public Joystick joy1;

	/******************** BUTTONS ********************/
	/**
	 * Button used to pull cubes into the robot.
	 */
	public Button motorIntakeIn;

	/**
	 * Button used to shoot cubes out of the robot.
	 */
	public Button motorIntakeOut;

	/**
	 * Button used to move the elevator upwards.
	 */
	public Button elevatorUp;

	/**
	 * Button used to move the elevator downwards.
	 */
	public Button elevatorDown;

	/**
	 * Button used to move the climber arm upwards.
	 */
	public Button climberArmUp;

	/**
	 * Button used to move the climber arm downwards.
	 */
	public Button climberArmDown;

	/**
	 * Button used to switch the tilted position of the intake.
	 */
	public Button intakeTilterToggle;

	/**
	 * Button used to manually tilt the intake upwards.
	 */
	public Button intakeTilterUp;

	/**
	 * Button used to manually tilt the intake downwards.
	 */
	public Button intakeTilterDown;

	/**
	 * Button used to climb upwards.
	 */
	public Button climberUp;

	/**
	 * Button used to climb downwards.
	 */
	public Button climberDown;

	/**
	 * Button used to shoot cubes out of the robot slowly.
	 */
	public Button softOutGo;
	/*************************************************/

	/**
	 * Chooser used to select the robot's autonomous position.
	 */
	public SendableChooser<Integer> autoPositionChooser = new SendableChooser<Integer>();

	/**
	 * Chooser used to select the robot's desired cube placement.
	 */
	public SendableChooser<Integer> autoCubeChooser = new SendableChooser<Integer>();

	/**
	 * Constructor of OI used to initalize all of the buttons to their joystick
	 * locations and assign each button to a specific command, as well as create the
	 * autonomous choosers on the SmartDashboard.
	 */
	public OI() {
		joy1 = new Joystick(Config.JOYSTICK_PORT);

		motorIntakeIn = new JoystickButton(joy1, Config.BUTTON_MOTOR_INTAKE_IN);
		motorIntakeOut = new JoystickButton(joy1, Config.BUTTON_MOTOR_INTAKE_OUT);
		elevatorUp = new JoystickButton(joy1, Config.BUTTON_ELEVATOR_UP);
		elevatorDown = new JoystickButton(joy1, Config.BUTTON_ELEVATOR_DOWN);
		softOutGo = new JoystickButton(joy1, Config.BUTTON_MOTOR_INTAKE_OUT_SLOW);
		climberArmDown = new JoystickButton(joy1, Config.BUTTON_ELEVATOR_DOWN_MANUAL);
		intakeTilterToggle = new JoystickButton(joy1, Config.BUTTON_INTAKE_TOGGLE_TILTER);
		intakeTilterUp = new JoystickButton(joy1, Config.BUTTON_INTAKE_TILTER_UP);
		intakeTilterDown = new JoystickButton(joy1, Config.BUTTON_INTAKE_TILTER_DOWN);

		motorIntakeIn.whileHeld(new IntakeActivateIn());
		motorIntakeOut.whileHeld(new IntakeActivateOut());
		elevatorUp.whileHeld(Robot.mou);
		elevatorDown.whileHeld(Robot.mod);
		elevatorUp.toggleWhenPressed(Robot.mou);
		elevatorDown.toggleWhenPressed(Robot.mod);
		intakeTilterToggle.whenPressed(new TiltIntake_TimeOut());
		intakeTilterUp.whenPressed(new TiltIntakeUp());
		intakeTilterDown.whenPressed(new TiltIntakeDown());
		climberArmDown.whileHeld(new ManualMoveElevatorDown());

		autoPositionChooser.addDefault("CHOOSE ONE", Config.AUTO_NONE);
		autoPositionChooser.addObject("Right", Config.RIGHT);
		autoPositionChooser.addObject("Center", Config.CENTER);
		autoPositionChooser.addObject("Left", Config.LEFT);
		autoCubeChooser.addDefault("CHOOSE ONE", Config.AUTO_NONE);
		autoCubeChooser.addObject("Switch", Config.SWITCH);
		autoCubeChooser.addObject("Scale", Config.SCALE);
		SmartDashboard.putData("Field Position", autoPositionChooser);
		SmartDashboard.putData("Cube Placement", autoCubeChooser);
	}

	/**
	 * Accessor for the joystick we use.
	 * 
	 * @return the current joystick.
	 */
	public Joystick getJoy() {
		return joy1;
	}

}
