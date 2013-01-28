package com.saintsrobotics.frc;

/**
 * Logs all of the warnings and errors while running the robot.
 * @author Saints Robotics
 */
public class Logger {
    private Logger() {}
    
    /**
     * Log the exception by converting the exception to a string.
     * @param exception
     */
    public static void log(Exception exception) {
        String message = exception.toString();
        Logger.log(message);
    }
    
    /**
     * Log the error messages by printing them out to the output panel.
     * @param message 
     */
    public static void log(String message) {
        System.out.println(message);
    }
}
