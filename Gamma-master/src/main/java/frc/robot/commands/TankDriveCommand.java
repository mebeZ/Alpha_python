/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class TankDriveCommand extends Command {
    public TankDriveCommand() {
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double goalSpeedRight = Robot.oi.rightStick.getY();
        double goalSpeedLeft = Robot.oi.leftStick.getY();

        Robot.driveSubsystem.pidRight.add_measurement(goalSpeedRight - Robot.driveSubsystem.getRightSpeed());
        Robot.driveSubsystem.pidLeft.add_measurement(goalSpeedLeft - Robot.driveSubsystem.getLeftSpeed());
        
        Robot.driveSubsystem.setRightSpeedTank(Robot.autoSubsystem.normalize(Robot.driveSubsystem.pidRight.getOutput()));
        Robot.driveSubsystem.setLeftSpeedTank(Robot.autoSubsystem.normalize(Robot.driveSubsystem.pidLeft.getOutput()));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
