/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class RaiseArm extends CommandBase {

  private final Climber m_climb;
  private double m_target;
  
  public RaiseArm(Climber climb) {
    m_climb = climb;
    addRequirements(climb);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_target = m_climb.getArmPosition();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_target = m_target+1;
    m_climb.setArmTargetWithBounds(m_target);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_climb.getArmPosition()<= Constants.Arm_MaxAngle;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_climb.stopArm();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
 // @Override
 // public void interrupted() {
 // }
}
