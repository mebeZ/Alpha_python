/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class AutoArmCommand extends Command {
    private double m_endPosRadians;
    private static final double THRESHOLD = 1;

    public AutoArmCommand(double endPosRadians) throws IllegalArgumentException {
        requires(Robot.armSubsystem);
        
        if (endPosRadians > Robot.armSubsystem.GREATEST_ARM_POS || endPosRadians < Robot.armSubsystem.LOWEST_ARM_POS)
            throw new IllegalArgumentException("The position was out of bounds");
       
        m_endPosRadians = endPosRadians;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.armSubsystem.pid.add_measurement(m_endPosRadians - Robot.armSubsystem.getPos());
        Robot.armSubsystem.setSpeed(Robot.armSubsystem.pid.getOutput());
    }

    @Override
    protected boolean isFinished() {
        if (m_endPosRadians - Robot.armSubsystem.getPos() > THRESHOLD) {
            return false;
        } else {
            return true;
        }        
    }

    @Override
    protected void end() {
        new JoystickArmCommand(Robot.oi.rightStick.getTwist()).start();
    }

    @Override
    protected void interrupted() {
        end();
    }
}