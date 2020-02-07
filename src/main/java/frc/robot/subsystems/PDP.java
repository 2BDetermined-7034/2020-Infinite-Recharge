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
    SmartDashboard.putNumber("Right1", getCurrent(Constants.PDPdriveR1));
    SmartDashboard.putNumber("Right2", getCurrent(Constants.PDPdriveR2));
    SmartDashboard.putNumber("Right3", getCurrent(Constants.PDPdriveR3));
    SmartDashboard.putNumber("Left1", getCurrent(Constants.PDPdriveL1));
    SmartDashboard.putNumber("Left2", getCurrent(Constants.PDPdriveL2));
    SmartDashboard.putNumber("Left3", getCurrent(Constants.PDPdriveL3));
    SmartDashboard.putNumber("Intake", getCurrent(Constants.PDPintake));
    SmartDashboard.putNumber("EEEEEEEE", m_pdp.getTotalEnergy());
  }
}
