/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;


import frc.robot.commands.*;
import frc.robot.subsystems.*;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.util.Hashtable;

public class Robot extends IterativeRobot {
    // private static final String kDefaultAuto = "Default";
    // private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    public static LimelightSubsystem limelight;
    public static AutoSubsystem autoSubsystem;
    public static RetroreflectiveTapeSubsystem retroreflective;
    public static DriveSubsystem driveSubsystem;
    public static PigeonIMU pigeon;
    public static CANSparkMax lf, lm, lb, rf, rm, rb, sparkPigeon;

    // Temporary PID constants
    public static final double ARM_P_CONSTANT = 0.1;
    public static final double ARM_D_CONSTANT = 0.1;
    public static final double ARM_T_CONSTANT = 0.1;

    public static final double TANK_P_CONSTANT = 0.1;
    public static final double TANK_D_CONSTANT = 0.1;

    public static final double LOWEST_LIFT_POS = 1;
    public static final double MIDDLE_LIST_POS = 2;
    public static final double GREATEST_LIFT_POS = 3;
    public static ArmSubsystem armSubsystem;
    public static LiftSubsystem liftSubsystem;

    public static OI oi;

    public void robotInit() {
        // m_chooser.setDefaultOption("Default Auto", kDefaultAuto); // These lines were
        // causing errors and weren't necessary. They should either be deleted or
        // restored at some point soon
        // m_chooser.addOption("My Auto", kCustomAuto);
        // SmartDashboard.putData("Auto choices", m_chooser);
        oi = new OI();
        armSubsystem = new ArmSubsystem(PortMap.ARM_SPARK);
        pigeon = new PigeonIMU(PortMap.LEFT_FRONT_SPARK);
        retroreflective = new RetroreflectiveTapeSubsystem();
        driveSubsystem = new DriveSubsystem();
        autoSubsystem = new AutoSubsystem();
        limelight = new LimelightSubsystem();

        liftSubsystem = new LiftSubsystem(10);

        lf = new CANSparkMax(PortMap.LEFT_FRONT_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        lm = new CANSparkMax(PortMap.LEFT_MIDDLE_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        lb = new CANSparkMax(PortMap.LEFT_BACK_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        rf = new CANSparkMax(PortMap.RIGHT_FRONT_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        rm = new CANSparkMax(PortMap.RIGHT_MIDDLE_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        rb = new CANSparkMax(PortMap.RIGHT_BACK_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkPigeon = lf;
                
        new JoystickArmCommand(oi.rightStick.getTwist()).start();
        new JoystickLiftCommand(oi.leftStick.getTwist()).start();

        oi.lowestLiftLevel.whenPressed(new AutoLiftCommand(LOWEST_LIFT_POS));
        oi.middleLeftLevel.whenPressed(new AutoLiftCommand(MIDDLE_LIST_POS));
        oi.greatestLeftLevel.whenPressed(new AutoLiftCommand(GREATEST_LIFT_POS));

        pigeon.setYaw(0., 0);

    }

    public void robotPeriodic() {
    }

    public void autonomousInit() {
        pigeon.setYaw(0., 0);

        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);

        new AutoArmCommand(0).start();
    }

    public void autonomousPeriodic() {
    	Hashtable<String,Double> data = retroreflective.extractData();
    	System.out.println("distance: " + data.get("distance"));
    }

    public void teleopInit(){
        //switchCentricDriving.whenPressed(
         //   new ConditionalDriveCommand(new FieldCentricDriveCommand(), new RobotCentricDriveCommand()));

    }

    public void teleopPeriodic() {
    }

    public void testPeriodic() {
    }

    public void disabledInit() {
        pigeon.setYaw(0., 0);
    }
}
