package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The joystick control for the robot.
 * @author Saints Robotics
 */
public class JoystickControl implements IRobotComponent {
    // USB ports for the joysticks
    private static final int JOYSTICK_LEFT_DRIVE_PORT = 1;
    private static final int JOYSTICK_RIGHT_DRIVE_PORT = 2;
    private static final int JOYSTICK_OPERATOR_PORT = 3;
    
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
    
    private static final int SHOOTER_UP_BUTTON = 5;
    private static final int SHOOTER_DOWN_BUTTON = 6;
    private static final int SHOOTER_FEED_BUTTON = 8;
    
    private static final int CLIMBER_JOYSTICK_AXIS = 2;
    
    private ControlMode controlMode;

    private double tankLeftValue = 0.0;
    private double tankRightValue = 0.0;
    private double arcadeThrottleValue = 0.0;
    private double arcadeTurnValue = 0.0;
    private double shooterValue = 0.0;
    private double climberValue = 0.0;
    private boolean feederButton = false;
    
    public void robotDisable() {
    }

    public void robotEnable() {
        tankLeftValue = 0.0;
        tankRightValue = 0.0;
        arcadeThrottleValue = 0.0;
        arcadeTurnValue = 0.0;
        shooterValue = 0.0;
        climberValue = 0.0;
        feederButton = false;
    }

    public void act() {
        tankLeftValue = TANK_LEFT_JOYSTICK.getRawAxis(TANK_LEFT_JOYSTICK_AXIS);
        tankRightValue = TANK_RIGHT_JOYSTICK.getRawAxis(TANK_RIGHT_JOYSTICK_AXIS);
        arcadeThrottleValue = ARCADE_THROTTLE_JOYSTICK.getRawAxis(ARCADE_THROTTLE_JOYSTICK_AXIS);
        arcadeTurnValue = ARCADE_TURN_JOYSTICK.getRawAxis(ARCADE_TURN_JOYSTICK_AXIS);
        
        if(operatorJoystick.getRawButton(SHOOTER_UP_BUTTON) && shooterValue > 0)
        {
            shooterValue -= 0.01;
        }
        else if(operatorJoystick.getRawButton(SHOOTER_DOWN_BUTTON) && shooterValue < 1)
        {
            shooterValue += 0.01;
        }
        
        feederButton = operatorJoystick.getRawButton(SHOOTER_FEED_BUTTON);
        
        climberValue = operatorJoystick.getRawAxis(CLIMBER_JOYSTICK_AXIS);
    }
    
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
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 1, "Control mode: Arcade");
        }
        else    
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 1, "Control mode: Tank");
        }
        
        return controlMode;
    }
    
    public double[] getTankValues()
    {
        return new double[]{ tankLeftValue, tankRightValue };
    }
    
    public double[] getArcadeValues()
    {
        return new double[]{ arcadeThrottleValue, arcadeTurnValue };
    }
    
    public double getShooterSpeed()
    {
        return shooterValue;
    }
    
    public boolean getFeederButton()
    {
        return feederButton;
    }
    
    public double getClimberValue()
    {
        return climberValue;
    }
}
