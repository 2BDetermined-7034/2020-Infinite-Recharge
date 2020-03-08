/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.limelight.CamMode;
import frc.robot.commands.*;
import frc.robot.controllers.GunnerPanel;
import frc.robot.controllers.X3D;
import frc.robot.controllers.gPad;
//import frc.robot.controllers.gPad;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final Drivetrain m_drivetrain = new Drivetrain();
  private final PDP m_pdp = new PDP();
  private final Pneumatics m_pneumatics = new Pneumatics();
  private final Shooter m_shooter = new Shooter();
  private final Indexer m_indexer = new Indexer();
  // F private final ControlPanel m_controlPanel = new ControlPanel();
  // private final Leveler m_leveler = new Leveler();
  private final Climber m_climber = new Climber();
  private final LimeLight m_limeLight = new LimeLight();

  private final Command m_autoCommand;

  public final X3D m_X3D = new X3D(Constants.IDjoystick);
  public final GunnerPanel m_gunnerPanel = new GunnerPanel(Constants.IDgunnerpanel);
  public final gPad m_gPad = new gPad(Constants.IDgamepad);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    m_indexer.register();
    m_climber.register();
    m_shooter.register();
    m_drivetrain.register();

    SmartDashboard.putData("Toggle Compressor", new InstantCommand(() -> m_pneumatics.toggleCompressor()));
    SmartDashboard.putData("Change Lights", new InstantCommand(() -> m_limeLight.setLight()));
    SmartDashboard.putData("Change Mode", new InstantCommand(() -> m_limeLight.setMode(m_limeLight.getMode() == CamMode.VISION ? CamMode.DRIVERCAM : CamMode.VISION)));
    SmartDashboard.putData("Reset Pivot", new InstantCommand(() -> m_shooter.setPivotEncoder(0)));
    SmartDashboard.putData("Reset Arm", new InstantCommand(() -> m_climber.setArmEncoder(0)));
    SmartDashboard.putData("Reset Drivetrain", new InstantCommand(() -> m_drivetrain.setEncoders(0)));
    SmartDashboard.putData("Retract Shooter", new RetractShooter(m_shooter));
    SmartDashboard.putNumber("Fly Wheel Speed", 1);

    m_autoCommand = new TestAuto(m_drivetrain, m_limeLight, m_shooter, m_indexer);
    // m_autoCommand = new DriveForCm(m_drivetrain, 130);

    m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, () -> Shortcuts.deadZone(-m_X3D.getAxis("Y"), 0.1),
        () -> Shortcuts.deadZone(m_X3D.getAxis("X"), 0.1), () -> m_X3D.getRawButtonPressed(2)));
    //m_indexer.setDefaultCommand(
    //    new RunIndexer(m_indexer, () -> m_gPad.getAxis("LX") * .75, () -> -m_gPad.getAxis("LY") * .75));
    m_indexer.setDefaultCommand(new RunIndexer(m_indexer, () -> m_gunnerPanel.getIndexerHorizontalPower(), () -> m_gunnerPanel.getIndexerElevatorPower()));
    // m_level.setDefaultCommand(new RunLeveler(m_level, () ->
    // m_gPad.getTriggerAxis(Hand.kRight)-m_gPad.getTriggerAxis(Hand.kLeft)));
    //m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.setPivotTarget(0), m_shooter));
    m_shooter.setDefaultCommand(new ManualPivot(m_shooter, () -> m_gunnerPanel.getAxisPercent("pitch")));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    PrintCommand printCmd = new PrintCommand("WARNING ==ROBOT SELF-DESTRUCT SEQUENCE INITIATED==");
    // Command runArm = new RunArm(m_neoTest, () ->
    // m_gPad.getTriggerAxis(Hand.kLeft), () -> m_gPad.getTriggerAxis(Hand.kRight));
    m_X3D.getJoystickButton("3").whenPressed(new InstantCommand(() -> m_indexer.deployIntake(true)));
    m_X3D.getJoystickButton("4").whenPressed(new InstantCommand(() -> m_indexer.deployIntake(false)));
    //m_X3D.getJoystickButton(6).whenPressed(new SetBreak(m_climber, true));
    //m_X3D.getJoystickButton(4).whenPressed(new SetBreak(m_climber, false));
    m_gunnerPanel.getJoystickButton("armstoggle").whenPressed(new ToggleArm(m_climber, true));
    m_gunnerPanel.getJoystickButton("armstoggle").whenReleased(new ToggleArm(m_climber, false));
    m_gunnerPanel.getJoystickButton("winchup").whileHeld(new RunWinch(m_climber, () -> -1));
    m_gunnerPanel.getJoystickButton("winchdown").whileHeld(new RunWinch(m_climber, () -> .6));
    //m_gunnerPanel.getJoystickButton("armstoggle").toggleWhenActive(new SetArmPosition(m_climber, () -> (Constants.Arm_MaxAngle - Constants.Arm_MinAngle) * m_gunnerPanel.getAxisPercent("arms")));
    m_X3D.getJoystickButton(11).whileHeld(new SwoleMode(m_drivetrain));
    m_X3D.getJoystickButton(3).toggleWhenPressed(new VisAlign(m_drivetrain, m_shooter, m_limeLight, 
      () -> true, () -> (Math.abs(m_X3D.getX()) > .4)
    ));
    
    //TODO was bound to same button as winch m_X3D.getButton(7).whenPressed(new RetractArm(m_climber));
    //m_X3D.getButton(8).whenPressed(new RetractShooter(m_shooter));
    //x3D_B3.toggleWhenPressed(new RunArm(m_climber, () -> (1-m_X3D.getThrottle())/2));
    //x3D_B7.whenPressed(() -> m_climber.resetArmEncoder());
    m_X3D.getJoystickButton(12).whenPressed(printCmd);
    m_gunnerPanel.getJoystickButton("intakein").whileHeld(new RunIntake(m_indexer, () -> -Constants.IndexIntake_Output));
    m_gunnerPanel.getJoystickButton("intakeout").whileHeld(new RunIntake(m_indexer, () -> Constants.IndexIntake_Output));
    m_gunnerPanel.getJoystickButton("intakein").whenPressed(new InstantCommand(() -> m_indexer.setHopper(.25)));
    m_gunnerPanel.getJoystickButton("intakein").whenReleased(new InstantCommand(() -> m_indexer.setHopper(0)));
    //Fm_gPad.getJoystickButton("X").whenPressed(new InstantCommand(() -> m_shooter.setWheels(SmartDashboard.getNumber("Fly Wheel Speed", 0))));
    //Fm_gPad.getJoystickButton("X").whenReleased(new InstantCommand(() -> m_shooter.setWheels(0)));
    //Fm_gPad.getJoystickButton("Y").toggleWhenPressed(new RunArm(m_climber, () -> 2*(m_gPad.getAxis("LTrigger")-m_gPad.getAxis("RTrigger"))));
    m_gunnerPanel.getJoystickButton("intakeretract").whenPressed(new InstantCommand(() -> m_indexer.deployIntake(true)));
    m_gunnerPanel.getJoystickButton("intakeretract").whenReleased(new InstantCommand(() -> m_indexer.deployIntake(false)));
    m_gunnerPanel.getJoystickButton("fire").whileHeld(new RunShooter(m_shooter, () -> m_gunnerPanel.getAxisPercent("speed")));
    //Fm_gPad.getJoystickButton("RSB").toggleWhenPressed(new ManualPivot(m_shooter, () -> (1+m_X3D.getAxis("THROTTLE"))/2));

    /*
    m_gPad.getJoystickButton("A").whileHeld(new RunIntake(m_indexer, () -> Constants.IndexIntake_Output));
    m_gPad.getJoystickButton("B").whileHeld(new RunIntake(m_indexer, () -> -Constants.IndexIntake_Output));
    m_gPad.getJoystickButton("X").whenPressed(new InstantCommand(() -> m_shooter.setWheels(SmartDashboard.getNumber("Fly Wheel Speed", 0))));
    m_gPad.getJoystickButton("X").whenReleased(new InstantCommand(() -> m_shooter.setWheels(0)));
    m_gPad.getJoystickButton("Y").toggleWhenPressed(new RunArm(m_climber, () -> 2*(m_gPad.getAxis("LTrigger")-m_gPad.getAxis("RTrigger"))));
    m_gPad.getJoystickButton("START").whenPressed(new InstantCommand(() -> m_indexer.deployIntake(true)));
    m_gPad.getJoystickButton("BACK").whenPressed(new InstantCommand(() -> m_indexer.deployIntake(false)));
    m_gPad.getJoystickButton("RSB").toggleWhenPressed(new ManualPivot(m_shooter, () -> (1+m_X3D.getAxis("THROTTLE"))/2));
    m_gPad.getJoystickButton("LB").whileHeld(new InstantCommand(() -> m_indexer.setHopper(.25)));
    m_gPad.getJoystickButton("RB").whileHeld(new InstantCommand(() -> m_indexer.setHopper(0)));
    m_gPad.getJoystickButton("LSB").whenPressed(printCmd);
    */

    
    //(new JoystickButton(m_gPad, 1)).whenPressed(new InstantCommand(() -> m_shooter.setWheels(SmartDashboard.getNumber("Fly Wheel Speed", 0))));
    //(new JoystickButton(m_gPad, 1)).whenReleased(new InstantCommand(() -> m_shooter.setWheels(0)));
    //(new JoystickButton(m_gPad, 2)).toggleWhenPressed(new ManualPivot(m_shooter, () -> (1+m_X3D.getThrottle())/2));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
