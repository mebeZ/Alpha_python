package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public final PIDController turncontroller;

public class LiftArmSubsystem extends PIDSubsystem {
    public LiftArmSubsystem(double p, double i, double d, Encoder encoder, TalonSRX motor) {
        super("Lift", p, i, d);
        m_encoder = encoder;
        lift_motor = motor;
        Gyro g = new Gyro(RobotMap.)
    }

    lift_motor.setNeutralMode(NeutralMode.Break);

    public boolean getChange() {
        if(m_encoder.getRaw() == 0)
            return true;
        return false;
    }

    public void getChange() {
    
    }
    

    public void startAdjustment(double setPoint) {
        getPIDController.setPercentTolerance(1);
        
    }
    public void pidWrite(double output) {
       m_encoder.set(ControlMode.PercentOutput, output);
    }

}