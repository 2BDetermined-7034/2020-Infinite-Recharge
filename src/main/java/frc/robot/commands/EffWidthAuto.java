/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class EffWidthAuto extends CommandBase {

  private double targetAngle;
  private double encoderOffsetL;
  private double encoderOffsetR;
  private double timeStop = 999;
  private final int rotationNumber = 5;
  private double initialAngle;

  private final Drivetrain m_dt;

  private Timer timer;

  public EffWidthAuto(Drivetrain dt) {
    m_dt = dt;
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = new Timer();
    timer.start();
    timeStop = 999;
    initialAngle = m_dt.getAngle();
    targetAngle = initialAngle + (rotationNumber * 360);
    encoderOffsetL = m_dt.getPositionL();
    encoderOffsetR = m_dt.getPositionR();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timeStop == 999) {
      m_dt.autoDrive(.5, -.5);
      if(m_dt.getAngle() >= targetAngle) {
        timeStop = timer.get() + 1;
      }
    }
    else {
      m_dt.autoDrive(0, 0);
    }
    SmartDashboard.putNumber(getName() + " Degrees Turned", m_dt.getAngle()-initialAngle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (!interrupted) {
      double degrees = m_dt.getAngle() - initialAngle;
      double L = (m_dt.getPositionL() - encoderOffsetL);
      double R = (m_dt.getPositionR() - encoderOffsetR);
      System.out.println("Effective Width is " + (180*(L+R)) / (-1*degrees*Math.PI) + " in");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() >= timeStop;
  }
}

