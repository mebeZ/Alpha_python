public class OI {
    public Joystick TEST_JOY; 

    public OI() {
      TEST_JOY = new Joystick(RobotMap.TEST_JOYSTICK);
    }

    public double getX() {
      TEST_JOY.getX(); 
    }

    public double getY() {
      TEST_JOY.getY();
    }
  
}
