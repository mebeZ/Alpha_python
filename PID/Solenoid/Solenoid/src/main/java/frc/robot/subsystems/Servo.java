import org.usfirst.frc.team1155.robot.RobotMap;
import org.usfirst.frc.team1155.robot.OI;

public class Servo extends PWM {
    private static Servo1;
    private static Servo2;

    public Servo() {
        Servo1 = new Servo(RobotMap.S1);
        Servo2 = new Servo(RobotMap.S2);  
    }

    public static void moveCamera(double speedX, double speedY) {
        Servo1.set(speedX);
        Servo2.set(speedY);
    }
    
}