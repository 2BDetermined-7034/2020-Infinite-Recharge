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
import frc.robot.Constants;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Shooter;

public class MoveArm extends CommandBase {
  
  private final Climber m_climb;
  private DoubleSupplier m_desiredTarget;
  private Shooter m_shooter;
  private double target;

  public MoveArm(Climber climb, Shooter shooter, DoubleSupplier target) {
    m_climb = climb;
    m_desiredTarget = target;
    m_shooter = shooter;
    addRequirements(climb);
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setPivotTarget(90);
    target = m_climb.getArmPosition();
    //m_target = m_climb.getArmPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean shooterReady = m_shooter.getPivotPosition() > 80;
    if(shooterReady) {
      target = Shortcuts.squeeze(m_desiredTarget.getAsDouble(), Constants.Arm_MinAngle, Constants.Arm_MaxAngle);
    }
    m_climb.setArmTargetWithBounds(target);
    //new may need testing
    //m_climb.setWinch(m_i.getAsDouble()*.75);
    SmartDashboard.putNumber(getName() + " Target", target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stopArm();
    //nwe may need tested
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() { return m_climb.getArmTemperature() > 60; }
}
