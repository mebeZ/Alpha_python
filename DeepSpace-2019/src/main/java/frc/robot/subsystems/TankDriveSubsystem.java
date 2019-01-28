/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.PID;
import frc.robot.Robot;

public class TankDriveSubsystem extends Subsystem {
  private PID tankAnglePID;
  
  public TankDriveSubsystem() {

    System.out.println("Init tank subsystem");
  }

  public void setLeftSpeed(double speed) {
    Robot.lf.set(speed);
    Robot.lm.set(speed);
    Robot.lb.set(speed);
  }

  public void setRightSpeed(double speed) {
    Robot.rf.set(speed);
    Robot.rm.set(speed);
    Robot.rb.set(speed);
  }

  public void resetTurnPID() {resetTurnPID(.8, 2, 0.3);}
  public void resetTurnPID(double p, double i, double d) {tankAnglePID = new PID(p,i,d);}

  @Override
  public void initDefaultCommand() {
  }
}
