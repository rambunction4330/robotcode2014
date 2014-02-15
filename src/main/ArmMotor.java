/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 *
 * @author asteiner
 */
public class ArmMotor {
    private SpeedController one;
    private SpeedController two;
    private Relay rac;
    private DigitalInput lim;
    private boolean override;
    private NetworkTable nettable;
    
    public ArmMotor() {
        one = new Jaguar(Map.armMotorOne);
        two = new Jaguar(Map.armMotorTwo);
        rac = new Relay(Map.armRachet);
        lim = new DigitalInput(Map.shooterDownLimit);
        override = false;
        nettable = NetworkTable.getTable("SmartDashboard");
    }
    
    public void setArm() {
        //boolean runthread = true;
        Thread displaylimit = new Thread(){
            public void run(){
                while(true) {
                //while(runthread) {
                    nettable.putBoolean("Shooter Limit Switch Pressed", lim.get());
                    }
                }
            };
        displaylimit.start();
        rac.set(Relay.Value.kForward);
        override = false;
        while (!(lim.get() || override))
        {
            one.set(-.7);
            two.set(-.7);
        }
        one.set(0);
        two.set(0);
        rac.set(Relay.Value.kOff);
        displaylimit.interrupt();
        //runthread = false;
    }
    
    public void throwArm() {
        rac.set(Relay.Value.kReverse);
        Timer.delay(1);
        rac.set(Relay.Value.kOff);
    }
    
    public void forceOverride()
    {
        override = true;
    }
    
    /*public void releaseArm() {
        rac.set(Relay.Value.kForward);
    }
    
    public void engageArm() {
        rac.set(Relay.Value.kReverse);
    }
    public void stopArm() {
        rac.set(Relay.Value.kOff);
    }*/
}
