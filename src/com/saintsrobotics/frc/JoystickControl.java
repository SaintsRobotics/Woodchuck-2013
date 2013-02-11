package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The joystick control for the robot.
 * @author Saints Robotics
 */
public class JoystickControl {
    // USB ports for the joysticks
    private static final int JOYSTICK_LEFT_DRIVE_PORT = 1;
    private static final int JOYSTICK_RIGHT_DRIVE_PORT = 2;
    private static final int JOYSTICK_OPERATOR_PORT = 3;
    
    // Constant to convert joystick value to between -1 to 1
    private final int JOYSTICK_CONSTANT = 200;
    
    private Joystick leftDriveJoystick;
    private Joystick rightDriveJoystick;
    private Joystick operatorJoystick;
    
    private final Joystick TANK_LEFT_JOYSTICK;
    private final Joystick TANK_RIGHT_JOYSTICK;
    
    private static final int TANK_LEFT_JOYSTICK_AXIS = 2;
    private static final int TANK_RIGHT_JOYSTICK_AXIS = 2;
    
    private final Joystick ARCADE_THROTTLE_JOYSTICK;
    private final Joystick ARCADE_TURN_JOYSTICK;
    
    private static final int ARCADE_THROTTLE_JOYSTICK_AXIS = 1;
    private static final int ARCADE_TURN_JOYSTICK_AXIS = 2;
    
    private ControlMode controlMode;
    
    public static class ControlMode{
        public final int value;
        
        public static final ControlMode arcadeDrive = new ControlMode(0);
        public static final ControlMode tankDrive = new ControlMode(1);

        private ControlMode(int value)
        {
            this.value = value;
        }
    }
    
    public JoystickControl() {
        leftDriveJoystick = new Joystick(JOYSTICK_LEFT_DRIVE_PORT);
        rightDriveJoystick = new Joystick(JOYSTICK_RIGHT_DRIVE_PORT);
        operatorJoystick = new Joystick(JOYSTICK_OPERATOR_PORT);
        
        TANK_LEFT_JOYSTICK = leftDriveJoystick;
        TANK_RIGHT_JOYSTICK = rightDriveJoystick;
        
        ARCADE_THROTTLE_JOYSTICK = leftDriveJoystick;
        ARCADE_TURN_JOYSTICK = rightDriveJoystick;
        
        //Default driving mode
        controlMode = ControlMode.tankDrive;
    }
    
    public ControlMode getControlMode()
    {
        if(controlMode.value == ControlMode.arcadeDrive.value)
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 0, "Control mode: Arcade");
        }
        else    
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 0, "Control mode: Tank");
        }
        
        return controlMode;
    }
    
    public double readRightJoystickX() {
        return leftDriveJoystick.getRawAxis(2) / JOYSTICK_CONSTANT;
    }
    
    public double readRightJoystickY() {
        return leftDriveJoystick.getRawAxis(1) / JOYSTICK_CONSTANT;
    }
}
