/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;
import frc.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LiftSubsystem extends Subsystem {
    public WPI_TalonSRX m_motor;
    public final double LOWEST_ARM_POS = 0.0;
    public final double GREATEST_ARM_POS = Math.PI / 2;
    public PID pid;

    public LiftSubsystem(int motorChannel) {
        m_motor = new WPI_TalonSRX(motorChannel);
    }

    public void newPID() {
        pid = new PID(Robot.ARM_P_CONSTANT, 0, Robot.ARM_D_CONSTANT);
    }

    public double getPos() {
        return m_motor.getSensorCollection().getQuadraturePosition();
    }

    public void setSpeed(double speed) {
        m_motor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    protected void initDefaultCommand() {
    }
}