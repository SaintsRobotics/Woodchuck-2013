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
    
    private Jaguar climberMotor;
    
    public Climber(JoystickControl controller)
    {
        this.controller = controller;
        
        climberMotor = new Jaguar(CLIMBER_JAGUAR_CHANNEL);
    }
    
    public void robotDisable() {
        climberMotor.disable();
    }

    public void robotEnable() {
        climberMotor.set(0.0);
    }

    public void act() {
        climberMotor.set(controller.getClimberValue());
    }
    
}
