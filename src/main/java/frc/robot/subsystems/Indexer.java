/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Indexer extends SubsystemBase {
  
  private final WPI_TalonSRX m_intake;
  private final WPI_TalonSRX m_front;
  private final WPI_TalonSRX m_back;
  private final Solenoid m_intakePivot;

  public Indexer() {
    m_intake = new WPI_TalonSRX(Constants.IDindexerIntake);
    m_front = new WPI_TalonSRX(Constants.IDindexerFront);
    m_back = new WPI_TalonSRX(Constants.IDindexerBack);

    m_back.setInverted(true);

    m_intakePivot = new Solenoid(Constants.IDintakeExtender);
  }

  public double setIntake(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_intake.set(s);
    return s;
  }

  public double setFront(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_front.set(s);
    return s;
  }

  public double setBack(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_back.set(s);
    return s;
  }

  public double getIntakeCurrent() {
    return m_intake.getSupplyCurrent();
  }

  public void deployIntake(boolean state) {
    m_intakePivot.set(state);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Intake Output", m_intake.getMotorOutputPercent());
    SmartDashboard.putNumber(getName() + " Front Output", m_front.getMotorOutputPercent());
    SmartDashboard.putNumber(getName() + " Back Output", m_back.getMotorOutputPercent());
  }
}
