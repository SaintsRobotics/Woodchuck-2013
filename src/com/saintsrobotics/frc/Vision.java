package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * Implement vision targeting for the robot.
 * @author Saints Robotics
 */
public class Vision implements IRobotComponent
{
    private final NetworkTable table;
    
    private double distance;
    private double angle;
    
    public Vision(NetworkTable table)
    {
        this.table = table;
    }
    
    /**
     * Finds the distance away the goal is from the robot.
     * @return Distance from the goal
     */
    public double getDistance()
    {
        double distance = 0;
        
        try
        {
            //distance = table.getNumber("Distance");
        }
        catch (TableKeyNotDefinedException e)
        {
            Logger.log(e);
        }
        
        return distance;
    }
    
    /**
     * Find the angle that the robot is from the goal.
     * Zero degrees means the robot is perfectly centered with the goal.
     * @return Angle from the goal
     */
    public double getAngle()
    {
        double angle = 0;
        
        try
        {
            //angle = table.getNumber("Angle");
        }
        catch (TableKeyNotDefinedException e)
        {
            Logger.log(e);
        }
        
        return angle;
    }

    public void robotDisable()
    {
    }

    public void robotEnable()
    {
    }

    public void act()
    {
        distance = getDistance();
        angle = getAngle();
    }

    public void robotAuton()
    {
    }
}
