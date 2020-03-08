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
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Shooter;

public class ManualPivot extends CommandBase {
  
  private final Shooter m_shoot;

  private final DoubleSupplier m_percent;

  public ManualPivot(Shooter shooter, DoubleSupplier percent) {
    m_shoot = shooter;
    m_percent = percent;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double p = MathUtil.clamp(m_percent.getAsDouble(), 0, 1);
    double t = Shortcuts.squeeze(p, Constants.ShootPiv_MinAngle, Constants.ShootPiv_MaxAngle);

    m_shoot.setPivotTarget(t, false);

    SmartDashboard.putNumber(getName() + " Target", t);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shoot.getPivotTemp() > 60;
  }
}
