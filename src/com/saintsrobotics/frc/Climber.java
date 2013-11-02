/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author huadianz
 */
public class Climber implements IRobotComponent
{
    private final JoystickControl controller;
    
    private static final int CLIMBER_JAGUAR_CHANNEL = 6;
    private static final boolean CLIMBER_JAGUAR_INVERTED = false;
    
    private static final int LIMIT_DIGITAL_SIDECAR = 1;
    private static final int LIMIT_DIGITAL_CHANNEL = 5;
    
    private final Motor climberMotor;
    
    private final DigitalInput limitSwitch;
    
    public Climber(JoystickControl controller)
    {
        this.controller = controller;
        
        climberMotor = new Motor(CLIMBER_JAGUAR_CHANNEL, CLIMBER_JAGUAR_INVERTED);
        limitSwitch = new DigitalInput(LIMIT_DIGITAL_SIDECAR, LIMIT_DIGITAL_CHANNEL);
    }
    
    public void robotDisable()
    {
        climberMotor.motor.disable();
    }

    public void robotEnable()
    {
        climberMotor.motor.set(0.0);
    }

    public void act()
    {
        //if (limitSwitch.get() && controller.getClimberValue() > 0)
        //{
        //    climberMotor.motor.set(0.0);
        //}
        //else
        //{
        climberMotor.motor.set(controller.getClimberValue());
        if (limitSwitch.get())
        {
            LightShow.SetClimbFin();
        }
        //}
        report();
    }

    public void robotAuton()
    {
    }
    
    private void report()
    {
        SmartDashboard.putBoolean("Climber Switch", !limitSwitch.get());
    }
}
