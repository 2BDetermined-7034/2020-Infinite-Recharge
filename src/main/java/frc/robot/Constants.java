/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    //talonSRX
    public static final int IDshooterWheels = 2;

    public static final int IDindexerIntake = 5;
    public static final int IDindexerFront = 4;
    public static final int IDindexerBack = 1;
  
    //sparkMAX
    public static final int IDdriveR1 = 1;
    public static final int IDdriveR2 = 2;
    public static final int IDdriveR3 = 3;
    public static final int IDdriveL1 = 4;
    public static final int IDdriveL2 = 5;
    public static final int IDdriveL3 = 6;

    public static final int IDshooterPivot = 9;

    public static final int IDarm = 10;
    public static final int IDwinch = 7;
    public static final int IDlevelerTrolley = 8;

    //controllers
    public static final int IDjoystick = 0;
    public static final int IDgamepad = 1;
  
    //pneumatics
    public static final int IDcompressor = 0;
    public static final int[] IDwinchBrake = {2, 3};
    public static final int IDshifter = 4;
    public static final int IDintakeExtender = 5;
    
    //sensors

    //PDP
    public static final int PDPdriveR1 = 0;
    public static final int PDPdriveR2 = 1;
    public static final int PDPdriveR3 = 2;
    public static final int PDParm = 3;

    public static final int PDPlevelerTrolley = 4;
    public static final int PDPshooterR = 5;
    public static final int PDPshooterL = 6;
    public static final int PDPlimeLight = 7;

    public static final int PDPshooterPivot = 8;
    public static final int PDPindexerBack = 9;
    public static final int PDPindexerFront = 10;
    public static final int PDPindexerIntake = 11;

    public static final int PDPwinch = 12;
    public static final int PDPdriveL3 = 13;
    public static final int PDPdriveL2 = 14;
    public static final int PDPdriveL1 = 15;

    //shooter
    public static final double ShootPiv_kP = 0.02;
    public static final double ShootPiv_kI = 0;
    public static final double ShootPiv_kD = 0;
    public static final double ShootPiv_kGrav = 0;
    public static final double ShootPiv_DegreesPerRot = 0.95;
    public static final double ShootPiv_MaxOutput = .4;
    public static final double ShootPiv_MaxAngle = 100;
    public static final double ShootPiv_MinAngle = 0;
    public static final double ShootPiv_PosThreshold = 2;
    public static final int ShootPiv_SoftCurrentLimit = 12;
    public static final int ShootPiv_HardCurrentLimit = 16;
    public static final double ShootFly_VkP = 1;
    
    //Vision Allignment
    public static final double VisX_kP = 1;
    public static final double VisX_MAX = .3;
    public static final double VisTimeLimit = 5;
    public static final double VisX_Offset = 0;
    public static final double VisX_Tol = 0;
    public static final double VisY_Tol = 5;
    public static final double VisY_distanceConstant = 254.3;
    public static final double VisY_Offset = 6;
    public static final double VisY_VTol = 100;
    public static final double VisX_VTol = 100;
    public static int Vis_TimerConfidence = 5;

    //Indexer
    public static final double IndexIntake_SoftCurrentLimit = 25;
    public static final double IndexIntake_HardCurrentLimit = 50;
    public static final double IndexIntake_Output = .75;

    //Drivetrain
    public static final double Shift_spread = 3.6;
    public static final boolean HIGH_GEAR = false;
    public static final boolean LOW_GEAR = true;
    public static final double ShiftRPMTriggerHigh = 5200;
    public static final double ShiftRPMTriggerLow = 1000;
    public static final double DT_kP = .03;
    public static final double DT_kI = 0;
    public static final double DT_kD = .1;
    public static final double DT_MaxOutput = 1;
    public static final double DT_cmPerRot = 6.86;

    public static final double DT_pidPositionTolearance = 1;
    public static final double DT_pidVelocityTolearance = 100;
    public static final int DT_SoftCurrentLimit = 20;
    public static final int DT_HardCurrentLimit = 40;
    public static final double DT_Deadzone = 0.15;


    //Arm
    public static final double Arm_MinAngle = 0;
    public static final double Arm_MaxAngle = 110;
    public static final double Arm_MaxOutput = .5;
    public static final int Arm_SoftCurrentLimit = 40;
    public static final int Arm_HardCurrentLimit = 60;
    public static final double Arm_DegreesPerRot = 7.667;
    public static final double Arm_kP = .05;
    public static final double Arm_kI = 0;
    public static final double Arm_kD = 0;
	public static final double Arm_kGrav = 0;
	public static final double Level_InchesPerRot = .01;
    public static final double Arm_MaxVelocity = 1;

    //Winch
    public static final int Winch_HardCurrentLimit = 60;
    
    //Conversions
    public static final double CM_PER_IN = 2.54;
}
