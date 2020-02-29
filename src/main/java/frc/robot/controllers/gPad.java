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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class gPad extends Joystick {

    private Map<String, JoystickButton> buttons;
    private ArrayList<String> axes;

    public gPad(final int port) {
        super(port);
        buttons = new HashMap<>();
        String[] buttonNames = {"A","B","X","Y","LB","RB","BACK","START","LSB","RSB"};
        for(int i = 1; i <= 10; i++) {
            buttons.put(buttonNames[i-1], new JoystickButton(this, i));
        }
        axes = new ArrayList<>(Arrays.asList("LX","LY","LTrigger","RTrigger","RX","RY"));
    }

    public JoystickButton getButton(String buttonName) {
        return buttons.get(buttonName);
    }

    public double getAxis(String axisName) {
        return getRawAxis(axes.indexOf(axisName));
    }
}
