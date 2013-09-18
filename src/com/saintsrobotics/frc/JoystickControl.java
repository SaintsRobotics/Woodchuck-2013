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
    //private static final int JOYSTICK_LEFT_DRIVE_PORT = 1;
    //private static final int JOYSTICK_RIGHT_DRIVE_PORT = 2;
    private static final int JOYSTICK_OPERATOR_PORT = 2;
    private static final int JOYSTICK_XBOX_DRIVE_PORT = 1;
    
    //private Joystick leftDriveJoystick;
    //private Joystick rightDriveJoystick;
    private Joystick operatorJoystick;
    private Joystick xboxDriveJoystick;
    
    private int CONTROL_MODE;
    
    private final Joystick XBOX_DRIVE_JOYSTICK;
    
    //private final Joystick TANK_LEFT_JOYSTICK;
    //private final Joystick TANK_RIGHT_JOYSTICK;
    
    private static final int TANK_LEFT_JOYSTICK_AXIS = 2;
    private static final int TANK_RIGHT_JOYSTICK_AXIS = 5;
    
    //private final Joystick ARCADE_THROTTLE_JOYSTICK;
    //private final Joystick ARCADE_TURN_JOYSTICK;
    
    private static final int ARCADE_THROTTLE_JOYSTICK_AXIS = 2;
    private static final int ARCADE_TURN_JOYSTICK_AXIS = 4;
    private static final int ARCADE_1_TURN_JOYSTICK_AXIS = 1;
    
    private static final int DRIVE_SLOW_BUTTON = 5;
    private static final double DRIVE_SLOW_VALUE = 0.33;
    private static final double DRIVE_DEAD_ZONE = 0.13;
    
    private static final int SHOOTER_SET_BUTTON = 1;
    private static final int SHOOTER_SET_ZERO_BUTTON = 7;
    private static final int SHOOTER_UP_BUTTON = 5;
    private static final int SHOOTER_DOWN_BUTTON = 6;
    private static final int SHOOTER_FEED_BUTTON = 8;
    
    private static final double SHOOTER_PRESET_VALUE = 0.99;
    private static final double SHOOTER_INCREMENT_VALUE = 0.005;
    
    private static final int CLIMBER_JOYSTICK_AXIS = 3;
    
    private static final int RAISE_BUTTON = 4;
    
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
    private boolean raiseButton = false;
    
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
        //tankLeftValue = TANK_LEFT_JOYSTICK.getRawAxis(TANK_LEFT_JOYSTICK_AXIS);
        //tankRightValue = TANK_RIGHT_JOYSTICK.getRawAxis(TANK_RIGHT_JOYSTICK_AXIS);
        //arcadeThrottleValue = ARCADE_THROTTLE_JOYSTICK.getRawAxis(ARCADE_THROTTLE_JOYSTICK_AXIS);
        //arcadeTurnValue = ARCADE_TURN_JOYSTICK.getRawAxis(ARCADE_TURN_JOYSTICK_AXIS);
        //arcade1TurnValue= ARCADE_THROTTLE_JOYSTICK.getRawAxis(ARCADE_TURN_JOYSTICK_AXIS);
        
        tankLeftValue = XBOX_DRIVE_JOYSTICK.getRawAxis(TANK_LEFT_JOYSTICK_AXIS);
        tankRightValue = XBOX_DRIVE_JOYSTICK.getRawAxis(TANK_RIGHT_JOYSTICK_AXIS);
        arcadeThrottleValue = XBOX_DRIVE_JOYSTICK.getRawAxis(ARCADE_THROTTLE_JOYSTICK_AXIS);
        arcadeTurnValue = XBOX_DRIVE_JOYSTICK.getRawAxis(ARCADE_TURN_JOYSTICK_AXIS);
        arcade1TurnValue= XBOX_DRIVE_JOYSTICK.getRawAxis(ARCADE_1_TURN_JOYSTICK_AXIS);
        
        slowButton = XBOX_DRIVE_JOYSTICK.getRawButton(DRIVE_SLOW_BUTTON);
        curveTurnValues();
        deadZone();
        
        if(slowButton)
        {
            //slowDriveValues();
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: ON");
        }
        else
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: OFF");
        }
        
        if(XBOX_DRIVE_JOYSTICK.getRawButton(4))
        {
            controlMode = ControlMode.tankDrive;
        }
        
        if(XBOX_DRIVE_JOYSTICK.getRawButton(3))
        {
            controlMode = ControlMode.arcadeDrive;
        }
        
        if(XBOX_DRIVE_JOYSTICK.getRawButton(2))
        {
            controlMode = ControlMode.arcadeDrive1;
        }
        
        if(operatorJoystick.getRawButton(SHOOTER_SET_BUTTON))
        {
            shooterValue = SHOOTER_PRESET_VALUE;
            LightShow.SetShootStandby();
        }
        else if(operatorJoystick.getRawButton(SHOOTER_SET_ZERO_BUTTON))
        {
            shooterValue = 0.0;
            LightShow.SetDefault();
        }
        
        if(operatorJoystick.getRawButton(SHOOTER_UP_BUTTON) && shooterValue > 0)
        {
            shooterValue -= SHOOTER_INCREMENT_VALUE;
        }
        else if(operatorJoystick.getRawButton(SHOOTER_DOWN_BUTTON) && shooterValue < 1)
        {
            shooterValue += SHOOTER_INCREMENT_VALUE;
        }
        
        feederButton = operatorJoystick.getRawButton(SHOOTER_FEED_BUTTON);
        
        raiseButton = operatorJoystick.getRawButton(RAISE_BUTTON);
        
        climberValue = operatorJoystick.getRawAxis(CLIMBER_JOYSTICK_AXIS);
    }

    private void deadZone() {        
        if (Math.abs(tankLeftValue) < DRIVE_DEAD_ZONE) {
            tankLeftValue = 0;
        }
        if (Math.abs(tankRightValue) < DRIVE_DEAD_ZONE) {
            tankRightValue = 0;
        }
        if (Math.abs(arcadeThrottleValue) < DRIVE_DEAD_ZONE) {
            arcadeThrottleValue = 0;
        }
        if (Math.abs(arcadeTurnValue) < DRIVE_DEAD_ZONE) {
            arcadeTurnValue = 0;
        }
        if (Math.abs(arcade1TurnValue) < DRIVE_DEAD_ZONE) {
            arcade1TurnValue = 0;
        }
    }

    public void robotAuton() {
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
        //leftDriveJoystick = new Joystick(JOYSTICK_LEFT_DRIVE_PORT);
        //rightDriveJoystick = new Joystick(JOYSTICK_RIGHT_DRIVE_PORT);
        operatorJoystick = new Joystick(JOYSTICK_OPERATOR_PORT);
        xboxDriveJoystick = new Joystick(JOYSTICK_XBOX_DRIVE_PORT);
        
        //TANK_LEFT_JOYSTICK = leftDriveJoystick;
        //TANK_RIGHT_JOYSTICK = rightDriveJoystick;
        
        //ARCADE_THROTTLE_JOYSTICK = leftDriveJoystick;
        //ARCADE_TURN_JOYSTICK = rightDriveJoystick;
        
        XBOX_DRIVE_JOYSTICK = xboxDriveJoystick;
        
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
    
    public boolean getRaiseButton()
    {
        return raiseButton;
    }
    
    public double getClimberValue()
    {
        return climberValue;
    }
}
