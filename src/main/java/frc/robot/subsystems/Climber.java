/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Shortcuts;

public class Climber extends SubsystemBase {
  
  private final WPI_TalonSRX m_gear;
  private final WPI_TalonSRX m_gear2;

  private int TOP = 100000;
  private int BOTTOM = -100000;

  public Climber() {
    m_gear = new WPI_TalonSRX(Constants.IDclimber[0]);
    m_gear2 = new WPI_TalonSRX(Constants.IDclimber[1]);
    m_gear2.follow(m_gear);
  }

  public double set(double power) {
    double p = Shortcuts.bound(power, 1);
    m_gear.set(ControlMode.PercentOutput, p);
    return p;
  }

  public int setPosition(int target) {
    int t = (int) MathUtil.clamp(target, BOTTOM, TOP);
    m_gear.set(ControlMode.Position, t);
    return t;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Zipper Encoder", m_gear.getSelectedSensorPosition());
  }
}
