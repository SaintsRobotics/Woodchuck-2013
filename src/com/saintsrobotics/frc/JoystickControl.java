package com.saintsrobotics.frc;

import com.sun.squawk.util.MathUtils;
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
    
    private int CONTROL_MODE;
    
    private final Joystick TANK_LEFT_JOYSTICK;
    private final Joystick TANK_RIGHT_JOYSTICK;
    
    private static final int TANK_LEFT_JOYSTICK_AXIS = 2;
    private static final int TANK_RIGHT_JOYSTICK_AXIS = 2;
    
    private final Joystick ARCADE_THROTTLE_JOYSTICK;
    private final Joystick ARCADE_TURN_JOYSTICK;
    
    private static final int ARCADE_THROTTLE_JOYSTICK_AXIS = 2;
    private static final int ARCADE_TURN_JOYSTICK_AXIS = 1;
    
    private static final int DRIVE_SLOW_BUTTON = 1;
    private static final double DRIVE_SLOW_VALUE = 0.33;
    
    private static final int SHOOTER_UP_BUTTON = 5;
    private static final int SHOOTER_DOWN_BUTTON = 6;
    private static final int SHOOTER_FEED_BUTTON = 8;
    
    private static final int CLIMBER_JOYSTICK_AXIS = 5;
    
    private ControlMode controlMode;

    private double tankLeftValue = 0.0;
    private double tankRightValue = 0.0;
    private double arcadeThrottleValue = 0.0;
    private double arcadeTurnValue = 0.0;
    private double arcade1TurnValue = 0.0;
    private double shooterValue = 0.0;
    private double climberValue = 0.0;
    private boolean slowButton = false;
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
        arcade1TurnValue= ARCADE_THROTTLE_JOYSTICK.getRawAxis(ARCADE_TURN_JOYSTICK_AXIS);
        
        slowButton = leftDriveJoystick.getRawButton(DRIVE_SLOW_BUTTON);
        curveTurnValues();
        
        if(slowButton)
        {
            slowDriveValues();
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: ON");
        }
        else
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: OFF");
        }
        
        if(leftDriveJoystick.getRawButton(3))
        {
            controlMode = ControlMode.tankDrive;
        }
        
        if(leftDriveJoystick.getRawButton(4))
        {
            controlMode = ControlMode.arcadeDrive;
        }
        
        if(leftDriveJoystick.getRawButton(5))
        {
            controlMode = ControlMode.arcadeDrive1;
        }
        
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
        public static final ControlMode arcadeDrive1 = new ControlMode(2);

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
        controlMode = ControlMode.arcadeDrive;
    }
    
    public ControlMode getControlMode()
    {
        if(controlMode.value == ControlMode.arcadeDrive.value)
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 1, "Control mode: Arcade 2 Joystick");
        }
        else if(controlMode.value == ControlMode.tankDrive.value)    
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 1, "Control mode: Tank");
        }
        else
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 1, "Control mode: Arcade 1 Joystick");
        }
        
        return controlMode;
    }
    
    private void slowDriveValues()
    {
        tankLeftValue *= DRIVE_SLOW_VALUE;
        tankRightValue *= DRIVE_SLOW_VALUE;
        arcadeThrottleValue *= DRIVE_SLOW_VALUE;
    }
    
    private void curveTurnValues()
    {
        arcadeTurnValue = 0.5 * MathUtils.pow(arcadeTurnValue, 3) + 0.5 * arcadeTurnValue;
        arcade1TurnValue = 0.5 * MathUtils.pow(arcade1TurnValue, 3) + 0.5 * arcade1TurnValue;
    }
    
    public double[] getTankValues()
    {
        return new double[]{ tankLeftValue, tankRightValue };
    }
    
    public double[] getArcadeValues()
    {
        return new double[]{ arcadeThrottleValue, arcadeTurnValue };
    }
    
    public double[] getArcade1Values()
    {
        return new double[]{ arcadeThrottleValue, arcade1TurnValue };
    }
    
    public double getShooterSpeed()
    {
        return shooterValue;
    }
    
    public boolean getSlowButton()
    {
        return slowButton;
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
