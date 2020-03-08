/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class Drive extends CommandBase {

  private final Drivetrain m_dt;

  private final DoubleSupplier m_driveY;
  private final DoubleSupplier m_driveX;

  private BooleanSupplier m_invert;
  private boolean inverted;

  public Drive(Drivetrain dt, DoubleSupplier Y, DoubleSupplier X, BooleanSupplier invert) {
    m_dt = dt;
    m_driveY = Y;
    m_driveX = X;
    m_invert = invert;
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_dt.setGear(Constants.HIGH_GEAR);
    inverted = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_invert.getAsBoolean()) { inverted = !inverted; }
    m_dt.drive((inverted ? 1 : -1) * m_driveY.getAsDouble(), m_driveX.getAsDouble()); 
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
