/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class gPad extends EpicHID {

    public static final String[] buttonNames = {"a","b","x","y","lb","rb","back","start","lsb","rsb"};
    public static final String[] axisNames = {"lx","ly","ltrigger","rtrigger","rx","ry"};

    public gPad(final int port) {
        super(port, buttonNames, axisNames);
    }
}