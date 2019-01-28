/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import frc.robot.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
    private CANSparkMax leftFrontSpark, leftBackSpark, rightFrontSpark, rightBackSpark;
    public PID pidRight, pidLeft;
    
    /**
     * Initialize robot's motors
     */
    public DriveSubsystem() {
        // Temporary PWM channels
        leftFrontSpark = new CANSparkMax(PortMap.LEFT_FRONT_SPARK, MotorType.kBrushless);
        leftBackSpark = new CANSparkMax(PortMap.LEFT_BACK_SPARK, MotorType.kBrushless);
        rightFrontSpark = new CANSparkMax(PortMap.RIGHT_FRONT_SPARK, MotorType.kBrushless);
        rightBackSpark = new CANSparkMax(PortMap.RIGHT_BACK_SPARK, MotorType.kBrushless);

        pidRight = new PID(Robot.TANK_P_CONSTANT, 0, Robot.TANK_D_CONSTANT);
        pidLeft = new PID(Robot.TANK_P_CONSTANT, 0, Robot.TANK_D_CONSTANT);
    }

    public void setRightSpeedTank(double rightSpeed) {
        rightBackSpark.set(rightSpeed);
        rightFrontSpark.set(rightSpeed);
    }

    public void setLeftSpeedTank(double leftSpeed) {
        leftFrontSpark.set(leftSpeed);
        leftBackSpark.set(leftSpeed);
    }

    public double getRightSpeed() {
        return rightFrontSpark.get();
    }

    public double getLeftSpeed() {
        return leftFrontSpark.get();
    }

    @Override
    protected void initDefaultCommand() {

    }
}