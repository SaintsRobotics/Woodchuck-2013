package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * The drive system for the robot.
 * @author Saints Robotics
 */
public class Drive {
    // CANJaguar constants
    public static final int CANJAGUAR_FRONT_LEFT_ID = 1;
    public static final int CANJAGUAR_FRONT_RIGHT_ID = 2;
    public static final int CANJAGUAR_BACK_LEFT_ID = 3;
    public static final int CANJAGUAR_BACK_RIGHT_ID = 4;
    
    public static final boolean CANJAGUAR_FRONT_LEFT_INVERTED = false;
    public static final boolean CANJAGUAR_FRONT_RIGHT_INVERTED = false;
    public static final boolean CANJAGUAR_BACK_LEFT_INVERTED = false;
    public static final boolean CANJAGUAR_BACK_RIGHT_INVERTED = false;
    
    // Other constants
    private final double MAX_SPEED = 30; // NEEDS TO BE TESTED AND CHANGED
    
    private final CANJaguar.ControlMode CANJAGUAR_CONTROL_MODE =
            CANJaguar.ControlMode.kSpeed;
    private final CANJaguar.NeutralMode CANJAGUAR_NEUTRAL_MODE =
            CANJaguar.NeutralMode.kBrake;
    
    private CANJaguar frontLeftMotor;
    private CANJaguar frontRightMotor;
    private CANJaguar backLeftMotor;
    private CANJaguar backRightMotor;
    
    private int[] motorsInverted = new int[4];
    
    public Drive(int frontLeftMotorID, int frontRightMotorID,
            int backLeftMotorID, int backRightMotorID,
            boolean frontLeftMotorInverted, boolean frontRightMotorInverted,
            boolean backLeftMotorInverted, boolean backRightMotorInverted) {
        try {
            frontLeftMotor = new CANJaguar(frontLeftMotorID);
            frontRightMotor = new CANJaguar(frontRightMotorID);
            backLeftMotor = new CANJaguar(backLeftMotorID);
            backRightMotor = new CANJaguar(backRightMotorID);
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
        
        init();
        invertMotors(frontLeftMotorInverted, frontRightMotorInverted,
                backLeftMotorInverted, backRightMotorInverted);
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
    
    private void invertMotors(boolean frontLeftMotorInverted, boolean frontRightMotorInverted,
            boolean backLeftMotorInverted, boolean backRightMotorInverted) {
        motorsInverted[0] = (frontLeftMotorInverted ? 1 : -1);
        motorsInverted[1] = (frontRightMotorInverted ? 1 : -1);
        motorsInverted[2] = (backLeftMotorInverted ? 1 : -1);
        motorsInverted[3] = (backRightMotorInverted ? 1 : -1);
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
    
    public double limit(double speed) {
        if (speed > MAX_SPEED) {
            return MAX_SPEED;
        }
        if (speed < -MAX_SPEED) {
            return -MAX_SPEED;
        }
        
        return speed;
    }
}
