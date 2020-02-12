/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;

public class Drive extends CommandBase {

  private double RPM;

  private final Drivetrain m_dt;

  private final DoubleSupplier m_driveY;
  private final DoubleSupplier m_driveX;

  public Drive(Drivetrain dt, DoubleSupplier Y, DoubleSupplier X) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.m_dt = dt;
    this.m_driveY = Y;
    this.m_driveX = X;
    addRequirements(dt);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    RPM = Constants.IDarm2;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if(Drivetrain.getAverageRPM() >= RPM){
      Drivetrain.setGear(Constants.ShiftHigh);
      m_dt.drive2(-m_driveY.getAsDouble()/Constants.Shift_spread, m_driveX.getAsDouble());
      RPM = Constants.Low_RPM;
    }
    else{
      Drivetrain.setGear(Constants.ShiftLow);
      m_dt.drive2(-m_driveY.getAsDouble(), m_driveX.getAsDouble());
      RPM = Constants.Low_RPM;
    }
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_dt.autoDrive(0, 0); 
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }
}
