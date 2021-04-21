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

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Add your docs here.
 */
public class EpicHID extends GenericHID {

    private ArrayList<String> m_digitalInputs;
    private Map<String, JoystickButton> m_joyButtons;
    private ArrayList<String> m_analogInputs;

    public EpicHID(int port, String[] digitalNames, String[] analogNames) {
        super(port);
        m_joyButtons = new HashMap<>();

        m_digitalInputs = new ArrayList<>(Arrays.asList(digitalNames));
        m_analogInputs = new ArrayList<>(Arrays.asList(analogNames));

        for(int i = 0; i < digitalNames.length; i++) {
            m_joyButtons.put(digitalNames[i], new JoystickButton(this, i+1));
        }
    }

    public JoystickButton getJoystickButton(String name) {
        return m_joyButtons.get(name.toUpperCase());
    }
    public JoystickButton getJoystickButton(int id) {
        return getJoystickButton(m_digitalInputs.get(id-1));
    }

    public boolean getRawButton(String name) {
        return getRawButton(m_digitalInputs.indexOf(name.toUpperCase())+1);
    }

    public double getAxis(String name) {
        return getRawAxis(m_analogInputs.indexOf(name.toUpperCase()));
    }

    
    public double getAxisPercent(String name) {
        return (1 + getAxis(name)) / 2;
    }

    public double getY(Hand hand) {
        return 0;
    }

    public double getX(Hand hand) {
        return 0;
    }

}
