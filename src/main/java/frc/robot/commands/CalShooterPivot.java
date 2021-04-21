/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Shooter;

public class CalShooterPivot extends CommandBase {

  private Shooter m_shooter;

  private double power;

  private double targetV = -1000;

  /**
   * Creates a new RetractShooter.
   */
  public CalShooterPivot(Shooter shooter) {
    m_shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.stopWheels();
    power = m_shooter.getPivotOutput();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double v = m_shooter.getPivotVelocity();
    power += Shortcuts.bound(0.0002*(targetV-v), 0.002);
    m_shooter.setPivot(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopPivot();
    if(!interrupted) {
      Shortcuts.print("PIVOT RESET");
      m_shooter.setPivotEncoder(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.getPivotCurrent() >= Constants.ShootPiv_SoftCurrentLimit;
  }
}