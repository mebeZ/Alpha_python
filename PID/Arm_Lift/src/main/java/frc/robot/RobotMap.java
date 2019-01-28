/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static TalonSRX LIFTMOTOR = 0;
  public static Joystick CAMERA_JOYSTICK = 0;
  public static Gyro LIFTGYRO = 0;
  public static Solenoid MODULE_1 = 0;
  public static Solenoid MODULE_2 = 1;
  public static JoystickButton FORWARD_BUTTON = 0;
  public static JoystickButton BACKWARD_BUTTON = 1;
  public static JoystickButton OFF_BUTTON = 2;
  public static Servo S1 = 0;
  public static Servo S2 = 1;
}
