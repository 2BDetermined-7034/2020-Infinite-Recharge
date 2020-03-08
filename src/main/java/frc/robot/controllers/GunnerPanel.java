/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

/**
 * Add your docs here.
 */
public class GunnerPanel extends EpicHID {

    public static final String[] buttonNames = {"limemode", "armstoggle", "intakeretract", "winchup", "winchdown", "intakein", "intakeout", "fire", "indexout", "indexin", "elevatordown", "elevatorup"};
    public static final String[] axisNames = {"pitch", "speed", "arms"};

    public GunnerPanel(final int port) {
        super(port, buttonNames, axisNames);
    }

    public double getIndexerHorizontalPower() {
        return getJoystickButton("indexin").get() ? 0.75 : getJoystickButton("indexout").get() ? -0.75 : 0; 
    }
    
    public double getIndexerElevatorPower() {
        return getJoystickButton("elevatorup").get() ? 0.75 : getJoystickButton("elevatordown").get() ? -0.75 : 0;
    }
}
