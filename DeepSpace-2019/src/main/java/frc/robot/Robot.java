/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.OpenWingsCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.commands.ZLiftCommand;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.LineupSubsystem;
import frc.robot.subsystems.PositioningSubsystem;
import frc.robot.subsystems.RetroreflectiveTapeSubsystem;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.subsystems.WingsSubsystem;
import frc.robot.subsystems.ZLiftSubsystem;

public class Robot extends TimedRobot {
  public static OI oi;
  public static TankDriveSubsystem tankDriveSubsystem;
  public static LiftSubsystem liftSubsystem;
  public static PositioningSubsystem pos;
  public static LineupSubsystem lineup;
  public static RetroreflectiveTapeSubsystem retroreflective;
  public static LimelightSubsystem limelight;
  public static WingsSubsystem wingsSubsystem;
  public static ZLiftSubsystem ZLiftSubsystem;
  public static FrameCaptureSubsystem framecapturesubsystem;

  public static PigeonIMU pigeon;
  public static TalonSRX pigeonTalon;
  public static CANSparkMax lf, lm, lb, rf, rm, rb;

  // Constants for LIFT
  public static double LOWEST_LIFT_POS = 1;
  public static double GREATEST_LIFT_POS = 2000;

  // Constants for ZLIFT
  public static double WHEEL_RADIUS = 0.0;
  public static double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
  public static double LEVEL_THREE_HEIGHT = 22;
  public static double MAX_ZLIFT_ANGLE_THRESH = 5;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    oi = new OI();
    tankDriveSubsystem = new TankDriveSubsystem();
    liftSubsystem = new LiftSubsystem();
    pos = new PositioningSubsystem();
    lineup = new LineupSubsystem();
    retroreflective = new RetroreflectiveTapeSubsystem();
    limelight = new LimelightSubsystem();
    wingsSubsystem = new WingsSubsystem();
    ZLiftSubsystem = new ZLiftSubsystem();
    framecapturesubsystem = new FrameCaptureSubsystem();

    oi.startWings.whenPressed(new OpenWingsCommand());
    oi.startZLift.whenPressed(new ZLiftCommand());

    // Initialize left SPARK MAXs
    lf = new CANSparkMax(PortMap.LEFT_FRONT_SPARK, MotorType.kBrushless);
    lm = new CANSparkMax(PortMap.LEFT_MIDDLE_SPARK, MotorType.kBrushless);
    lb = new CANSparkMax(PortMap.LEFT_BACK_SPARK, MotorType.kBrushless);

    // Initialize right SPARK MAXs
    rf = new CANSparkMax(PortMap.RIGHT_FRONT_SPARK, MotorType.kBrushless);
    rm = new CANSparkMax(PortMap.RIGHT_MIDDLE_SPARK, MotorType.kBrushless);
    rb = new CANSparkMax(PortMap.RIGHT_BACK_SPARK, MotorType.kBrushless);
    
    pigeonTalon = new TalonSRX(PortMap.PIGEON_TALON);
    pigeon = new PigeonIMU(pigeonTalon);
    pigeon.setYaw(0.0, 0);

    // m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // SmartDashboard.putData("Auto mode", m_chooser);
    CameraServer.getInstance().startAutomaticCapture();
  }

  public static double getPigeonAngle(){
		double[] yawPitchRoll = new double[3];
		pigeon.getYawPitchRoll(yawPitchRoll);
		//System.out.println("yaw: " + yawPitchRoll[0] + " pitch: " + yawPitchRoll[1] + " roll: " + yawPitchRoll[2]);
		return Math.toRadians(yawPitchRoll[0] % 360.);
	}

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    pigeon.setYaw(0, 30);
    pos.updatePositionTank();
    lineup.resetInfo(0.305 * 6.3, 0.305 * -0.6, Math.toRadians(-19));
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();

    pos.updatePositionTank();

    System.out.println("PRINTING: " + retroreflective.extractData().get("to print"));
  }

  @Override
  public void teleopInit() {
    new TankDriveCommand().start();
    
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
