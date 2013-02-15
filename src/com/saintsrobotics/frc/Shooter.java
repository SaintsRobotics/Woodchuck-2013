package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;

/**
 * The shooter for the robot.
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
    private final double ENCODER_PULSE_DISTANCE = 1.0/3;
    
    private DigitalInput encoderInput;
    private Encoder shooterEncoder;
    
    private final int SHOOTER_JAGUAR_CHANNEL = 10;
    
    private Jaguar shooterMotor;
    
    private boolean lastSwitched;
    
    public Shooter(Vision vision, JoystickControl controller) {
        this.vision = vision;
        this.controller = controller;
        
        feeder = new Relay(FEEDER_RELAY_CHANNEL);
        feederSwitch = new DigitalInput(FEEDER_DIGITAL_SIDECAR_SLOT, FEEDER_DIGITAL_CHANNEL);
        
        encoderInput = new DigitalInput(ENCODER_DIGITAL_SIDECAR_SLOT, ENCODER_DIGITAL_CHANNEL);
        shooterEncoder = new Encoder(encoderInput, encoderInput, false, EncodingType.k1X);
        shooterEncoder.setDistancePerPulse(ENCODER_PULSE_DISTANCE);
        
        shooterMotor = new Jaguar(SHOOTER_JAGUAR_CHANNEL);
    }

    public void robotDisable() {
        shooterEncoder.stop();
        shooterMotor.disable();
    }

    public void robotEnable() {
        shooterEncoder.reset();
        shooterEncoder.start();
        lastSwitched = feederSwitch.get();
    }

    public void act() {
        shooterMotor.set(controller.getShooterSpeed());
        
        if(feederSwitch.get() && !lastSwitched)
        {
            feeder.set(Relay.Value.kOff);
        }
        else if(controller.getFeederButton())
        {
            feeder.set(Relay.Value.kOn);
        }
        
        lastSwitched = feederSwitch.get();
    }
}
