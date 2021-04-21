/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Indexer extends SubsystemBase {
  
  private final WPI_TalonSRX m_intake;
  private final WPI_TalonSRX m_hopper;
  private final WPI_TalonSRX m_conveyor;
  private final WPI_TalonSRX m_elevator;
  private final DoubleSolenoid m_intakePivot;

  public Indexer() {
    m_intake = new WPI_TalonSRX(Constants.IDindexerIntake);
    m_hopper = new WPI_TalonSRX(Constants.IDindexerHopper);
    m_conveyor = new WPI_TalonSRX(Constants.IDindexerConveyor);
    m_elevator = new WPI_TalonSRX(Constants.IDindexerElevator);

    m_intake.setInverted(false);
    m_hopper.setInverted(false);
    m_conveyor.setInverted(false);
    m_elevator.setInverted(true);
    
    m_intakePivot = new DoubleSolenoid(Constants.IDintakeForward, Constants.IDintakeReverse);
  }

  public double setIntake(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_intake.set(s);
    return s;
  }

  public double setHopper(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_hopper.set(s);
    return s;
  }

  public double setConveyor(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_conveyor.set(s);
    return s;
  }

  public double setElevator(double speed) {
    double s = Shortcuts.bound(speed, 1);
    m_elevator.set(s);
    return s;
  }

  public double getIntakeCurrent() {
    return m_intake.getSupplyCurrent();
  }

  public void deployIntake(boolean state) {
    m_intakePivot.set(state ? Value.kForward : Value.kReverse);
  }

  public void stop() {
    setIntake(0);
    setHopper(0);
    setConveyor(0);
    setElevator(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Intake Output", m_intake.getMotorOutputPercent());
    SmartDashboard.putNumber(getName() + " Hopper Output", m_hopper.getMotorOutputPercent());
    SmartDashboard.putNumber(getName() + " Conveyor Output", m_conveyor.getMotorOutputPercent());
    SmartDashboard.putNumber(getName() + " Elevator Output", m_elevator.getMotorOutputPercent());
  }
}
