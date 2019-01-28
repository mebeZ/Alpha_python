/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankDriveCommand extends Command {
  public TankDriveCommand() {
    requires(Robot.tankDriveSubsystem);
  }

  @Override
  protected void initialize() {
    Robot.tankDriveSubsystem.setLeftSpeed(0);
    Robot.tankDriveSubsystem.setRightSpeed(0);

    System.out.println("Init tank command");
  }

  @Override
  protected void execute() {
    Robot.tankDriveSubsystem.setLeftSpeed(-Robot.oi.leftJoystick.getY());
    Robot.tankDriveSubsystem.setRightSpeed(Robot.oi.rightJoystick.getY());  
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.tankDriveSubsystem.setLeftSpeed(0);
    Robot.tankDriveSubsystem.setRightSpeed(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
