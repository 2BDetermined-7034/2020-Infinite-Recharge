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
import frc.robot.subsystems.Indexer;

public class RunIndexer extends CommandBase {
  
  private final Indexer m_in;
  private DoubleSupplier m_back;
  private DoubleSupplier m_front;

  public RunIndexer(Indexer in, DoubleSupplier front, DoubleSupplier back) {
    m_in = in;
    m_front = front;
    m_back = back;
    addRequirements(in);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putNumber("Indexer Testing Elev", .5);
    //SmartDashboard.putNumber("Indexer Testing Conv", .5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_in.setConveyor(m_front.getAsDouble());
    m_in.setElevator(m_back.getAsDouble());
    //m_in.setConveyor(SmartDashboard.getNumber("Indexer Testing Conv", .5));
    //m_in.setElevator(SmartDashboard.getNumber("Indexer Testing Elev", .5));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_in.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
