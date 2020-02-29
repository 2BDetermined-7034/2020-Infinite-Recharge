/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LimeLight;

public class VisAlignX extends CommandBase {

  private final Drivetrain m_dt;
  private final LimeLight m_ll;
  private final DoubleSupplier m_driveY;
  private final DoubleSupplier m_driveX;

  private boolean tapeDetected;
  private double gyroAngle;
  //private double gyroTarget;
  private double visionAngle;

  public VisAlignX(Drivetrain dt, LimeLight ll, DoubleSupplier Y, DoubleSupplier X) {
    m_dt = dt;
    m_ll = ll;
    m_driveY = Y;
    m_driveX = X;
    addRequirements(dt);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    //gyroTarget = m_dt.getAngle();
    visionAngle = 0;
    tapeDetected = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    gyroAngle = m_dt.getAngle();
    tapeDetected = m_ll.getDetected();
    double error;
    double turn;
    if (tapeDetected) {
      // if(Math.abs(visTable.getEntry("tx").getDouble(0)) > 1)
      visionAngle = m_ll.getXAngle() + Constants.VisX_Offset;
      error = visionAngle;
      //gyroTarget = gyroAngle + lastVision;

      //double area = m_ll.getArea();
      //double targetArea = 0.475;
      //double toleranceArea = 0.1;

      //autoSpeed = area > targetArea + toleranceArea ? 0.5 : area < targetArea - toleranceArea ? -0.5 : 0;
      turn = Shortcuts.bound(Constants.VisX_kP/Constants.VisX_MAX * error/40, Constants.VisX_MAX);
    } else {
      //angle = gyroTarget - gyroAngle;
      turn = 0;
    }

    m_dt.drive(-m_driveY.getAsDouble(), turn);
    SmartDashboard.putNumber("turn", turn);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_driveX.getAsDouble() > .5 || (tapeDetected && Math.abs(visionAngle) < Constants.VisX_Tol);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_dt.autoDrive(0, 0);
  }
}
*/
