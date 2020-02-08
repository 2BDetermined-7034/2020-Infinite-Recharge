/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Shooter;

public class runShooter extends CommandBase {
  
  private final Shooter m_shoot;

  private final DoubleSupplier m_power;
  private final DoubleSupplier m_spin;

  public runShooter(Shooter shooter, DoubleSupplier powerIn, DoubleSupplier spinIn) {
    m_shoot = shooter;
    m_power = powerIn;
    m_spin = spinIn;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double power = (1-m_power.getAsDouble())/2;
    double spin = m_spin.getAsDouble();
    spin = 0;
    m_shoot.set(power+spin, power-spin);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shoot.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}