/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.PortMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    public Joystick rightStick, leftStick;
    
    public JoystickButton lowestLiftLevel;
    public JoystickButton middleLeftLevel;
    public JoystickButton greatestLeftLevel;

    public OI() {
        rightStick = new Joystick(PortMap.RIGHT_STICK);
        leftStick = new Joystick(PortMap.LEFT_STICK);

        lowestLiftLevel = new JoystickButton(rightStick, PortMap.LOWEST_LIFT_LEVEL_BUTTON);
        middleLeftLevel = new JoystickButton(rightStick, PortMap.MIDDLE_LIFT_LEVEL_BUTTON);
        greatestLeftLevel = new JoystickButton(rightStick, PortMap.GREATEST_LIFT_LEVEL_BUTTON);
    }
}