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
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        
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
}
