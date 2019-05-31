/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot;

import org.usfirst.frc.team5427.robot.commands.IntakeActivateIn;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateOut;
import org.usfirst.frc.team5427.robot.commands.TiltIntakeDown;
import org.usfirst.frc.team5427.robot.commands.TiltIntakeUp;
import org.usfirst.frc.team5427.robot.commands.TiltIntake_TimeOut;
import org.usfirst.frc.team5427.util.Config;
import org.usfirst.frc.team5427.util.SameLine;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	Button motorIntakeIn;

	/**
	 * Button used to shoot cubes out of the robot.
	 */
	Button motorIntakeOut;

	/**
	 * Button used to move the elevator upwards.
	 */
	Button elevatorUp;

	/**
	 * Button used to move the elevator downwards.
	 */
	Button elevatorDown;

	/**
	 * Button used to move the climber arm upwards.
	 */
	Button climberArmUp;

	/**
	 * Button used to move the climber arm downwards.
	 */
	Button climberArmDown;

	/**
	 * Button used to switch the tilted position of the intake.
	 */
	Button intakeTilterToggle;

	/**
	 * Button used to manually tilt the intake upwards.
	 */
	Button intakeTilterUp;

	/**
	 * Button used to manually tilt the intake downwards.
	 */
	Button intakeTilterDown;

	/**
	 * Button used to climb upwards.
	 */
	Button climberUp;

	/**
	 * Button used to climb downwards.
	 */
	Button climberDown;

	/**
	 * Button used to shoot cubes out of the robot slowly.
	 */
	Button softOutGo;
	/*************************************************/

	/**
	 * Chooser used to select the robot's autonomous position.
	 */
	SendableChooser<Integer> autoPositionChooser = new SendableChooser<Integer>();

	/**
	 * Chooser used to select the robot's desired cube placement.
	 */
	SendableChooser<Integer> autoCubeChooser = new SendableChooser<Integer>();

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

		motorIntakeIn.whenPressed(new IntakeActivateIn());
		motorIntakeOut.whenPressed(new IntakeActivateOut());
		elevatorUp.whenPressed(Robot.mou);
		elevatorDown.whenPressed(Robot.mod);
		intakeTilterToggle.whenPressed(new TiltIntake_TimeOut());
		intakeTilterUp.whenPressed(new TiltIntakeUp());
		intakeTilterDown.whenPressed(new TiltIntakeDown());

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
