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
import frc.robot.Shortcuts;
import frc.robot.subsystems.Climber;

public class ToggleArm extends CommandBase {

  private final Climber m_climb;
  private final boolean m_up;
  private double m_target;
  
  public ToggleArm(Climber climb, boolean up) {
    m_climb = climb;
    m_up = up;
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
    m_target += m_up ? 3 : -2;
    m_climb.setArmTargetWithBounds(m_target);
    //m_climb.setWinch(m_up?-.6:.6);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    boolean test = m_up
      ? m_climb.getArmPosition() >= Constants.Arm_MaxAngle - 5
      : m_climb.getArmPosition() <= Constants.Arm_MinAngle + 5; 
      System.out.println(m_climb.getArmPosition());
      //return test;
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {    m_climb.stopWinch();
    m_climb.stopArm();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
 // @Override
 // public void interrupted() {
 // }
}
