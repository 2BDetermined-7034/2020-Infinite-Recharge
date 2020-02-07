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

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonSRX leftSide;
  private final WPI_TalonSRX rightSide;

  public Shooter() {
    leftSide = new WPI_TalonSRX(Constants.IDshooterL);
    rightSide = new WPI_TalonSRX(Constants.IDshooterR);

    rightSide.setInverted(true);
    leftSide.setInverted(true);
  }

  public void set(double power) { set(power, power); }
  public void set(double powerL, double powerR) { 
    leftSide.set(Shortcuts.bound(powerL, 1));
    rightSide.set(Shortcuts.bound(powerR, 1));
  }

  public void stop() { leftSide.stopMotor(); rightSide.stopMotor(); }

  public double getLThrottle() { return leftSide.get(); }
  public double getRThrottle() { return rightSide.get(); }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Throttle", getLThrottle());
    SmartDashboard.putNumber("Right Throttle", getRThrottle());
  }
}
