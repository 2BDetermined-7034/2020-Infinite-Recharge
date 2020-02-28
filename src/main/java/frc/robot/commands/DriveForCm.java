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

public class DriveForCm extends CommandBase {
  private final double m_cm;
  private final Drivetrain m_dt;

  public DriveForCm(Drivetrain dt, double cm) {
    m_dt = dt;
    m_cm = cm;
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_dt.setEncoders(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_dt.driveFor(m_cm);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_dt.getPositionL() - m_cm) < Constants.DT_pidPositionTolearance
      && m_dt.getAbsoluteVelocity() < Constants.DT_pidVelocityTolearance;
  }
}
