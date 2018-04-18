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
//import org.usfirst.frc.team5427.robot.commands.MoveClimberArmDown;
//import org.usfirst.frc.team5427.robot.commands.MoveClimberArmUp;
//import org.usfirst.frc.team5427.robot.commands.MoveClimberDown;
//import org.usfirst.frc.team5427.robot.commands.MoveClimberUp;
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
 * This file creates the joy stick and assigns functions to its buttons
 * This file will use the same line method
 */
@SameLine
public class OI {

	public Joystick joy1;
	Button motorIntakeIn;
	Button motorIntakeOut;
	Button elevatorUp;
	Button elevatorDown;
	Button climberArmUp;
	Button climberArmDown;
	Button intakeTilterToggle;
	Button intakeTilterUp;
	Button intakeTilterDown;
	Button climberUp;
	Button climberDown;
 
	Button softOutGo;


	SendableChooser<Integer> autoColorChooser = new SendableChooser<Integer>();
	SendableChooser<Integer> autoPositionChooser = new SendableChooser<Integer>();
	SendableChooser<Integer> autoCubeChooser = new SendableChooser<Integer>();
	
	public OI() {
		joy1 = new Joystick(Config.JOYSTICK_PORT);
        
        /** 
         * Create the buttons
         */
        motorIntakeIn = new JoystickButton(joy1,Config.BUTTON_MOTOR_INTAKE_IN);
        motorIntakeOut = new JoystickButton(joy1, Config.BUTTON_MOTOR_INTAKE_OUT);
        elevatorUp = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_UP);
        elevatorDown = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_DOWN);
        softOutGo =  new JoystickButton(joy1,Config.BUTTON_MOTOR_INTAKE_OUT_SLOW);
        climberArmDown = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_DOWN_MANUAL);
        intakeTilterToggle = new JoystickButton(joy1,Config.BUTTON_INTAKE_TOGGLE_TILTER);
        intakeTilterUp = new JoystickButton(joy1,Config.BUTTON_INTAKE_TILTER_UP);
        intakeTilterDown = new JoystickButton(joy1,Config.BUTTON_INTAKE_TILTER_DOWN);

        
        /**
         * Sets the functions of the buttons
         */
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
		
		SmartDashboard.putData("Field Position",autoPositionChooser);
		SmartDashboard.putData("Cube Placement",autoCubeChooser);
		SmartDashboard.putString("Position Selector", "Position Selector");
		SmartDashboard.putString("Cube Placement Selector", "Cube Placement Selector");
		SmartDashboard.putString("Intake Camera", "Intake Camera");
    }

	
	/**
	 * Gets the name of the joy stick being used
	 * @return this joy stick's name
	 */
    public Joystick getJoy() {
        return joy1;
    } 
	    
}
