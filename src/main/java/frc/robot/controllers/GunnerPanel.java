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

    public static final String[] buttonNames = {"LIMEMODE", "ARMSWITCH", "INTAKESWITCH", "WINCHUP", "WINCHDOWN", "INTAKEIN", "INTAKEOUT", "SHOOT", "CONVEYOROUT", "CONVEYORIN", "ELEVATORDOWN", "ELEVATORUP"};
    public static final String[] axisNames = {"PITCH", "SPEED", "ARM"};

    public GunnerPanel(final int port) {
        super(port, buttonNames, axisNames);
    }
}
