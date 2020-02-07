/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Indexer extends SubsystemBase {
  
  private final WPI_TalonSRX m_intake;

  public Indexer() {
    m_intake = new WPI_TalonSRX(Constants.IDintake);
  }

  public double setIntake(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_intake.set(s);
    return s;
  }

  public double getIntakeCurrent() {
    return m_intake.getSupplyCurrent();
  }

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Talon Current", getIntakeCurrent());
  }
}
