package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * An abstraction over using motors.
 * @author Saints Robotics
 */
public class Motor {
    public final Jaguar motor;
    private boolean isInverted;
    
    public Motor(int motorID, boolean isInverted) {
        Jaguar newMotor = null;
        
        try {
            newMotor = new Jaguar(motorID);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
        
        this.motor = newMotor;        
        this.isInverted = isInverted;
    }
    
    public int invert() {
        return (isInverted ? -1 : 1);
    }
}
