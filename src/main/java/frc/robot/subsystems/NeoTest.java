/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class NeoTest extends SubsystemBase {

  private final CANSparkMax m_neo;
  private final CANPIDController m_pidControl;
  private final CANEncoder m_encoder;

  private int m_pidSlot = 0;

  private double velP = 0.0006;

  public NeoTest() {
    m_neo = new CANSparkMax(Constants.IDarm1, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_pidControl = m_neo.getPIDController();
    m_encoder = m_neo.getEncoder();

    m_neo.setSmartCurrentLimit(100);

    m_pidControl.setP(SmartDashboard.getNumber(getName() + " kP", .1));
    m_pidControl.setI(SmartDashboard.getNumber(getName() + " kI", 0));
    m_pidControl.setD(SmartDashboard.getNumber(getName() + " kD", 0));
    m_pidControl.setFF(SmartDashboard.getNumber(getName() + " kFF", 0));
    m_pidControl.setOutputRange(-1, 1);
    // m_pidControl.setSmartMotionMaxVelocity(200, 0);

    SmartDashboard.putNumber(getName() + " kP", m_pidControl.getP());
    SmartDashboard.putNumber(getName() + " kI", m_pidControl.getI());
    SmartDashboard.putNumber(getName() + " kD", m_pidControl.getD());
    SmartDashboard.putNumber(getName() + " kFF", m_pidControl.getFF());
    // SmartDashboard.putNumber(getName() + " Max Output",
    // m_pidControl.getOutputMax());

    //CANSparkMax m_neo2 = new CANSparkMax(Constants.IDarm2, CANSparkMaxLowLevel.MotorType.kBrushless);
    //m_neo2.follow(m_neo);

    Shortcuts.print(getName() + " subsystem init");
  }

  public double set(double power) {
    m_neo.set(power);
    return m_neo.getAppliedOutput();
  }

  public void stop() {
    m_neo.stopMotor();
  }

  public void reset() {
    m_neo.getEncoder().setPosition(0);
  }

  public double getEncoderPosition() {
    return m_encoder.getPosition();
  }

  public double getEncoderVelocity() {
    return m_encoder.getVelocity();
  }

  public void setPosition(double rotations) {
    m_pidControl.setReference(rotations, ControlType.kPosition);
  }

  public void setVelocity(double rpm) {
    m_pidControl.setReference(rpm, ControlType.kVelocity);
  }

  public void updateP() {
    m_pidControl.setP(SmartDashboard.getNumber(getName() + " kP", 0));
  }

  public void updateI() {
    m_pidControl.setI(SmartDashboard.getNumber(getName() + " kI", 0));
  }

  public void updateD() {
    m_pidControl.setD(SmartDashboard.getNumber(getName() + " kD", 0));
  }

  public void updateMaxOutput() {
    double x = SmartDashboard.getNumber(getName() + " Max Output", 0);
    m_pidControl.setOutputRange(-x, x);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("NeoEncoderPos", getEncoderPosition());
    SmartDashboard.putNumber("NeoEncoderVel", m_encoder.getVelocity());
    SmartDashboard.putNumber("NeoOutput", m_neo.getAppliedOutput());
    SmartDashboard.putNumber("NeoCurrent", m_neo.getOutputCurrent());
    SmartDashboard.putNumber("NeoTemp", m_neo.getMotorTemperature());
  }
}
