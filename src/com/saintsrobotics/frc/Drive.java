package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public abstract class Drive {
    // CANJaguar IDs
    public static int CANJAGUAR_FRONT_LEFT_ID = 1;
    public static int CANJAGUAR_FRONT_RIGHT_ID = 2;
    public static int CANJAGUAR_BACK_LEFT_ID = 3;
    public static int CANJAGUAR_BACK_RIGHT_ID = 4;
    
    private CANJaguar frontLeftJaguar;
    private CANJaguar frontRightJaguar;
    private CANJaguar backLeftJaguar;
    private CANJaguar backRightJaguar;
    
    public Drive(int frontLeftJaguarID, int frontRightJaguarID,
            int backLeftJaguarID, int backRightJaguarID) {
        try {
            frontLeftJaguar = new CANJaguar(frontLeftJaguarID);
            frontRightJaguar = new CANJaguar(frontRightJaguarID);
            backLeftJaguar = new CANJaguar(backLeftJaguarID);
            backRightJaguar = new CANJaguar(backRightJaguarID);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
}
