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
    private static final double THRESHOLD = 0.5;
    private PID pid;

    public AutoLiftCommand(double level) throws IllegalArgumentException {
        requires(Robot.liftSubsystem);
        
        if (level > Robot.GREATEST_LIFT_POS || level < Robot.LOWEST_LIFT_POS) {
            throw new IllegalArgumentException("The position was out of bounds");
        }
        m_endPos = level;
    }

    @Override
    protected void initialize() {
        System.out.println("init");
        pid = new PID(Robot.liftSubsystem.LIFT_P_GAIN, 
                      Robot.liftSubsystem.LIFT_I_GAIN, 
                      Robot.liftSubsystem.LIFT_D_GAIN);
        System.out.println("inited");
    }

    @Override
    protected void execute() {
        double currentPos = Robot.liftSubsystem.getPos();

        pid.add_measurement(m_endPos - currentPos);
        Robot.liftSubsystem.setSpeed(pid.getOutput());

        System.out.println("[PID OUT]" + pid.getOutput());
    }

    @Override
    protected boolean isFinished() {
        if (m_endPos - Robot.liftSubsystem.getPos() > THRESHOLD) return false;

        return true;
    }

    @Override
    protected void end() {        
        System.out.println("[FINAL POS]" + Robot.liftSubsystem.getPos());
    }

    @Override
    protected void interrupted() {
        end();
    }
}