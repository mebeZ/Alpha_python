package frc.robot.subsystems;

import frc.robot.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoSubsystem extends Subsystem {
	Timer timer;
	public TalonSRX pigeon;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	public double getPigeonAngle(){
		double[] yawPitchRoll = new double[3];
		Robot.pigeon.getYawPitchRoll(yawPitchRoll);
		//System.out.println("PigoenAngle: " + yawPitchRoll[0] % 360.);
		return Math.toRadians(yawPitchRoll[0] % 360.);
	}
	
	public double normalize(double speed) {
		if (speed > 1) return 1.0;
		else if (speed < -1) return -1.0;
		
		return 0;
	}
	
    /* public void getVal() {
        System.out.println("The x-cordinate is " + getPos().get(0));
        
    } */

}