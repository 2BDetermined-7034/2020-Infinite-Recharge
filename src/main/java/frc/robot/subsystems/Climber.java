/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Climber extends SubsystemBase {

  private final CANSparkMax m_arm;
  private final CANPIDController m_armPID;
  private final CANEncoder m_armEnc;

  private final CANSparkMax m_winch;
  private final DoubleSolenoid m_brake;

  public Climber() {
    m_arm = new CANSparkMax(Constants.IDarm, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_armPID = m_arm.getPIDController();
    m_armEnc = m_arm.getEncoder();

    m_arm.setSmartCurrentLimit(Constants.Arm_HardCurrentLimit);

    m_arm.setIdleMode(IdleMode.kBrake);
    m_armEnc.setPositionConversionFactor(Constants.Arm_DegreesPerRot);
    m_armEnc.setPosition(0);

    setArmP(Constants.Arm_kP);
    setArmI(Constants.Arm_kI);
    setArmD(Constants.Arm_kD);

    m_armPID.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);
    m_armPID.setSmartMotionMaxVelocity(Constants.Arm_MaxVelocity, 0);
    m_armPID.setOutputRange(-Constants.Arm_MaxOutput, Constants.Arm_MaxOutput);
    m_arm.setInverted(true);

    m_winch = new CANSparkMax(Constants.IDwinch, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_winch.setSmartCurrentLimit(Constants.Winch_HardCurrentLimit);
    m_winch.setInverted(true);
    m_winch.setIdleMode(IdleMode.kBrake);

    m_brake = new DoubleSolenoid(Constants.IDwinchBrake[0], Constants.IDwinchBrake[1]);
  }

  public double getArmOutput() { return m_arm.getAppliedOutput(); }
  public double getArmPosition() { return m_armEnc.getPosition(); }
  public double getArmVelocity() { return m_armEnc.getVelocity(); }
  public double getArmTemperature() { return m_arm.getMotorTemperature(); }
  public double getArmCurrent() { return m_arm.getOutputCurrent(); }

  public double getWinchOutput() { return m_winch.getAppliedOutput(); }
  public double getWinchCurrent() { return m_winch.getOutputCurrent(); }

  public void setArmP(double kP) { m_armPID.setP(kP, 0); }
  public void setArmI(double kI) { m_armPID.setI(kI, 0); }
  public void setArmD(double kD) { m_armPID.setD(kD, 0); }

  public double setArm(double power) {
    double p = Shortcuts.bound(power, 1);
    m_arm.set(p);
    return p;
  }

  public void setArmEncoder(double correctAngle) {
    m_armEnc.setPosition(correctAngle);
  }

  /**
   * Sets the target position in degrees for the arm. Does not have bounds, so be careful. Includes gravity compensation.
   * @param angle in degrees
   * @return
   */
  public CANError setArmTarget(double angle) {
    double gravityCompensation = Constants.Arm_kGrav * Math.cos(Math.toRadians(getArmPosition()));
    return m_armPID.setReference(angle, ControlType.kPosition, 0, gravityCompensation, ArbFFUnits.kPercentOut);
  }

  /**
   * Same as setArmTarget, but with bounds. Go crazy. Go stupid.
   * @param angle in degrees.
   * @return The bounded angle.
   */
  public double setArmTargetWithBounds(double angle) {
    angle = MathUtil.clamp(angle, Constants.Arm_MinAngle, Constants.Arm_MaxAngle);
    setArmTarget(angle);
    return angle;
  }

  /**
   * Sets the target velocity for the Arm. Use this for calibration.
   * @param targetV I'm gonna be honest, I don't know the units.
   * @return
   */
  public CANError setArmVelocityTarget(double targetV) {
    return m_armPID.setReference(targetV, ControlType.kVelocity);
  }

  //winch power
  public double setWinch(double power) {
    double p = Shortcuts.bound(power, 1);
    m_winch.set(p);
    return p;
  }

  public void setBrake(boolean broken) {
    m_brake.set(broken ? Value.kReverse : Value.kForward);
  }

  public void stopArm() { m_arm.stopMotor(); }

  public void stopWinch() { m_winch.stopMotor(); }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Arm Output", getArmOutput());
    SmartDashboard.putNumber(getName() + " Arm Position", getArmPosition());
    SmartDashboard.putNumber(getName() + " Arm Temperature", getArmTemperature());
  }
}
