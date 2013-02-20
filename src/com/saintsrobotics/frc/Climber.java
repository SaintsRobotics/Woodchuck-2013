/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author huadianz
 */
public class Climber implements IRobotComponent{

    private JoystickControl controller;
    
    private static final int CLIMBER_JAGUAR_CHANNEL = 4;
    private static final boolean CLIMBER_JAGUAR_INVERTED = false;
    
    private Motor climberMotor;
    
    public Climber(JoystickControl controller)
    {
        this.controller = controller;
        
        climberMotor = new Motor(CLIMBER_JAGUAR_CHANNEL, CLIMBER_JAGUAR_INVERTED);
    }
    
    public void robotDisable() {
        climberMotor.motor.disable();
    }

    public void robotEnable() {
        climberMotor.motor.set(0.0);
    }

    public void act() {
        climberMotor.motor.set(controller.getClimberValue());
    }
    
}
