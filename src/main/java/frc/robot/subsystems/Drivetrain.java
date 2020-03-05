/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  
  private final CANSparkMax m_left;
  private final CANSparkMax m_left2;
  private final CANSparkMax m_left3;

  private final CANSparkMax m_right;
  private final CANSparkMax m_right2;
  private final CANSparkMax m_right3;
  
  private final CANEncoder m_leftEnc;
  private final CANEncoder m_rightEnc;

  private final DifferentialDrive m_drive;
  private final DifferentialDriveOdometry m_odometry = null;

  //private final CANPIDController m_pidControl;

  private final AHRS m_gyro;

  public Drivetrain() {
    m_left = new CANSparkMax(Constants.IDdriveL1, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_left2 = new CANSparkMax(Constants.IDdriveL2, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_left3 = new CANSparkMax(Constants.IDdriveL3, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right = new CANSparkMax(Constants.IDdriveR1, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right2 = new CANSparkMax(Constants.IDdriveR2, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right3 = new CANSparkMax(Constants.IDdriveR3, CANSparkMaxLowLevel.MotorType.kBrushless);

    m_left.setIdleMode(IdleMode.kBrake);
    m_left2.setIdleMode(IdleMode.kBrake);
    m_left3.setIdleMode(IdleMode.kBrake);
    m_right.setIdleMode(IdleMode.kBrake);
    m_right2.setIdleMode(IdleMode.kBrake);
    m_right3.setIdleMode(IdleMode.kBrake);

    m_left2.follow(m_left);
    m_left3.follow(m_left);
    m_right2.follow(m_right);
    m_right3.follow(m_right);

    m_left.setInverted(true);
    m_right.setInverted(true);

    m_left.setSmartCurrentLimit(40);
    m_right.setSmartCurrentLimit(40);

    m_leftEnc = m_left.getEncoder();
    m_rightEnc = m_right.getEncoder();

    m_drive = new DifferentialDrive(m_left, m_right);

    m_gyro = new AHRS(SPI.Port.kMXP);

  }

  public void drive(double speed, double rot) {
    m_drive.arcadeDrive(speed, rot);
	}

	public void drive2(double speed, double rot) {
    m_drive.arcadeDrive(speed, rot, true);
	}

	public void autoDrive(double leftSpeed, double rightSpeed) {
		m_drive.tankDrive(rightSpeed, leftSpeed);
  }
  
   /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_left.setVoltage(leftVolts);
    m_right.setVoltage(-rightVolts);
    m_drive.feed();
  }

	public void stop() {
		m_drive.stopMotor();
  }
  
  public double getAngle() {
    return m_gyro.getYaw();
  }

  public double getEncPosL ()  {
    return m_leftEnc.getPosition();
  }

  public double getEncPosR ()  {
    return m_rightEnc.getPosition();
  }

   /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEnc.getVelocity(), m_rightEnc.getVelocity());
  }

  

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */

   // TODO figure out if gyro is reversed
  public double getHeading() {
    return Math.IEEEremainder(m_gyro.getAngle(), 360);
  }

  	

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Left Encoder", m_leftEnc.getPosition());
    SmartDashboard.putNumber(getName() + " Right Encoder", m_rightEnc.getPosition());
    SmartDashboard.putNumber(getName() + " Left Throttle", m_left.getAppliedOutput());
    SmartDashboard.putNumber(getName() + " Right Throttle", m_right.getAppliedOutput());

    m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEnc.getPosition(), m_rightEnc.getPosition());
  }
}
