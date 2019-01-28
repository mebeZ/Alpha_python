package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class RetroreflectiveTapeSubsystem extends Subsystem {

    public final static double meterArea = 0.605; // In percent
    public final static double cameraWidth = 0.015; // In meters
    public final static double tapeLength = .1397; // In meters
    public final static double tapeWidth = .0508; // In meters
    public final static double tapeAngle = .24434; // In radians, approximation according to FRC

    public NetworkTable getTable() {
        return Robot.limelight.getCameraTable();
    }

    public double get(NetworkTable table, String variable) {
        return Robot.limelight.getTableData(table, variable);
    }

    // Below are helper functions for extractData()
    public Comparator<Hashtable<String,Double>> dataCompare = 
        (Hashtable<String,Double> pair1, Hashtable<String, Double> pair2) -> pair1.get("x") > pair2.get("x") ? 1 : -1; // Might need to paramaterize
    public double[] averagePos(Hashtable<String,Double> data1, Hashtable<String,Double> data2){
        return new double[]{(data1.get("x") + data2.get("x"))/2,
                            (data1.get("y") + data2.get("y"))/2};
    }
    public boolean facingLeft(double skew){return skew > -45;}
    public boolean facingRight(double skew) {return skew < -45;}
    public Hashtable<String,Double> createData(NetworkTable t, int nth){
        // Takes a snapshot of data and the nth contour you want and converts it into a hashtable
        Hashtable<String, Double> end = new Hashtable<String, Double>();
        end.put("nth",(double) nth);
        end.put("x",get(t,"tx" + nth));
        end.put("y",get(t,"ty" + nth));
        end.put("s",get(t,"ts" + nth));
        end.put("a",get(t,"ta" + nth));
        return end;
    }

    public double getAngle(double skew){
        double firstQuadAngle = skew + 90;
        if (firstQuadAngle > 45) {firstQuadAngle = 90 - firstQuadAngle;}
        return Math.acos(Math.tan(Math.toRadians(firstQuadAngle))/Math.tan(tapeAngle));
    }

    public double[] center(ArrayList<Hashtable<String,Double>> values){
        boolean leftPair  = facingLeft(values.get(1).get("s")) && facingRight(values.get(0).get("s")) && isContour(values.get(1)) && isContour(values.get(0));
        boolean rightPair = facingRight(values.get(1).get("s")) && facingLeft(values.get(2).get("s")) && isContour(values.get(1)) && isContour(values.get(2));
        if (leftPair)
            return averagePos(values.get(0),values.get(1));
        else if (rightPair)
            return averagePos(values.get(2),values.get(1));
        else 
            return new double[]{};
    }

    public boolean isContour(Hashtable<String,Double> data){return data.get("a") != 0;}
    // Above are helper functions for extractData()

    public Hashtable<String,Double> extractData(){
        // Extracts data from the given data. Currently: Center position, distance, shift 
        NetworkTable t = getTable();
        Hashtable<String,Double> data = new Hashtable<String,Double>();
        String[] keys = new String[]{"centerX","centerY","distance","shift","center tape skew","angle"};
        for (String key : keys){data.put(key,0.0);}
        // Find the center of the two retroflective pieces of tape that are facing
        // twoards each other!
        ArrayList<Hashtable<String,Double>> values = new ArrayList<Hashtable<String,Double>>();
        for(int i = 0; i < 3; i++) {values.add(createData(t,i));}
        values.sort(dataCompare);
        double[] centerPos = center(values);
        if (centerPos.length == 0){return data;}
        System.out.println("detected");
        double distance = Math.sqrt(meterArea / values.get(1).get("a")) + cameraWidth; // Not sure wether this is the correct math
        double degreeLength = tapeLength / (Math.sqrt((tapeLength/tapeWidth) * LimelightSubsystem.imageHeight * LimelightSubsystem.imageWidth * values.get(1).get("a")/100.));
        double shift = degreeLength * centerPos[0]; //Negative to the Left, Positive to the Right
        System.out.println(centerPos[0]);
        data.put("to print", values.get(2).get("s"));
        data.put("angle",getAngle(data.get("center tape skew")));
        data.put("centerX",centerPos[0]);
        data.put("centerY", centerPos[1]);
        data.put("distance", distance);
        data.put("shift", shift);
        return data;
    }

    public void drawCross(Graphics g, int hxPos, int hyPos, int vxPos, int vyPos, int length, int thick) {
        g.fillRect(hxPos, hyPos, length, thick);
        g.fillRect(vxPos, vyPos, thick, length);
    }

    @Override
    protected void initDefaultCommand() {
        // LITTERALLY DIE
    }
}