/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickArmCommand extends Command {
    private double m_speed;

    public JoystickArmCommand(double speed) {
        requires(Robot.armSubsystem);

        m_speed = speed;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.armSubsystem.pid.add_measurement(m_speed - Robot.armSubsystem.m_motor.get());
        Robot.armSubsystem.setSpeed(Robot.autoSubsystem.normalize(Robot.armSubsystem.pid.getOutput()));
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