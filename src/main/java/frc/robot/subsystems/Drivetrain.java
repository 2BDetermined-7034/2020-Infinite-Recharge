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
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
  private CANPIDController m_rightPID;
  private CANPIDController m_leftPID;
  private final DifferentialDrive m_drive;

  private final DoubleSolenoid m_shifter;

  private final AHRS m_gyro;

  public Drivetrain() {
    //Initialization
    m_left = new CANSparkMax(Constants.IDdriveL1, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_left2 = new CANSparkMax(Constants.IDdriveL2, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_left3 = new CANSparkMax(Constants.IDdriveL3, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right = new CANSparkMax(Constants.IDdriveR1, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right2 = new CANSparkMax(Constants.IDdriveR2, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_right3 = new CANSparkMax(Constants.IDdriveR3, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_drive = new DifferentialDrive(m_left, m_right);
    m_rightEnc = m_right.getEncoder();
    m_leftEnc = m_left.getEncoder();
    m_rightPID = m_right.getPIDController();
    m_leftPID = m_left.getPIDController();

    m_shifter = new DoubleSolenoid(Constants.IDshifterHigh, Constants.IDshifterLow);

    m_gyro = new AHRS(SPI.Port.kMXP);

    //Configuration
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

    m_left2.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 100);
    m_left3.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 100);
    m_right2.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 100);
    m_right3.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 100);

    m_left.setInverted(true);
    m_right.setInverted(true);

    m_left.setSmartCurrentLimit(Constants.DT_HardCurrentLimit);
    m_right.setSmartCurrentLimit(Constants.DT_HardCurrentLimit);

    m_rightEnc.setPositionConversionFactor(Constants.DT_cmPerRot);
    m_leftEnc.setPositionConversionFactor(Constants.DT_cmPerRot);

    m_rightPID.setP(Constants.DT_kP);
    m_rightPID.setI(Constants.DT_kI);
    m_rightPID.setD(Constants.DT_kD);
    m_rightPID.setOutputRange(-Constants.DT_MaxOutput, Constants.DT_MaxOutput);
    m_leftPID.setP(Constants.DT_kP);
    m_leftPID.setI(Constants.DT_kI);
    m_leftPID.setD(Constants.DT_kD);
    m_leftPID.setOutputRange(-Constants.DT_MaxOutput, Constants.DT_MaxOutput);
  }

  //Getters for drivetrain
  public double getOutputL() { return m_left.getAppliedOutput(); }
  public double getOutputR() { return m_right.getAppliedOutput(); }
  public double getPositionL() { return m_leftEnc.getPosition(); }
  public double getPositionR() { return m_rightEnc.getPosition(); }
  public double getVelocityL() { return m_leftEnc.getVelocity(); }
  public double getVelocityR() { return m_rightEnc.getVelocity(); }
  public double getAverageVelocity() { return (getVelocityL()-getVelocityR())/2; }
  public double getRotationalVelocity() { return (getVelocityL()+getVelocityR())/2; }
  public double getAbsoluteVelocity() { return (Math.abs(getVelocityL())+Math.abs(getVelocityR()))/2; }
  public double getRPM() { return getAbsoluteVelocity()/m_leftEnc.getPositionConversionFactor(); }
  public double getCurrentL() { return m_left.getOutputCurrent(); } //Only gets the current of the master motor controller
  public double getCurrentR() { return m_right.getOutputCurrent(); } //Only gets the current of the master motor controller

  public boolean getGear() {return (m_shifter.get() == Value.kForward); }
  
  public double getAngle() { 
    if (false) {
      return m_gyro.getYaw();
    }
    else {
      return 1.6438*(getPositionL()+getPositionR());
    }
    
  } //TODO if gyro is not working, use difference between the encoders

  public void drive(double speed, double rot) {
    m_drive.arcadeDrive(speed, rot);
	}

	public void driveLinear(double speed, double rot) {
    m_drive.arcadeDrive(speed, rot, false);
  }

	public void autoDrive(double leftSpeed, double rightSpeed) {
		m_drive.tankDrive(-rightSpeed, leftSpeed);
  }
  
  public void driveFor(double cm) {
    m_leftPID.setReference(-cm, ControlType.kPosition);
    m_rightPID.setReference(cm, ControlType.kPosition);
  }

  public void setPositionTarget(double left, double right) {
    m_leftPID.setReference(left, ControlType.kPosition);
    m_rightPID.setReference(right, ControlType.kPosition);
  }

  public void setEncoders(double inches) {
    m_leftEnc.setPosition(inches);
    m_rightEnc.setPosition(-inches);
  }

  public void setGear(boolean gear) {
    m_shifter.set(gear ? Value.kForward : Value.kReverse);
    //double conversion = Constants.DT_InchesPerRot/(gear == Constants.LOW_GEAR ? Constants.Shift_spread : 1);
    //m_rightEnc.setPositionConversionFactor(conversion);
    //m_leftEnc.setPositionConversionFactor(conversion);
  }

  public void stop() { m_drive.stopMotor(); }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName() + " Left Velocity", m_leftEnc.getVelocity());
    SmartDashboard.putNumber(getName() + " Right Velocity", m_rightEnc.getVelocity());
    SmartDashboard.putNumber(getName() + " Left Position", m_leftEnc.getPosition());
    SmartDashboard.putNumber(getName() + " Right Position", m_rightEnc.getPosition());
    SmartDashboard.putNumber(getName() + " Left Throttle", m_left.getAppliedOutput());
    SmartDashboard.putNumber(getName() + " Right Throttle", m_right.getAppliedOutput());
    SmartDashboard.putBoolean(getName() + " is in LOW", getGear());
  }
}
