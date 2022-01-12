/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

public class BlakeTrashEyes extends CommandBase {

  private final Drivetrain m_dt;
  private final LimeLight m_ll;
  private final Shooter m_shoot;

  private BooleanSupplier m_vis;
  private BooleanSupplier m_interrupt;

  private boolean tapeDetected;

  private int tapeTimer;

  private double errorY;
  private double errorX;
  private double targetY;
  private double targetX_L;
  private double targetX_R;
  //private double distance;
  
  public BlakeTrashEyes(Drivetrain dt, Shooter shooter, LimeLight ll, BooleanSupplier useVision, BooleanSupplier interrupt) {
    m_dt = dt;
    m_ll = ll;
    m_shoot = shooter;
    m_vis = useVision;
    m_interrupt = interrupt;
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
    boolean vis = m_vis.getAsBoolean();
    tapeDetected = m_ll.getDetected();

    if(tapeDetected) {
      tapeTimer++;
    }
    else {
      tapeTimer = 0;
    }

    if (tapeTimer >= Constants.Vis_TimerConfidence && vis) {
      double visX = m_ll.getXAngle() + Constants.VisX_Offset;      

      targetX_L = m_dt.getPositionL() + visX;
      targetX_R = m_dt.getPositionR() + visX;

      double[] contour0 = m_ll.getContour0();
      SmartDashboard.putNumber("contour1 x", contour0[0]);
      SmartDashboard.putNumber("contour1 y", contour0[1]);
      SmartDashboard.putNumber("contour1 area", contour0[2]);
      SmartDashboard.putNumber("contour1 skew", contour0[3]);

      double[] contour1 = m_ll.getContour1();
      SmartDashboard.putNumber("contour2 x", contour1[0]);
      SmartDashboard.putNumber("contour2 y", contour1[1]);
      SmartDashboard.putNumber("contour2 area", contour1[2]);
      SmartDashboard.putNumber("contour2 skew", contour1[3]);

      double[] contour2 = m_ll.getContour2();
      SmartDashboard.putNumber("contour3 x", contour2[0]);
      SmartDashboard.putNumber("contour3 y", contour2[1]);
      SmartDashboard.putNumber("contour3 area", contour2[2]);
      SmartDashboard.putNumber("contour3 skew", contour2[3]);

      //double distanceNow = 0.0254*Constants.VisY_distanceConstant/visA;
    }

    m_dt.setPositionTarget(targetX_L, targetX_R);

    //SmartDashboard.putNumber("BRUHBRUHBRUH", targetY);
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
  }
}
