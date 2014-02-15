/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package main;


//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
/**
* @author Amanda & Shen 
*/
public class BallLauncher extends SimpleRobot {
    DriveTrain dT = new DriveTrain();
    ArmMotor arm = new ArmMotor();
    VisionProcessor vP = new VisionProcessor();
    Joystick right = new Joystick(Map.joystickRight);
    Joystick left = new Joystick(Map.joystickLeft);
    NetworkTable nettable = NetworkTable.getTable("SmartDashboard");
    boolean backwards = false;
    //Encoder ec = new Encoder(Map.encoderone, Map.encodertwo);
    //Hello GIT is now operational
    
    public void autonomous() {
        while (isEnabled()) {
            if (vP.leftOrRight() == 1) {
            //if (right.getAxis(Joystick.AxisType.kY) < -.1) {
                dT.setWheels(-.6, .6);
            }
            if (vP.leftOrRight() == 2) {
            //if (right.getAxis(Joystick.AxisType.kY) > .1) {
                dT.setWheels(.6, -.6);
            }
            if (vP.leftOrRight() == 0) {
                dT.setWheels(0, 0);
                arm.setArm();
                arm.forceOverride();
                Timer.delay(2);
                arm.throwArm();
            }
        //Make it shoot laters.
        }
        
    }

    public void operatorControl() {
        new Thread(){
            public void run(){
                while (isEnabled()) {
                    if (backwards) {
                        dT.setWheelsBackwards(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));
                    }
                    else {
                        dT.setWheels(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));

                    }
                    if (buttons(3)) {
                        arm.forceOverride();
                    }
                    if (buttons(10)) {
                        backwards=!backwards;
                        nettable.putString("Front", backwards ? "arm/back":"normal");
                    }
                }
            }
        }.start();
        new Thread(){
            public void run(){
                while (isEnabled()) {
                    if (buttons(2))
                    {
                        arm.setArm();
                    }
                    else if (buttons(1))
                    {
                        arm.throwArm();
                    }
                }
            }
        }.start();
    }
    
    public void test() {
    }
    
    public boolean buttons(int x) {
        return right.getRawButton(x) || left.getRawButton(x);
    }
}