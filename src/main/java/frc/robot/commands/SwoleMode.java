/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class SwoleMode extends CommandBase {
  private Drivetrain m_dt;

  /**
   * Creates a new SwoleMode.
   */
  public SwoleMode(Drivetrain dt) {
    m_dt = dt;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_dt.setGear(Constants.LOW_GEAR);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_dt.setGear(Constants.HIGH_GEAR);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
