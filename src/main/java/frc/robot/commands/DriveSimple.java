/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveSimple extends CommandBase {

  private final Drivetrain m_dt;

  private final DoubleSupplier m_driveY;
  private final DoubleSupplier m_driveX;

  public DriveSimple(Drivetrain dt, DoubleSupplier Y, DoubleSupplier X) {
    this.m_dt = dt;
    this.m_driveY = Y;
    this.m_driveX = X;
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_dt.drive(-m_driveY.getAsDouble(), m_driveX.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    m_dt.autoDrive(0, 0); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() { return false; }
}
