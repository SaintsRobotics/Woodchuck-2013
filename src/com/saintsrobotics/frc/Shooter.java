package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
 * The shooter for the robot.
 *
 * @author Saints Robotics
 */
public class Shooter implements IRobotComponent {

    private final int FEEDER_RELAY_CHANNEL = 1;
    private final int FEEDER_DIGITAL_SIDECAR_SLOT = 1;
    private final int FEEDER_DIGITAL_CHANNEL = 1;
    private Relay feeder;
    private DigitalInput feederSwitch;
    private Vision vision;
    private JoystickControl controller;
    private final int ENCODER_DIGITAL_SIDECAR_SLOT = 1;
    private final int ENCODER_DIGITAL_CHANNEL = 2;
    private final double ENCODER_PULSE_DISTANCE = 1.0 / 3;
    private final int ENCODER_AVERAGE_SAMPLES = 25;
    private DigitalInput encoderInput;
    private Counter shooterEncoder;
    private static final int SHOOTER_JAGUAR_CHANNEL = 10;
    private static final boolean SHOOTER_JAGUAR_INVERTED = false;
    private Motor shooterMotor;
    private MovingAverage averageSpeed;
    private boolean lastSwitched;
    private int cycleCounts;
    private double lastShotCount;
    private double rateCount;
    private double prevTime;
    private double currentSpeed;
    private boolean autoFeed;

    public Shooter(Vision vision, JoystickControl controller) {
        this.vision = vision;
        this.controller = controller;

        averageSpeed = new MovingAverage(ENCODER_AVERAGE_SAMPLES);

        feeder = new Relay(FEEDER_RELAY_CHANNEL);
        feeder.setDirection(Relay.Direction.kForward);
        feederSwitch = new DigitalInput(FEEDER_DIGITAL_SIDECAR_SLOT, FEEDER_DIGITAL_CHANNEL);

        encoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_SLOT, ENCODER_DIGITAL_CHANNEL);
        shooterEncoder = new Counter(encoderInput);
        shooterEncoder.setSemiPeriodMode(true);

        shooterMotor = new Motor(SHOOTER_JAGUAR_CHANNEL, SHOOTER_JAGUAR_INVERTED);

        cycleCounts = 0;
        rateCount = 0.0;
        prevTime = 0.0;
        currentSpeed = 0.0;
    }

    public void robotDisable() {
        shooterEncoder.stop();
        shooterMotor.motor.disable();
        cycleCounts = 0;
        lastShotCount = 0;
    }

    public void robotEnable() {
        shooterEncoder.reset();
        shooterEncoder.start();
        lastSwitched = feederSwitch.get();
        SmartDashboard.putBoolean("Limit", lastSwitched);
    }
    
    public void robotAuton() {
        shooterMotor.motor.set(0.99);
        
        if (cycleCounts % 5 == 0) {
            currentSpeed = 10 * (shooterEncoder.get() - rateCount) / (Timer.getFPGATimestamp() - prevTime);
            rateCount = shooterEncoder.get();
            prevTime = Timer.getFPGATimestamp();
        }
        
        if(/*cycleCounts == 100 || */cycleCounts == 250 || cycleCounts == 400 || cycleCounts == 550)
        {
            autoFeed = true;
            lastShotCount = Timer.getFPGATimestamp();
        }
        else if(/*cycleCounts == 120 ||*/ cycleCounts == 270 || cycleCounts == 420 || cycleCounts == 570)
        {
            autoFeed = false;
        }
        
        cycleCounts++;
        
        if (feederSwitch.get() && !lastSwitched) {
            feeder.set(Relay.Value.kOff);
        } else if (autoFeed) {
            feeder.set(Relay.Value.kOn);
        }

        lastSwitched = feederSwitch.get();
        report();
    }

    public void act() {
        shooterMotor.motor.set(controller.getShooterSpeed());

        /*
         * if(shooterEncoder.getRate() * 60 > controller.getShooterSpeed() *
         * 5000) { shooterMotor.motor.set(0); } else {
         * shooterMotor.motor.set(1); }
         */

        if (cycleCounts == 5) {
            currentSpeed = 10 * (shooterEncoder.get() - rateCount) / (Timer.getFPGATimestamp() - prevTime);
            rateCount = shooterEncoder.get();
            prevTime = Timer.getFPGATimestamp();
            cycleCounts = 0;

        }
        cycleCounts++;
        
        if (feederSwitch.get() && !lastSwitched) {
            feeder.set(Relay.Value.kOff);
        } else if (controller.getFeederButton()) {
            feeder.set(Relay.Value.kOn);
            lastShotCount = Timer.getFPGATimestamp();
        }

        lastSwitched = feederSwitch.get();
        SmartDashboard.putBoolean("Limit", lastSwitched);
        report();
    }

    private void report() {
        DriverStationComm.printMessage(DriverStationLCD.Line.kUser2, 1, "Shoot Spd: " + Double.valueOf(currentSpeed).toString());
        DriverStationComm.printMessage(DriverStationLCD.Line.kUser3, 1, "Shoot Pwr: " + Double.valueOf(controller.getShooterSpeed() * 5000).toString());
        SmartDashboard.putNumber("Shooter Speed", currentSpeed);
        SmartDashboard.putNumber("Shooter Power", controller.getShooterSpeed() * 5000);
        SmartDashboard.putBoolean("Limit Switch", feederSwitch.get());
        SmartDashboard.putNumber("Last Shot Time", lastShotCount - Timer.getFPGATimestamp());
    }
}
