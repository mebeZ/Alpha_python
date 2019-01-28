/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class AutoLiftCommand extends Command {
    private double m_endPos;
    private static final double THRESHOLD = 1;

    public AutoLiftCommand(double level) throws IllegalArgumentException {
        requires(Robot.liftSubsystem);
        
        if (level > Robot.GREATEST_LIFT_POS || level < Robot.LOWEST_LIFT_POS) {
            throw new IllegalArgumentException("The position was out of bounds");
        }
        m_endPos = level;
    }

    @Override
    protected void initialize() {
        Robot.liftSubsystem.newPID();
    }

    @Override
    protected void execute() {
        Robot.liftSubsystem.pid.add_measurement(m_endPos - Robot.liftSubsystem.getPos());
        Robot.liftSubsystem.setSpeed(Robot.liftSubsystem.pid.getOutput());
    }

    @Override
    protected boolean isFinished() {
        if (m_endPos - Robot.liftSubsystem.getPos() > THRESHOLD) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void end() {
        new JoystickLiftCommand(Robot.oi.leftStick.getTwist()).start();
    }

    @Override
    protected void interrupted() {
        end();
    }
}