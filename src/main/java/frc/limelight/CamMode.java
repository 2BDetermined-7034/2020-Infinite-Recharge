/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.limelight;

/**
 * Add your docs here.
 */
public enum CamMode {
    VISION(0),
    DRIVERCAM(1);

    private final int mode;

    private CamMode(final int mode) {
        this.mode = mode;
    }

    public int getNetworkTableValue() {
        return mode;
    }

    public static CamMode getFromNetworkTableValue(int value) {
        return CamMode.values()[value];
    }
}
