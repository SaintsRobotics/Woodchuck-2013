/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    private ControlSystem controlSystem;
    private Drive drive;
    private DriverStationComm driverStation;
    private NetworkTable networkTable;
    private Shooter shooter;
    private Vision vision;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        driverStation = new DriverStationComm();
        networkTable = getNetworkTable();
        vision = new Vision(networkTable);
        drive = new Drive(
                Drive.CANJAGUAR_FRONT_LEFT_ID, Drive.CANJAGUAR_FRONT_RIGHT_ID,
                Drive.CANJAGUAR_BACK_LEFT_ID, Drive.CANJAGUAR_BACK_RIGHT_ID);
        shooter = new Shooter(vision);
        controlSystem = new ControlSystem(
                ControlSystem.JOYSTICK_LEFT_DRIVE_PORT,
                ControlSystem.JOYSTICK_RIGHT_DRIVE_PORT,
                ControlSystem.JOYSTICK_OPERATOR_PORT);
    }
    
    /**
     * This function is called at the beginning of autonomous.
     */
    public void autonomousInit() {
        Logger.log("Autonomous has begun!");
    }

    /**
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic() {

    }
    
    /**
     * This function is called at the beginning of operator control.
     */
    public void teleopInit() {
        Logger.log("Teleop has begun!");
    }
    
    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called at the beginning of disabled mode.
     */
    public void disabledInit() {
        Logger.log("The robot has been disabled :(");
    }
    
    /**
     * This function is called at the beginning of test mode.
     */
    public void testInit() {
        Logger.log("Test mode has begun.");
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() {
    
    }
    
    /**
     * Setup Network Tables, and get the NetworkTable for the SmartDashboard.
     * @return The network table for the SmartDashboard.
     */
    private NetworkTable getNetworkTable() {
        NetworkTable.setTeam(1899);
        NetworkTable.setServerMode();
        try {
            NetworkTable.initialize();
        }
        catch (Exception exception) {
            Logger.log(exception);
        }
        
        return NetworkTable.getTable("SmartDashboard");
    }
}
