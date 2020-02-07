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
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
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
  // private final Pneumatics m_pneumatics = new Pneumatics();
  private final Shooter m_shooter = new Shooter();
  private final Indexer m_indexer = new Indexer();
  //private final NeoTest m_neoTest = new NeoTest();
  //private final ControlPanel m_controlPanel = new ControlPanel();
  //private final Climber m_climber = new Climber();
  private final LimeLight m_limeLight = new LimeLight();

  private final TestAuto m_autoCommand = new TestAuto(m_drivetrain, m_limeLight);

  private final Joystick m_X3D = new Joystick(Constants.IDjoystick);
  private final XboxController m_gPad = new XboxController(Constants.IDgamepad);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // SmartDashboard.putData("Toggle Compressor", new
    // InstantCommand(m_pneumatics::toggleCompressor));
    //m_neoTest.register();
    //m_climber.register();
    m_shooter.register();
    m_drivetrain.register();
    m_pdp.register();
    m_limeLight.register();

    // SmartDashboard.putData("Set P", new InstantCommand(m_neoTest::updateP));
    // SmartDashboard.putData("Set I", new InstantCommand(m_neoTest::updateI));
    // SmartDashboard.putData("Set D", new InstantCommand(m_neoTest::updateD));

    m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, () -> m_X3D.getY(), () -> m_X3D.getX()));
    //m_climber.setDefaultCommand(new runClimber(m_climber, () -> m_gPad.getY(Hand.kRight)));

    // Configure the button bindings
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
    //Command runArm = new RunArm(m_neoTest, () -> m_gPad.getTriggerAxis(Hand.kLeft), () -> m_gPad.getTriggerAxis(Hand.kRight));
    
    if (m_X3D.getButtonCount() > 0) {
      JoystickButton x3D_Trigger = new JoystickButton(m_X3D, 1);
      JoystickButton x3D_B2 = new JoystickButton(m_X3D, 2);
      JoystickButton x3D_B3 = new JoystickButton(m_X3D, 3);
      JoystickButton x3D_B4 = new JoystickButton(m_X3D, 4);
      JoystickButton x3D_B5 = new JoystickButton(m_X3D, 5);
      JoystickButton x3D_B6 = new JoystickButton(m_X3D, 6);
      JoystickButton x3D_B7 = new JoystickButton(m_X3D, 7);
      JoystickButton x3D_B8 = new JoystickButton(m_X3D, 8);
      JoystickButton x3D_B9 = new JoystickButton(m_X3D, 9);
      JoystickButton x3D_B10 = new JoystickButton(m_X3D, 10);
      JoystickButton x3D_B11 = new JoystickButton(m_X3D, 11);
      JoystickButton x3D_B12 = new JoystickButton(m_X3D, 12);

      x3D_Trigger.whileHeld(new runIntake(m_indexer, () -> (m_X3D.getThrottle()-1)/2, () -> m_X3D.getRawButton(2)));
      // x3DButtons[3].whenPressed(() -> m_neoTest.reset());
      // x3DButtons[3].whenPressed(new motorSpinupTest(m_neoTest));
      x3D_B3.toggleWhenPressed(new VisAlign(m_drivetrain, m_limeLight, () -> m_X3D.getY(), () -> m_X3D.getX()));
      x3D_B12.whenPressed(printCmd);
    }

    if (m_gPad.getButtonCount() > 0) {

      //gPadButtons[Button.kA.value].toggleWhenPressed(runArm);
      //gPadButtons[Button.kB.value].whenPressed(() -> m_neoTest.reset());
      //gPadButtons[Button.kStart.value].whenPressed(printCmd);
    }
  
    // new JoystickButton(m_driverController, Button.kBumperRight.value)
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
