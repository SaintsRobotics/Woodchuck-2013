package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public class Drive {
    // Constants
    private static final int CANJAGUAR_FRONT_LEFT_ID = 1;
    private static final int CANJAGUAR_FRONT_RIGHT_ID = 2;
    private static final int CANJAGUAR_BACK_LEFT_ID = 3;
    private static final int CANJAGUAR_BACK_RIGHT_ID = 4;
    
    private static final boolean CANJAGUAR_FRONT_LEFT_INVERTED = false;
    private static final boolean CANJAGUAR_FRONT_RIGHT_INVERTED = false;
    private static final boolean CANJAGUAR_BACK_LEFT_INVERTED = false;
    private static final boolean CANJAGUAR_BACK_RIGHT_INVERTED = false;
    
    private static final CANJaguar.ControlMode CANJAGUAR_CONTROL_MODE =
            CANJaguar.ControlMode.kPercentVbus;
    private static final CANJaguar.NeutralMode CANJAGUAR_NEUTRAL_MODE =
            CANJaguar.NeutralMode.kBrake;
    
    // Instance variables
    private Motor frontLeftMotor;
    private Motor frontRightMotor;
    private Motor backLeftMotor;
    private Motor backRightMotor;
    
    public Drive() {
        frontLeftMotor = new Motor(CANJAGUAR_FRONT_LEFT_ID,
                CANJAGUAR_FRONT_LEFT_INVERTED);
        frontRightMotor = new Motor(CANJAGUAR_FRONT_RIGHT_ID,
                CANJAGUAR_FRONT_RIGHT_INVERTED);
        backLeftMotor = new Motor(CANJAGUAR_BACK_LEFT_ID,
                CANJAGUAR_BACK_LEFT_INVERTED);
        backRightMotor = new Motor(CANJAGUAR_BACK_RIGHT_ID,
                CANJAGUAR_BACK_RIGHT_INVERTED);
        
        init();
    }
    
    private void init() {
        try {
            // Set all motors to have the same modes
            frontLeftMotor.motor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            frontRightMotor.motor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            backLeftMotor.motor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            backRightMotor.motor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            
            frontLeftMotor.motor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            frontRightMotor.motor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            backLeftMotor.motor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            backRightMotor.motor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
    
    public void drive(JoystickControl controller)
    {
        if(controller.getControlMode().value == JoystickControl.ControlMode.arcadeDrive.value)
        {
            tankDrive(controller.getArcadeValues());
        }
        else
        {
            arcadeDrive(controller.getTankValues());
        }
    }
    
    //Index 0: left side motor value
    //Index 1: right side motor value
    public void tankDrive(double[] motorValues)
    {
        tankDrive(motorValues[0], motorValues[1]);
    }
    
    public void tankDrive(double leftValue, double rightValue) {
        leftValue = limit(leftValue);
        rightValue = limit(rightValue);
        
        try {
            frontLeftMotor.motor.setX(frontLeftMotor.invert() * leftValue);
            frontRightMotor.motor.setX(frontRightMotor.invert() * rightValue);
            backLeftMotor.motor.setX(backLeftMotor.invert() * leftValue);
            backRightMotor.motor.setX(backRightMotor.invert() * rightValue);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
    
    //Index 0: throttle value
    //Index 1: turning value
    public void arcadeDrive(double[] motorValues)
    {
        arcadeDrive(motorValues[0], motorValues[1]);
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        double leftValue = moveValue + rotateValue;
        double rightValue = moveValue - rotateValue;
        double[] motorValues = scale( new double[]{ leftValue, rightValue } );
        
        try
        {
            frontLeftMotor.motor.setX(frontLeftMotor.invert() * motorValues[0]);
            frontRightMotor.motor.setX(frontRightMotor.invert() * motorValues[1]);
            backLeftMotor.motor.setX(backLeftMotor.invert() * motorValues[0]);
            backRightMotor.motor.setX(backRightMotor.invert() * motorValues[1]);
        }
        catch (Exception e)
        {
            Logger.log(e);
        }
    }
    
    public void stopDrive() {
        try {
            frontLeftMotor.motor.setX(0);
            frontRightMotor.motor.setX(0);
            backLeftMotor.motor.setX(0);
            backRightMotor.motor.setX(0);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
    
    public double limit(double value) {
        if (value > 1) {
            return 1;
        }
        if (value < -1) {
            return -1;
        }
        
        return value;
    }
    
    public double[] scale (double[] values)
    {
        double scale = 1.0;
        double newValues[] = new double[values.length];
        
        for(int i = 0; i < values.length; i++)
        {
            double currentScale = 1 / Math.abs(values[i]);
            scale = scale > currentScale ? currentScale : scale;
        }
        
        if(scale < 1.0)
        {
            for(int i = 0; i < values.length; i++)
            {
                newValues[i] = scale * values[i];
            }
            
            return newValues;
        }
        else
        {
            return values;
        }
    }
}
