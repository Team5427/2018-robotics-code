/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team5427.robot;

import org.usfirst.frc.team5427.robot.commands.IntakeActivateIn;
import org.usfirst.frc.team5427.robot.commands.IntakeActivateOut;
//import org.usfirst.frc.team5427.robot.commands.MoveClimberDown;
//import org.usfirst.frc.team5427.robot.commands.MoveClimberUp;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorDown;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorFull;
import org.usfirst.frc.team5427.robot.commands.MoveElevatorUp;
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
 */
/**
 * This file makes the joystick This file will use the same line method
 * 
 */
@SameLine
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.
	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:
	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());
	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());
	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public Joystick joy1;
	
	Button motorIntakeIn;
	Button motorIntakeOut;
	Button elevatorUp;
	Button elevatorDown;
	Button climberUp;
	Button climberDown;
	Button elevatorAutoFull;

	SendableChooser<Integer> autoColorChooser = new SendableChooser<Integer>();
	SendableChooser<Integer> autoPositionChooser = new SendableChooser<Integer>();
	SendableChooser<Integer> autoCubeChooser = new SendableChooser<Integer>();
	
	public OI() {
		joy1 = new Joystick(Config.JOYSTICK_PORT);
        
        //Create buttons
        motorIntakeIn = new JoystickButton(joy1,Config.BUTTON_MOTOR_INTAKE_IN);
        motorIntakeOut = new JoystickButton(joy1,Config.BUTTON_MOTOR_INTAKE_OUT);
        motorIntakeOut = new JoystickButton(joy1, Config.BUTTON_MOTOR_INTAKE_OUT_SLOW);
//        solenoidIntake = new JoystickButton(joy1,Config.BUTTON_SOLENOD_INTAKE);
        elevatorUp = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_UP);
        elevatorDown = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_DOWN);
        climberUp = new JoystickButton(joy1,Config.BUTTON_ELEVATOR_UP);
        climberDown = new JoystickButton(joy1,Config.BUTTON_CLIMBER_DOWN);

        
        //set what they do
        motorIntakeIn.whileHeld(new IntakeActivateIn());
        motorIntakeOut.whileHeld(new IntakeActivateOut());
//        
       // solenoidIntake.whenPressed(new IntakeSolenoidSwitch());
        
        elevatorUp.whileHeld(Robot.mou);
        elevatorDown.whileHeld(Robot.mod);
        elevatorAutoFull.whenPressed(new MoveElevatorFull());
//        
//        climberUp.whenPressed(new MoveClimberUp());
//        climberDown.whenPressed(new MoveClimberDown());
        
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

    public Joystick getJoy() {
        return joy1;
    }
	 
	 
	    
}
