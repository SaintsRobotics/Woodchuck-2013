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
    private CANJaguar frontLeftMotor;
    private CANJaguar frontRightMotor;
    private CANJaguar backLeftMotor;
    private CANJaguar backRightMotor;
    
    private int[] motorsInverted = new int[4];
    
    public Drive() {
        try {
            frontLeftMotor = new CANJaguar(CANJAGUAR_FRONT_LEFT_ID);
            frontRightMotor = new CANJaguar(CANJAGUAR_FRONT_RIGHT_ID);
            backLeftMotor = new CANJaguar(CANJAGUAR_BACK_LEFT_ID);
            backRightMotor = new CANJaguar(CANJAGUAR_BACK_RIGHT_ID);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
        
        init();
        invertMotors();
    }
    
    private void init() {
        try {
            // Set all motors to have the same modes
            frontLeftMotor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            frontRightMotor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            backLeftMotor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            backRightMotor.changeControlMode(CANJAGUAR_CONTROL_MODE);
            
            frontLeftMotor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            frontRightMotor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            backLeftMotor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
            backRightMotor.configNeutralMode(CANJAGUAR_NEUTRAL_MODE);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
    
    private void invertMotors() {
        motorsInverted[0] = (CANJAGUAR_FRONT_LEFT_INVERTED ? 1 : -1);
        motorsInverted[1] = (CANJAGUAR_FRONT_RIGHT_INVERTED ? 1 : -1);
        motorsInverted[2] = (CANJAGUAR_BACK_LEFT_INVERTED ? 1 : -1);
        motorsInverted[3] = (CANJAGUAR_BACK_RIGHT_INVERTED ? 1 : -1);
    }
    
    public void tankDrive(double leftValue, double rightValue) {
        leftValue = limit(leftValue);
        rightValue = limit(rightValue);
        
        try {
            frontLeftMotor.setX(motorsInverted[0] * leftValue);
            frontRightMotor.setX(motorsInverted[1] * rightValue);
            backLeftMotor.setX(motorsInverted[2] * leftValue);
            backRightMotor.setX(motorsInverted[3] * rightValue);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        
    }
    
    public void stopDrive() {
        try {
            frontLeftMotor.setX(0);
            frontRightMotor.setX(0);
            backLeftMotor.setX(0);
            backRightMotor.setX(0);
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
