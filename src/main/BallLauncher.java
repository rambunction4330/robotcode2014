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
    Joystick shoot = new Joystick(Map.joystickShooter);
    NetworkTable nettable = NetworkTable.getTable("SmartDashboard");
    //Encoder ec = new Encoder(Map.encoderone, Map.encodertwo);
    //Hello GIT is now operational
    
    public void autonomous() {
            if (vP.leftOrRight() == 1) {
            //if (right.getAxis(Joystick.AxisType.kY) < -.1) {
                dT.setWheels(0, 0);
                new Thread()
                {
                    public void run()
                    {
                        Timer.delay(2.5);
                        arm.forceOverride();
                    }
                }.start();
                arm.setArm();
                arm.throwArm();
            }
            if (vP.leftOrRight() == 2) {
            //if (right.getAxis(Joystick.AxisType.kY) > .1) {
                dT.setWheels(0, 0);
                new Thread()
                {
                    public void run()
                    {
                        Timer.delay(2.5);
                        arm.forceOverride();
                    }
                }.start();
                arm.setArm();
                arm.throwArm();
            }
            if (vP.leftOrRight() == 0) {
                dT.setWheels(0, -0);
            }
    }

    public void operatorControl() {
        new Thread(){
            boolean backwards = false;
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
                    if (buttons(11)) {
                        backwards=!backwards;
                         //if (backwards) nettable.putString("Front", "arm/back");
                         //else nettable.putString("Front", "normal");
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
        return shoot.getRawButton(x);
    }
}
