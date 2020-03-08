/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class X3D extends Joystick {

    private Map<Integer, JoystickButton> buttons;

    public X3D(final int port) {
        super(port);
        buttons = new HashMap<>();
        for(int i = 1; i <= 12; i++) {
            buttons.put(i, new JoystickButton(this, i));
        }
    }

    public JoystickButton getButton(int buttonID) {
        return buttons.get(buttonID);
    }

}
