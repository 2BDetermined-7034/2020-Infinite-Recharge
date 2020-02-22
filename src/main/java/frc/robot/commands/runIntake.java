/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class runIntake extends CommandBase {
  
  private final Indexer m_in;
  private final DoubleSupplier m_speed;
  private final BooleanSupplier m_reverse;

  public runIntake(Indexer in, DoubleSupplier speed, BooleanSupplier reverse) {
    m_in = in;
    m_speed = speed;
    m_reverse = reverse;
    addRequirements(in);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean r = m_reverse.getAsBoolean();
    //r = m_in.getIntakeCurrent() > Constants.DexIntake_CurrentThreshold ? true : r;
    double s = m_speed.getAsDouble();
    m_in.setIntake(r ? -s : s);

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_in.setIntake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
