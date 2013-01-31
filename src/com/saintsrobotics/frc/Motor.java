package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * An abstraction over using motors.
 * @author Saints Robotics
 */
public class Motor {
    public final CANJaguar motor;
    private boolean isInverted;
    
    public Motor(int motorID, boolean isInverted) {
        CANJaguar newMotor = null;
        
        try {
            newMotor = new CANJaguar(motorID);
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
