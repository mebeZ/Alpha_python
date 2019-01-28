package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.lang.*;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;


public class FrameCaptureSubsystem {
    private Core c;
    private VideoCapture cap;
    static {
        System.LoadLibrary.;
    }
    public FrameCaptureSubsystem() {
        c = new Core();
        
        cap = new VideoCapture(0);
    }
    
    public void openCam() {
        cap.open(0);
    }
    public void isOpened() {
        if (cap.isOpened()) {
            System.out.println("cap is opened");
        }
    }
}
