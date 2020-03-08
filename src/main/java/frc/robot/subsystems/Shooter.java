/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Shooter extends SubsystemBase {

  private final WPI_TalonSRX m_wheelL;
  private final WPI_TalonSRX m_wheelR;
  private final CANSparkMax m_pivot;
  private final CANEncoder m_pivotEnc;
  private final CANPIDController m_pivotPID;

  public Shooter() {
    m_wheelL = new WPI_TalonSRX(Constants.IDshooterWheelL);
    m_wheelR = new WPI_TalonSRX(Constants.IDshooterWheelR);

    m_pivot = new CANSparkMax(Constants.IDshooterPivot, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_pivotEnc = m_pivot.getEncoder();
    m_pivotPID = m_pivot.getPIDController();

    m_wheelR.setInverted(false);
    m_wheelL.setInverted(false);
    m_wheelL.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 50, 50, .5));
    m_wheelR.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 50, 50, .5));

    m_wheelL.config_kP(0, Constants.ShootFly_kP);
    m_wheelR.config_kP(0, Constants.ShootFly_kP);

    m_pivot.setInverted(false);
    m_pivotEnc.setPositionConversionFactor(Constants.ShootPiv_DegreesPerRot);
    m_pivotPID.setP(Constants.ShootPiv_kP);
    m_pivotPID.setI(Constants.ShootPiv_kI);
    m_pivotPID.setD(Constants.ShootPiv_kD);
    m_pivotPID.setOutputRange(-Constants.ShootPiv_MaxOutput, Constants.ShootPiv_MaxOutput);
    m_pivot.setSmartCurrentLimit((int) Constants.ShootPiv_HardCurrentLimit);
  }

  //Getters for the flywheels
  public double getWheelsOutput() { return m_wheelL.get(); }
  public double getWheelsCurrent() { return m_wheelL.getSupplyCurrent(); }

  //Getters for the pivot
  public double getPivotOutput() { return m_pivot.getAppliedOutput(); }
  public double getPivotPosition() { return m_pivotEnc.getPosition(); }
  public double getPivotVelocity() { return m_pivotEnc.getVelocity(); }
  public double getPivotCurrent() { return m_pivot.getOutputCurrent(); }
  public double getPivotTemp() { return m_pivot.getMotorTemperature(); }
  
  //Setters for pivot PID controller
  public void setPivotP(double kP) { m_pivotPID.setP(kP); }
  public void setPivotI(double kI) { m_pivotPID.setI(kI); }
  public void setPivotD(double kD) { m_pivotPID.setD(kD); }

  //Sets the flywheels output
  public void setWheels(double output) {
    m_wheelR.set(Shortcuts.bound(output, 1));
    m_wheelL.set(Shortcuts.bound(output, 1));
  }

  public void setWheelsV(double output) {
    m_wheelR.set(ControlMode.Velocity, output*120000);
    m_wheelL.set(ControlMode.Velocity, output*-120000);
  }

  public void setPivot(double output) {
    m_pivot.set(Shortcuts.bound(output,1));
  }
  /**
   * Sets the target position in degrees for the pivot. Does not have bounds, so be careful. Includes gravity compensation.
   * @param targetAngle Target angle in degrees
   * @return
   */
  public CANError setPivotTarget(double angle, boolean useBounds) {
    if(useBounds) {
      angle = MathUtil.clamp(angle, Constants.ShootPiv_MinAngle, 
        Constants.ShootPiv_MaxAngle);
    }
    double gravityCompensation = Constants.ShootPiv_kGrav * 
      Math.cos(Math.toRadians(getPivotPosition()));
    return m_pivotPID.setReference(angle, ControlType.kPosition, 
      0, gravityCompensation, ArbFFUnits.kPercentOut);
  }

  public CANError setPivotTarget(double angle) {
    return setPivotTarget(angle, true);
  }

  /**
   * Sets the target velocity for the Pivot. Use this for calibration.
   * @param targetV I'm gonna be honest, I don't know the units.
   * @return
   */
  public CANError setPivotVelocityTarget(double targetV) {
    return m_pivotPID.setReference(targetV, ControlType.kVelocity);
  }

  public void setPivotEncoder(double correctAngle) {
    m_pivotEnc.setPosition(correctAngle);
  }

  public void stopWheels() {
    m_wheelR.stopMotor();
    m_wheelL.stopMotor();
  }

  public void stopPivot() {
    m_pivot.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Wheels Throttle", getWheelsOutput());
    SmartDashboard.putNumber(getName() + " Pivot Position", getPivotPosition());
    SmartDashboard.putNumber(getName() + " Pivot Output", getPivotOutput());
    SmartDashboard.putNumber(getName() + " Pivot Current", getPivotCurrent());
    SmartDashboard.putNumber(getName() + " Right Wheel Velocity", m_wheelR.getSelectedSensorVelocity());
    SmartDashboard.putNumber(getName() + " Left Wheel Velocity", m_wheelL.getSelectedSensorVelocity());
    SmartDashboard.putNumber(getName() + " Pivot Velocity", getPivotVelocity());
  }
}
