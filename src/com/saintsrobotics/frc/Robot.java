/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import InsightLT.*;
import java.io.IOException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
    private JoystickControl controlSystem;
    private Drive drive;
    private NetworkTable networkTable;
    private Shooter shooter;
    private Vision vision;
    private Climber climber;
    private InsightLT display;
    private LightShow leds;

    private DecimalData battData;
    private StringData teamNum;
    private StringData battWarn;

    private IRobotComponent[] components;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        //networkTable = NetworkTable.getTable("camera");
        leds = new LightShow();
        controlSystem = new JoystickControl();
        vision = new Vision(networkTable);
        drive = new Drive(controlSystem);
        shooter = new Shooter(vision, controlSystem);
        climber = new Climber(controlSystem);
        displayInit();

        components = new IRobotComponent[]{ controlSystem, vision, drive, shooter, climber };
    }

    /**
     * This function is called at the beginning of autonomous.
     */
    public void autonomousInit()
    {
        LightShow.SetDefault();
        Logger.log("Autonomous has begun!");
        enabledRoutine();
    }

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic()
    {
        autonomousRoutine();
    }

    /**
     * This function is called at the beginning of operator control.
     */
    public void teleopInit()
    {
        LightShow.SetDefault();
        Logger.log("Teleop has begun!");
        enabledRoutine();
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic()
    {
        actionRoutine();
    }

    /**
     * This function is called at the beginning of disabled mode.
     */
    public void disabledInit()
    {
        LightShow.SetDisabled();
        Logger.log("The robot has been disabled :(");
        disabledRoutine();
    }

    public void disabledPeriodic()
    {
        displayAct();
    }

    /**
     * This function is called at the beginning of test mode.
     */
    public void testInit()
    {
        Logger.log("Test mode has begun.");
    }

    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic()
    {
    }

    /**
     * Setup Network Tables, and get the NetworkTable for the SmartDashboard.
     * @return The network table for the SmartDashboard.
     */
    private NetworkTable getNetworkTable()
    {
        NetworkTable.setTeam(1899);
        NetworkTable.setServerMode();
        try
        {
            NetworkTable.initialize();
        }
        catch (IOException exception)
        {
            Logger.log(exception);
        }

        return NetworkTable.getTable("SmartDashboard");
    }

    private void disabledRoutine()
    {
        for (int i = 0; i < components.length; i++)
        {
            components[i].robotDisable();
        }
        display.startDisplay();
    }

    private void enabledRoutine()
    {
        for (int i = 0; i < components.length; i++)
        {
            components[i].robotEnable();
        }
        display.stopDisplay();
    }

    private void actionRoutine()
    {
        for (int i = 0; i < components.length; i++)
        {
            components[i].act();
        }
    }

    private void autonomousRoutine()
    {
        for (int i = 0; i < components.length; i++)
        {
            components[i].robotAuton();
        }
    }

    private void displayInit()
    {
        display = new InsightLT(InsightLT.TWO_ONE_LINE_ZONES);

        battData = new DecimalData("Battery: ");

        teamNum = new StringData();
        teamNum.setData("------FRC 1899------");

        battWarn = new StringData();
        battWarn.setData("BATTERY VOLTAGE LOW!");

        display.registerData(battData, InsightLT.LINE_1);
        display.registerData(teamNum, InsightLT.LINE_2);

        display.startDisplay();
    }

    private void displayAct()
    {
        double battVoltage = DriverStation.getInstance().getBatteryVoltage();
        battData.setData(battVoltage);
        if (battVoltage <= 12)
        {
            display.registerData(battWarn, InsightLT.LINE_2);
            display.setZoneScrollTime(InsightLT.LINE_2, 1000);
        }
    }
}
