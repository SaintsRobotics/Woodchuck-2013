package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Relay;

/**
 * The shooter for the robot.
 * @author Saints Robotics
 */
public class Shooter {
    
    private final int FEEDER_RELAY_CHANNEL = 1;
    
    private Vision vision;
    
    private Relay feeder;
    
    public Shooter(Vision vision) {
        this.vision = vision;
        feeder = new Relay(FEEDER_RELAY_CHANNEL);
    }
}
