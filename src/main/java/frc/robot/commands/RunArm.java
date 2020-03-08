/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class RunArm extends CommandBase {
  
  private final Climber m_climb;
  private double m_target;
  private final DoubleSupplier m_i;

  public RunArm(Climber climb, DoubleSupplier increment) {
    m_climb = climb;
    m_i = increment;
    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_target = m_climb.getArmPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_target = m_target+m_i.getAsDouble();
    m_climb.setArmTargetWithBounds(m_target);
    SmartDashboard.putNumber(getName() + " Target", m_target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stopArm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() { return m_climb.getArmTemperature() > 60; }
}
