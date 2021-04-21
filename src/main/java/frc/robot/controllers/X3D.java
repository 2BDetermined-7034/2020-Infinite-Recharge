/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

public class X3D extends EpicHID {

    public static final String[] button = {"TRIGGER","2","3","4","5","6","7","8","9","10","11","12"};
    public static final String[] axisNames = {"X","Y","TWIST","THROTTLE"};

    public X3D(int port) {
        super(port, button, axisNames);
    }

    public double getX(Hand hand) {
        return getAxis("X");
    }
    public double getY(Hand hand) {
        return getAxis("Y");
    }
}
