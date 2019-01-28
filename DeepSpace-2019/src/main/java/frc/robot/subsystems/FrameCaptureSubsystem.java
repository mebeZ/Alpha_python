import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class FrameCapture extends Subsystem {
    Core c = new Core();
    System.loadLibrary(c.NATIVE_LIBRARY_NAME);
    Videocapture v;
    public FrameCapture() {
        cap = new VideoCapture(0);
    }
    public openCam() {
        cap.open(0);
    }
    public isOpened() {
        if (cap.isOpened()) {
            System.out.println("cap is opened");
        }
    }
}
