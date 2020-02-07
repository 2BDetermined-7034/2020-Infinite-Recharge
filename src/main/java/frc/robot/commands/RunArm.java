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
import frc.robot.Shortcuts;
import frc.robot.subsystems.NeoTest;

public class RunArm extends CommandBase {
  
  private final NeoTest m_nt;
  private final DoubleSupplier p1;
  private final DoubleSupplier p2;

  public RunArm(NeoTest nt, DoubleSupplier power1, DoubleSupplier power2) {
    m_nt = nt;
    addRequirements(nt);
    p1 = power1;
    p2 = power2;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shortcuts.print(getName() + " command init");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double target = (p1.getAsDouble()+p2.getAsDouble())*7;
    //double target = p.getAsDouble()*5000;
    //m_nt.setVelocity(target);
    m_nt.setPosition(target);
    //m_nt.set(target);
    SmartDashboard.putNumber("Neo Target", target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_nt.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() { return false; }
}
