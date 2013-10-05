package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public class Drive implements IRobotComponent {
    // Constants
    private static final int JAGUAR_LEFT_ID = 1;
    private static final int JAGUAR_RIGHT_ID = 2;
    
    private static final boolean JAGUAR_LEFT_INVERTED = true;
    private static final boolean JAGUAR_RIGHT_INVERTED = false;
    
    private static final int ENCODER_DIGITAL_SIDECAR_CHANNEL = 1;
    private static final int ENCODER_LEFT_CHANNEL = 3;
    private static final int ENCODER_RIGHT_CHANNEL = 4;
    
    private static final double ENCODER_CODES_PER_REV = 10;
    private static final double ENCODER_GEARING_RATIO = 10;
    
    private JoystickControl controller;
    
    // Instance variables
    private Motor leftMotor;
    private Motor rightMotor;
    
    private Encoder leftEncoder;
    private DigitalInput leftEncoderInput;
    private Encoder rightEncoder;
    private DigitalInput rightEncoderInput;
    
    private Servo raiseServo;
    
    public Drive(JoystickControl controller) {
        leftMotor = new Motor(JAGUAR_LEFT_ID, JAGUAR_LEFT_INVERTED);
        rightMotor = new Motor(JAGUAR_RIGHT_ID, JAGUAR_RIGHT_INVERTED);
        
        leftEncoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_CHANNEL, ENCODER_LEFT_CHANNEL);
        rightEncoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_CHANNEL, ENCODER_RIGHT_CHANNEL);
        
        leftEncoder = new Encoder(leftEncoderInput, leftEncoderInput, false, EncodingType.k1X);
        rightEncoder = new Encoder(rightEncoderInput, rightEncoderInput, false, EncodingType.k1X);
        
        raiseServo = new Servo(5);
        
        this.controller = controller;
    }
    
    public void act()
    {
        if(controller.getControlMode().value == JoystickControl.ControlMode.arcadeDrive.value)
        {
            arcadeDrive(controller.getArcadeValues());
        }
        else if(controller.getControlMode().value == JoystickControl.ControlMode.tankDrive.value)
        {
            tankDrive(controller.getTankValues());
        }
        else
        {
            arcadeDrive(controller.getArcade1Values());
        }
        
        if(controller.getRaiseButton())
        {
            raiseServo.setAngle(135);
            LightShow.SetClimbUnfin();
        }
        else
        {
            raiseServo.setAngle(180);
        }
        
        report();
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
            leftMotor.motor.set(leftMotor.invert() * leftValue);
            rightMotor.motor.set(rightMotor.invert() * rightValue);
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
        double leftValue = moveValue - rotateValue;
        double rightValue = moveValue + rotateValue;
        double[] motorValues = scale( new double[]{ leftValue, rightValue } );
        
        //System.out.println(leftValue + " : " + rightValue + " :: " + motorValues);
        
        try
        {
            leftMotor.motor.set(leftMotor.invert() * motorValues[0]);
            rightMotor.motor.set(rightMotor.invert() * motorValues[1]);
        }
        catch (Exception e)
        {
            Logger.log(e);
        }
    }
    
    public void stopDrive() {
        try {
            leftMotor.motor.set(0);
            rightMotor.motor.set(0);
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
        
        leftMotor.motor.disable();
        rightMotor.motor.disable();
    }

    public void robotEnable() {
        leftEncoder.reset();
        leftEncoder.start();
        
        rightEncoder.reset();
        rightEncoder.start();
    }

    public void robotAuton() {
    }
    
    private void report() {
        SmartDashboard.putNumber("Arcade Throttle", controller.getArcadeValues()[0]);
        SmartDashboard.putNumber("Arcade Turn", controller.getArcadeValues()[1]);
    }
}
