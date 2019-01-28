/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickLiftCommand extends Command {
    public JoystickLiftCommand() {
        requires(Robot.liftSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        // TODO: Change input to a saner option

        Robot.liftSubsystem.setSpeed(Robot.oi.leftJoystick.getTwist());
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