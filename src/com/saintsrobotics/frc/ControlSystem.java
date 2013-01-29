package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The control system for the robot.
 * @author Saints Robotics
 */
public class ControlSystem {
    // USB ports for the joysticks
    public static final int JOYSTICK_LEFT_DRIVE_PORT = 1;
    public static final int JOYSTICK_RIGHT_DRIVE_PORT = 2;
    public static final int JOYSTICK_OPERATOR_PORT = 3;
    
    private Joystick leftDriveJoystick;
    private Joystick rightDriveJoystick;
    private Joystick operatorJoystick;
    
    public ControlSystem(int leftJoystickPort, int rightJoystickPort,
            int operatorJoystickPort) {
        leftDriveJoystick = new Joystick(leftJoystickPort);
        rightDriveJoystick = new Joystick(rightJoystickPort);
        operatorJoystick = new Joystick(operatorJoystickPort);
    }
}
