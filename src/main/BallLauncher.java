/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package main;


//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Watchdog;
/**
* @author Amanda & Shen 
*/
public class BallLauncher extends SimpleRobot {
    DriveTrain dT = new DriveTrain();
    ArmMotor arm = new ArmMotor();
    //VisionProcessor vP = new VisionProcessor();
    Joystick right = new Joystick(Map.joystickRight);
    Joystick left = new Joystick(Map.joystickLeft);
    //Encoder ec = new Encoder(Map.encoderone, Map.encodertwo);
    
    public void autonomous() {
        /*while (isEnabled()) {
            //if (vP.leftOrRight() == 1) {
            if (right.getAxis(Joystick.AxisType.kY) < -.1) {
                dT.setWheels(-.6, .6);
            }
            //if (vP.leftOrRight() == 2) {
            if (right.getAxis(Joystick.AxisType.kY) > .1) {
                dT.setWheels(.6, -.6);
            }
            //if (vP.leftOrRight() == 0) {
            else {
                dT.setWheels(0, 0);
            }*/
        //Make it shoot laters.
        //}
    }

    public void operatorControl() {
       new Thread(){
           public void run(){
                while (isEnabled()) {
<<<<<<< OURS
                    dT.setWheels(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));
               //     if (right.getRawButton(3))
               //     {
              //          arm.forceArmStop();
             //       }
=======
            dT.setWheels(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));
>>>>>>> THEIRS
                }
           }
       }.start();
        while (isEnabled()) {
            if (right.getRawButton(2)) {
                arm.setArm();
            }
        }.start();
        new Thread(){
            public void run(){
                while (isEnabled()) {
                    if (right.getRawButton(2))
                    {
                        System.out.println("Set Arm");
                        arm.setArm();
                    }
                    else if (right.getRawButton(1))
                    {
                        arm.throwArm();
                    }
                }
=======
            if (right.getRawButton(5)) {
                arm.releaseArm();
            } else if (right.getRawButton(3)) {
                arm.engageArm();
            } else {
                arm.stopArm();
>>>>>>> THEIRS
            }
        }
    }
    
    public void test() {
    }
}
