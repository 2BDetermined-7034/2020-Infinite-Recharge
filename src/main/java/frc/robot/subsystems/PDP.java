/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PDP extends SubsystemBase {

  private final PowerDistributionPanel m_pdp;

  public PDP() {
    m_pdp = new PowerDistributionPanel();
    m_pdp.clearStickyFaults();
  }

  public double getCurrent(int channel) {
    return m_pdp.getCurrent(channel);
  }

  @Override
  public void periodic() {
    
    SmartDashboard.putNumber("DriveR1 Current", getCurrent(Constants.PDPdriveR1));
    SmartDashboard.putNumber("DriveR2 Current", getCurrent(Constants.PDPdriveR2));
    SmartDashboard.putNumber("DriveR3 Current", getCurrent(Constants.PDPdriveR3));
    SmartDashboard.putNumber("DriveL1 Current", getCurrent(Constants.PDPdriveL1));
    SmartDashboard.putNumber("DriveL2 Current", getCurrent(Constants.PDPdriveL2));
    SmartDashboard.putNumber("DriveL3 Current", getCurrent(Constants.PDPdriveL3));
    /*
    SmartDashboard.putNumber("Intake", getCurrent(Constants.PDPindexerIntake));
    SmartDashboard.putNumber("Front Indexer", getCurrent(Constants.PDPindexerFront));
    SmartDashboard.putNumber("Back Indexer", getCurrent(Constants.PDPindexerBack));
    SmartDashboard.putNumber("LimeLight Current", getCurrent(Constants.PDPlimeLight));
    SmartDashboard.putNumber("Shooter Pivot Current", getCurrent(Constants.PDPshooterPivot));
    SmartDashboard.putNumber("ShooterR Current", getCurrent(Constants.PDPshooterR));
    SmartDashboard.putNumber("ShooterL Current", getCurrent(Constants.PDPshooterL));
    SmartDashboard.putNumber("Arm Current", getCurrent(Constants.PDParm));
    SmartDashboard.putNumber("Winch Current", getCurrent(Constants.PDPwinch));
    SmartDashboard.putNumber("Leveler Trolley Current", getCurrent(Constants.PDPlevelerTrolley));
    
    SmartDashboard.putNumber("Total Energy Used", m_pdp.getTotalEnergy());
    */
  }
}
