package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public class Drive implements IRobotComponent {
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
    
    private static final int ENCODER_LEFT_CHANNEL = 1;
    private static final int ENCODER_RIGHT_CHANNEL = 2;
    
    private static final double ENCODER_CODES_PER_REV = 10;
    private static final double ENCODER_GEARING_RATIO = 10;
    
    private JoystickControl controller;
    
    // Instance variables
    private Motor frontLeftMotor;
    private Motor frontRightMotor;
    private Motor backLeftMotor;
    private Motor backRightMotor;
    
    private Encoder leftEncoder;
    private DigitalInput leftEncoderInput;
    private Encoder rightEncoder;
    private DigitalInput rightEncoderInput;
    
    public Drive(JoystickControl controller) {
        frontLeftMotor = new Motor(CANJAGUAR_FRONT_LEFT_ID,
                CANJAGUAR_FRONT_LEFT_INVERTED);
        frontRightMotor = new Motor(CANJAGUAR_FRONT_RIGHT_ID,
                CANJAGUAR_FRONT_RIGHT_INVERTED);
        backLeftMotor = new Motor(CANJAGUAR_BACK_LEFT_ID,
                CANJAGUAR_BACK_LEFT_INVERTED);
        backRightMotor = new Motor(CANJAGUAR_BACK_RIGHT_ID,
                CANJAGUAR_BACK_RIGHT_INVERTED);
        
        leftEncoderInput = new DigitalInput(ENCODER_LEFT_CHANNEL);
        rightEncoderInput = new DigitalInput(ENCODER_RIGHT_CHANNEL);
        
        leftEncoder = new Encoder(leftEncoderInput, leftEncoderInput, false, EncodingType.k1X);
        rightEncoder = new Encoder(rightEncoderInput, rightEncoderInput, false, EncodingType.k1X);
        
        this.controller = controller;
        
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
    
    public void act()
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

    public void robotDisable() {
        leftEncoder.stop();
        rightEncoder.stop();
        
        try
        {
            frontLeftMotor.motor.disableControl();
            frontRightMotor.motor.disableControl();
            backLeftMotor.motor.disableControl();
            backRightMotor.motor.disableControl();
        }
        catch (CANTimeoutException e)
        {
            Logger.log(e);
        }
    }

    public void robotEnable() {
        leftEncoder.reset();
        leftEncoder.start();
        
        rightEncoder.reset();
        rightEncoder.start();
        
        try
        {
            frontLeftMotor.motor.enableControl();
            frontRightMotor.motor.enableControl();
            backLeftMotor.motor.enableControl();
            backRightMotor.motor.enableControl();
        }
        catch (CANTimeoutException e)
        {
            Logger.log(e);
        }
    }
}
