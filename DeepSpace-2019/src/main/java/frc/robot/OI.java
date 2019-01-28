/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  public Joystick leftJoystick;
  public Joystick rightJoystick;

  public JoystickButton startZLift;
  public JoystickButton startWings;

  public OI() {
    leftJoystick = new Joystick(PortMap.LEFT_JOYSTICK);
    rightJoystick = new Joystick(PortMap.RIGHT_JOYSTICK);

    // Temporary arguments
    startZLift = new JoystickButton(leftJoystick, PortMap.LEFT_JOYSTICK_BUTTON_ONE);
    startWings = new JoystickButton(leftJoystick, PortMap.RIGHT_JOYSTICK_BUTTON_TWO);
  }
}
