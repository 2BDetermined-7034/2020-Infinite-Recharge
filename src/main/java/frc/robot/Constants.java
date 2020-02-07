/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //talonSRX
    public static final int IDshooterL = 0;
    public static final int IDshooterR = 1;

    public static final int IDintake = 1;

    public static final int[] IDclimber = {2, 3};
  
    //sparkMAX
    public static final int IDdriveR1 = 1;
    public static final int IDdriveR2 = 2;
    public static final int IDdriveR3 = 3;
    public static final int IDdriveL1 = 4;
    public static final int IDdriveL2 = 5;
    public static final int IDdriveL3 = 6;

    public static final int IDarm1 = 1;
    public static final int IDarm2 = 2;
  
    //controllers
    public static final int IDjoystick = 0;
    public static final int IDgamepad = 1;
  
    //pneumatics
    public static final int IDcompressor = 0; 
    public static final int[] IDshifter = {0, 1};
  
    //sensors

    //PDP
    public static final int PDPdriveR1 = 0;
    public static final int PDPdriveR2 = 1;
    public static final int PDPdriveR3 = 2;
    public static final int PDPintake = 10;
    public static final int PDPdriveL1 = 15;
    public static final int PDPdriveL2 = 14;
    public static final int PDPdriveL3 = 13;

    //Vision Allignment
    public static double VisX_kP = 1;
    public static double VisX_MAX = .3;
    public static double VisTimeLimit = 5;
    public static double VisX_Offset = 0;
    public static double VisX_Tol = 0;
    public static double VisY_Tol = 5;

    //Indexer
    public static double DexIntake_CurrentThreshold = 25;
}
