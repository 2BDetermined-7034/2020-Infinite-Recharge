/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLight extends SubsystemBase {
  
  private final NetworkTable m_table;

  public LimeLight() {
    m_table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public double getXAngle() { return m_table.getEntry("tx").getDouble(0); }
  public double getYAngle() { return m_table.getEntry("ty").getDouble(0); }
  public double getArea() { return m_table.getEntry("ta").getDouble(0); }
  public double getShortSide() { return m_table.getEntry("tshort").getDouble(0); }
  public double getLongSide() { return m_table.getEntry("tlong").getDouble(0); }
  public boolean getDetected() { return m_table.getEntry("tv").getDouble(0) > 0; }
  public double getLastDetected() { return m_table.getEntry("tv").getLastChange(); }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " X Angle", getXAngle());
    SmartDashboard.putNumber(getName() + " Y Angle", getYAngle());
    SmartDashboard.putBoolean(getName() + " Detected", getDetected());
  }
}
