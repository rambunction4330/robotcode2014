package main;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Amanda Steiner
 */
//somenewcode
public class DriveTrain {
    private SpeedController rightmf;
    private SpeedController rightmb;
    private SpeedController leftmf;
    private SpeedController leftmb;
    
    public DriveTrain() {
        rightmf = new Jaguar(Map.jaguarRightFront);
        rightmb = new Jaguar(Map.jaguarRightBack);
        leftmf = new Jaguar(Map.jaguarLeftFront);
        leftmb = new Jaguar(Map.jaguarLeftBack);
    }
    public DriveTrain(int fR, int bR, int fL, int bL) {
        rightmf = new Jaguar(fR);
        rightmb = new Jaguar(bR);
        leftmf = new Jaguar(fL);
        leftmb = new Jaguar(bL);
    }
    public void setWheels(double yRight, double yLeft) {
        rightmf.set(yRight*Map.speedConstant);
        rightmb.set(yRight*Map.speedConstant);
        leftmf.set(yLeft*-Map.speedConstant);
        leftmb.set(yLeft*-Map.speedConstant);
    }
}
