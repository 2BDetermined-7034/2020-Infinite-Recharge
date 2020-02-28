/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class RetractShooter extends CommandBase {

  private Shooter m_shooter;

  /**
   * Creates a new RetractShooter.
   */
  public RetractShooter(Shooter shooter) {
    m_shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.stopWheels();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setPivot(-.7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopPivot();
    //m_shooter.setPivotEncoder(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.getPivotCurrent() >= Constants.ShootPiv_SoftCurrentLimit;
  }
}
