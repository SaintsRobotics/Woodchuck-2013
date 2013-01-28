package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The control system for the robot.
 * @author Saints Robotics
 */
public class ControlSystem {
    public static int LEFT_DRIVE_JOYSTICK_PORT = 1;
    public static int RIGHT_DRIVE_JOYSTICK_PORT = 2;
    public static int OPERATOR_JOYSTICK_PORT = 3;
    
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
