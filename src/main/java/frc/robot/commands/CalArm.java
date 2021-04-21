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
import frc.robot.subsystems.Climber;

public class CalArm extends CommandBase {

  private Climber m_climber;

  private double power;

  private double targetV = -50;

  /**
   * Creates a new RetractShooter.
   */
  public CalArm(Climber climber) {
    m_climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    power = m_climber.getArmOutput();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double v = m_climber.getArmVelocity();
    power += Shortcuts.bound(0.00001*(targetV-v), 0.002);
    m_climber.setArm(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.stopArm();
    if(!interrupted) {
      Shortcuts.print("ARM RESET");
      m_climber.setArmEncoder(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_climber.getArmCurrent() >= Constants.Arm_SoftCurrentLimit;
  }
}