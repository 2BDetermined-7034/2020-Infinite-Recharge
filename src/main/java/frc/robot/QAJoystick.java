/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class QAJoystick extends Joystick {

    public QAJoystick(final int port) {
        super(port);
    }

    
    public double QAgetX() {
        return 0;
    }
    public double QAgetY() {
        return 0;
    }
    public double QAgetTwist() {
        return 0;
    }
    public double QAgetThrottle() {
        return 0;
    }
    public boolean getRawButtonPressed(int button) {
        return false;
    }

}
