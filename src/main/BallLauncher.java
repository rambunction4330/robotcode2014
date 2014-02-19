/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package main;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

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
    Encoder encA = new Encoder(Map.ENCODER_RIGHT_A, Map.ENCODER_RIGHT_B);
    Encoder encB = new Encoder(Map.ENCODER_LEFT_A, Map.ENCODER_LEFT_B);
    boolean shootAnyway;

    public void autonomous() {
        shootAnyway = false;
        //int targetAcquired = vP.leftOrRight();
        new Thread() {
            public void run() {
                Timer.delay(2.5);
                shootAnyway = true;
            }
        }.start();
        while(!shootAnyway && vP.leftOrRight() == 0 && isAutonomous() && isEnabled());
        //Timer.delay(1);
        encB.reset();
        encB.setDistancePerPulse(1.0/243); //243.5
        encB.setSamplesToAverage(127);
        encB.start();
        dT.setWheels(-.7, -.7);
        new Thread() {
            public void run() {
                Timer.delay(2.25);
                arm.forceOverride();
            }
        }.start();
        arm.setArm();
        while (encB.getDistance() >= -7.875 && encB.getDistance() <= 7.875) {
            System.out.println("EncB Dist:" + encB.getDistance());
        }
        System.out.println("Final EncB Dist:" + encB.getDistance());
        encB.reset();
        dT.setWheels(0, 0);
        Timer.delay(.75);
        if (isAutonomous() && isEnabled()) {
//            if (targetAcquired != 2) {
//                System.out.println("No Target");
//                Timer.delay(2);
//            } else {
//                System.out.println("Target was found");
//            }
            arm.throwArm();
        }
        
        shootAnyway = false;
    }

    public void operatorControl() {
        new Thread() {
            boolean backwards = false;

            public void run() {
                while (isEnabled()) {
                    if (backwards) {
                        dT.setWheelsBackwards(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));
                    } else {
                        dT.setWheels(right.getAxis(Joystick.AxisType.kY), left.getAxis(Joystick.AxisType.kY));

                    }
                    if (buttons(3)) {
                        arm.forceOverride();
                    }
                    if (buttons(11)) {
                        backwards = !backwards;
                        //if (backwards) nettable.putString("Front", "arm/back");
                        //else nettable.putString("Front", "normal");
                        nettable.putString("Front", backwards ? "arm/back" : "normal");
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                while (isEnabled()) {
                    if (buttons(2)) {
                        arm.setArm();
                    } else if (buttons(1)) {
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
