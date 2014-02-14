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
    
    public ArmMotor() {
        one = new Jaguar(Map.armMotorOne);
        two = new Jaguar(Map.armMotorTwo);
        rac = new Relay(Map.armRachet);
        lim = new DigitalInput(Map.shooterDownLimit);
        override = false;
    }
    
    public void setArm() {
        rac.set(Relay.Value.kForward);
        one.set(-1);
        two.set(-1);
        while (!lim.get()) {// || override
            one.set(0);
            two.set(0);
            rac.set(Relay.Value.kOff);
            override = false;
        }
    }
    
    public void forceArmStop()
        {
            override = true;
        }
    
    public void throwArm() {
        rac.set(Relay.Value.kReverse);
        Timer.delay(1);
        rac.set(Relay.Value.kOff);
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
