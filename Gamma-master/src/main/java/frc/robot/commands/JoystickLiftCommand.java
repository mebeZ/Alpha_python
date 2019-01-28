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
    private double m_speed;

    public JoystickLiftCommand(double speed) {
        requires(Robot.liftSubsystem);

        m_speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.liftSubsystem.newPID();
    }

    @Override
    protected void execute() {
        Robot.liftSubsystem.pid.add_measurement(m_speed - Robot.liftSubsystem.m_motor.get());
        Robot.liftSubsystem.setSpeed(Robot.autoSubsystem.normalize(Robot.liftSubsystem.pid.getOutput()));
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