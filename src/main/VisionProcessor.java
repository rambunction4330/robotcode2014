package main;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;

/**
 *
 * @author Amanda Steiner
 */
public class VisionProcessor {
    private Relay led;
    private NetworkTable green;

    public VisionProcessor() {
        led = new Relay(Map.ledPort);
        green = NetworkTable.getTable("SmartDashboard");
    }
    
    public int leftOrRight(){
       NetworkTable green = NetworkTable.getTable("SmartDashboard");
       led.set(Relay.Value.kForward);
        try {
            // Check COG_X
            if (green.getDouble("COG_X") > 325) {   
                 //System.out.println("1");
                 return 1;
                 //right of camera
            }
            if (green.getDouble("COG_X") < 315) {
                //System.out.println("2");
                return 2;
                //left of camera
            }} catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }
       //System.out.println("0");
       return 0;
       //center of camera
    }
    
    public boolean highGoal() {
        return green.getBoolean("BLOB");
    }
    
    public void ledOn() {
        led.set(Relay.Value.kForward);
    }
}