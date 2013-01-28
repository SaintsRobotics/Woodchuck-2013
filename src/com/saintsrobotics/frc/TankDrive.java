package com.saintsrobotics.frc;

/**
 * A tank drive system for the robot.
 * @author Saints Robotics
 */
public class TankDrive extends Drive {
    public TankDrive(int frontLeftJaguarID, int frontRightJaguarID,
            int backLeftJaguarID, int backRightJaguarID) {
        super(frontLeftJaguarID, frontRightJaguarID, backLeftJaguarID,
                backRightJaguarID);
    }
}
