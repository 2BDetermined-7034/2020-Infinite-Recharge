/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpilibj.Sendable;
/**
 * Add your docs here.
 */
public class Shortcuts {
    public static void putVars(String[] keys, Object[] vars) {
        for(int i = 0; i < keys.length && i < vars.length; i++) {
            putVar(keys[i], vars[i]);
        }
    }

    public static void putVar(String key, Object var) {
        if (var instanceof Double) { SmartDashboard.putNumber(key, (double) var); }
        else if(var instanceof Boolean) { SmartDashboard.putBoolean(key, (boolean) var); }
        else if(var instanceof String) { SmartDashboard.putString(key, (String) var); }
        else if(var instanceof Sendable) { SmartDashboard.putData(key, (Sendable) var); }
    }

    public static Object getVar(String key, Object var) {
        if (var instanceof Double) { return SmartDashboard.getNumber(key, (double) var); }
        else if(var instanceof Boolean) { return SmartDashboard.getBoolean(key, (boolean) var); }
        else if(var instanceof String) { return SmartDashboard.getString(key, (String) var); }
        return null;
    }

    /**
     * Sets the variable to the value on the SmartDashboard. Will create the key if it doesn't exist.
     * @param key The SD key
     * @param var Variable to set, DOES NOT WORK FOR PRIMITIVE TYPES
     */
    public static Object updateVar(String key, Object var) {
        if (!SmartDashboard.containsKey(key)) { 
            putVar(key, var);
            return var;
        }
        else { return getVar(key, var); }
    }

    public static double bound(double x, double bound) { 
        return MathUtil.clamp(x, -bound, bound);
        // return (x > bound) ? bound : (x < -bound) ? -bound : x; 
    }

    public static double squeeze(double x, double low, double high) {
        return low + (high-low)*x;
    }

    /**
     * Nudges x towards the target with strength. Set x to the result.
     * @param x Number to be nudged
     * @param target Number that x is nudged towards
     * @param strength The strength with which to nudge x
     * @return The new x.
     */
    public static double nudge(double x, double target, double strength) {
        double n = 1/strength;
        return (n*x+target)/(n+1);
    }

    public static double deadZone(double x, double zone) { return (Math.abs(x) > zone) ? (x - Math.copySign(zone, x)) / (1.0 - zone) : 0.0; }

    public static void print(String str) {
        System.out.println("[DEBUG] " + str);
    }
}
