/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LimeLight extends SubsystemBase {

    private final NetworkTable m_table;

    public LimeLight() {
        m_table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getXAngle() { return m_table.getEntry("tx").getDouble(0); }
    public double getYAngle() { return m_table.getEntry("ty").getDouble(0); }
    public double getArea() { return m_table.getEntry("ta").getDouble(0); }
    public double getShortSide() { return m_table.getEntry("tshort").getDouble(0); }
    public double getLongSide() { return m_table.getEntry("tlong").getDouble(0); }
    public boolean getDetected() { return m_table.getEntry("tv").getDouble(0) > 0; }
    public double getLastDetected() { return m_table.getEntry("tv").getLastChange(); }

    public double[] getContour0() {
        double[] contour0 = new double[4];

        contour0[0] = m_table.getEntry("tx0").getDouble(0);
        contour0[1] = m_table.getEntry("ty0").getDouble(0);
        contour0[2] = m_table.getEntry("ta0").getDouble(0);
        contour0[3] = m_table.getEntry("ts0").getDouble(0);
        return contour0;
    }

    public double[] getContour1() {
        double[] contour1 = new double[4];

        contour1[0] = m_table.getEntry("tx1").getDouble(0);
        contour1[1] = m_table.getEntry("ty1").getDouble(0);
        contour1[2] = m_table.getEntry("ta1").getDouble(0);
        contour1[3] = m_table.getEntry("ts1").getDouble(0);
        return contour1;
    }

    public double[] getContour2() {
        double[] contour2 = new double[4];

        contour2[0] = m_table.getEntry("tx2").getDouble(0);
        contour2[1] = m_table.getEntry("ty2").getDouble(0);
        contour2[2] = m_table.getEntry("ta2").getDouble(0);
        contour2[3] = m_table.getEntry("ts2").getDouble(0);
        return contour2;
    }

    public double getEstimatedDistance() {
        return 2.494*Math.pow(Math.tan(Math.toRadians(Constants.Vis_LLAngle+getYAngle())), -1);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber(getName() + " X Angle", getXAngle());
        SmartDashboard.putNumber(getName() + " Y Angle", getYAngle());
        SmartDashboard.putBoolean(getName() + " Detected", getDetected());
        SmartDashboard.putNumber(getName() + " Distance", getEstimatedDistance());
    }
}
