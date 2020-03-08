/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class SetArmPosition extends CommandBase {
  /**
   * Creates a new SetArmPosition.
   */

  private final Climber m_climb;
  private final DoubleSupplier m_target;
  private double m_currentTarget;

  public SetArmPosition(Climber climb, DoubleSupplier target) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    m_target = target;
    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_currentTarget = m_climb.getArmPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double target = m_target.getAsDouble();
    // If it's currently at its target, make it stay there
    if(m_climb.getArmPosition() == target) m_currentTarget = m_climb.getArmPosition();

    boolean runWinchUp = false;
    boolean runWinchDown = false;

    if(m_climb.getArmPosition() > target) {
      m_currentTarget -= 1;
      runWinchDown = true;
    } else {
      m_currentTarget += 1;
      runWinchUp = true;
    }
    m_climb.setArmTargetWithBounds(m_currentTarget);
    m_climb.setWinch(runWinchUp ? -.6 : runWinchDown ? .6 : 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stopArm();
    m_climb.stopWinch();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
