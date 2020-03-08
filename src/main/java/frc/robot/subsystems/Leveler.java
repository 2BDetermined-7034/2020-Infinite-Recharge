/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Leveler extends SubsystemBase {
  private CANSparkMax m_trolley;
  private CANEncoder m_trolleyEnc;

  /**
   * Creates a new Leveler.
   */
  public Leveler() {
    //Initialization
    m_trolley = new CANSparkMax(Constants.IDlevelerTrolley, MotorType.kBrushless);
    m_trolleyEnc = m_trolley.getEncoder();

    //Configuration
    m_trolley.setIdleMode(IdleMode.kBrake);

    m_trolley.setSmartCurrentLimit(40);

    m_trolleyEnc.setPositionConversionFactor(Constants.Level_InchesPerRot);
  }

  public double set(double power) {
    double p = Shortcuts.bound(power, 1);
    m_trolley.set(p);
    return p;
  }

  public void stop() {
    m_trolley.stopMotor();
  }

  public double getTrolleyPosition() {
    return m_trolleyEnc.getPosition();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Trolley Position", getTrolleyPosition());
  }
}
