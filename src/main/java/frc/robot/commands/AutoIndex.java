/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class AutoIndex extends CommandBase {
  private final Indexer m_index;
  private int m_stepper;
  private int m_ballCount;
  private int m_timer;

  /**
   * Creates a new AutoIndex.
   */
  public AutoIndex(Indexer indexer) {
    m_index = indexer;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stepper = 0;
    m_ballCount = 0;
    m_timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Ball Counter", m_ballCount);
    if(m_stepper == 0 && m_index.getIntakeCurrent() > 6) {
      m_ballCount++;
      m_stepper = 1;
    }
    if(m_stepper == 1) {
      m_timer++;
      if(m_timer > 50) {
        m_stepper = 0;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
