package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The control system for the robot.
 * @author Saints Robotics
 */
public class ControlSystem {
    // USB ports for the joysticks
    private static final int JOYSTICK_LEFT_DRIVE_PORT = 1;
    private static final int JOYSTICK_RIGHT_DRIVE_PORT = 2;
    private static final int JOYSTICK_OPERATOR_PORT = 3;
    
    private Joystick leftDriveJoystick;
    private Joystick rightDriveJoystick;
    private Joystick operatorJoystick;
    
    public ControlSystem() {
        leftDriveJoystick = new Joystick(JOYSTICK_LEFT_DRIVE_PORT);
        rightDriveJoystick = new Joystick(JOYSTICK_RIGHT_DRIVE_PORT);
        operatorJoystick = new Joystick(JOYSTICK_OPERATOR_PORT);
    }
}
