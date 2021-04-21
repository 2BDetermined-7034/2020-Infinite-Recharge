/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Shortcuts;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

public class VisAlign extends CommandBase {

  private final Drivetrain m_dt;
  private final LimeLight m_ll;
  private final Shooter m_shoot;

  private BooleanSupplier m_vis;
  private BooleanSupplier m_interrupt;
  private DoubleSupplier m_drive;

  private boolean tapeDetected;

  private int tapeTimer;

  private double errorY;
  private double errorX;
  private double targetY;
  private double targetX_L;
  private double targetX_R;
  //private double distance;
  
  public VisAlign(Drivetrain dt, Shooter shooter, LimeLight ll, BooleanSupplier useVision, BooleanSupplier interrupt, DoubleSupplier drive) {
    m_dt = dt;
    m_ll = ll;
    m_shoot = shooter;
    m_vis = useVision;
    m_interrupt = interrupt;
    m_drive = drive;
    addRequirements(dt);
    addRequirements(shooter);
    addRequirements(ll);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    tapeDetected = false;
    tapeTimer = 0;
    //distance = m_ll.getEstimatedDistance();
    targetY = 45;
    targetX_L = m_dt.getPositionL();
    targetX_R = m_dt.getPositionR();
    errorY = 0;
    errorX = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_ll.setLights(true);
    boolean vis = m_vis.getAsBoolean();
    tapeDetected = m_ll.getDetected();
    double drive = m_drive.getAsDouble();
    boolean liteMode = Shortcuts.deadZone(drive, .1) != 0;

    if(tapeDetected) {
      tapeTimer++;
    }
    else {
      tapeTimer = 0;
    }

    if (tapeTimer >= Constants.Vis_TimerConfidence && vis) {
      double visY = m_ll.getYAngle();
      double visX = m_ll.getXAngle() + Constants.VisX_Offset;
      double visA = m_ll.getArea();
      double distance = m_ll.getEstimatedDistance();
      
      targetX_L = m_dt.getPositionL() + visX;
      targetX_R = m_dt.getPositionR() + visX;
      //targetY = .8*Math.toDegrees(Math.atan(.7*(distance+1)));
      targetY = 90-(visY+Constants.Vis_LLAngle);


      errorX = visX;
      errorY = m_shoot.getPivotPosition() - targetY;

      double n = 3;
      double distanceNow = 0.0254*Constants.VisY_distanceConstant/visA;
      
    } else {

    }

    if(liteMode) {
      //m_dt.setGear(Constants.HIGH_GEAR);
      m_dt.drive(drive, errorX/20);
      targetX_L = m_dt.getPositionL();
      targetX_R = m_dt.getPositionR();
    }
    else {
      m_dt.setGear(Constants.LOW_GEAR);
      m_dt.setPositionTarget(targetX_L, targetX_R);
    }
    


    m_shoot.setPivotTarget(targetY);

    SmartDashboard.putNumber("BRUHBRUHBRUH", targetY);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    
    
    return m_interrupt.getAsBoolean();
    /*
    (tapeTimer >= Constants.Vis_TimerConfidence)
    && (Math.abs(errorX) < Constants.VisX_Tol)
    && (Math.abs(errorY) < Constants.VisY_Tol)
    && (m_shoot.getPivotVelocity() < Constants.VisX_VTol)
    && (m_dt.getAbsoluteVelocity() < Constants.VisY_VTol);
    */
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_dt.setGear(Constants.HIGH_GEAR);
  }
}