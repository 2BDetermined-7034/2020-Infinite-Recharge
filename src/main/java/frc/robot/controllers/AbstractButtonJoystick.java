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

/**
 * Add your docs here.
 */
public abstract class AbstractButtonJoystick extends Joystick {

    private Map<Integer, JoystickButton> buttons;

    public AbstractButtonJoystick(final int port, int lowerBound, int upperBound) {
        super(port);
        buttons = new HashMap<>();
        for(int i = lowerBound; i <= upperBound; i++) {
            buttons.put(i, new JoystickButton(this, i));
        }
    }

    public JoystickButton getButton(int buttonID) {
        return buttons.get(buttonID);
    }

}
