/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.EpicPID;
import frc.robot.Robot;
import frc.robot.subsystems.NeoDrivetrain;

public class TurnForDegrees extends CommandBase {

  private double angle;
  private double maxSpeed;

  private EpicPID pid;

  private NeoDrivetrain m_dt;
  private Timer time;

  public TurnForDegrees(NeoDrivetrain dt, double degrees, double maxSpeed) {
    this.angle = degrees;
    this.maxSpeed = maxSpeed;
    m_dt = dt;
    addRequirements(dt);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    time.reset();
    time.start();
    pid = new EpicPID(10, 0, 0, m_dt.getAngle()+angle, maxSpeed, 5);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double output = pid.calculate(m_dt.getAngle(), time.get());
    m_dt.autoDrive(output, -output);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return pid.isDone();
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interupted) {
    System.out.println("bruhEnd");
  }
}
*/

