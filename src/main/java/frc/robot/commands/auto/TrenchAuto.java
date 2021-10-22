/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Shortcuts;
import frc.robot.commands.Drive;
import frc.robot.commands.DriveForCm;
import frc.robot.commands.RunIndexer;
import frc.robot.commands.VisAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchAuto extends SequentialCommandGroup {
  /**
   * Creates a new TrenchAuto.
   */
  public TrenchAuto(Drivetrain drivetrain, Indexer indexer, LimeLight ll, Shooter shooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      //new InstantCommand(() -> indexer.setIntake(1)),
      //375 cm's is all three, 350 is first two
      //new DriveForCm(drivetrain, -260, .3),
      //new DriveForCm(drivetrain, -80, .2),
      new DriveForCm(drivetrain, -355, .2),
      new ParallelRaceGroup(
        new Drive(drivetrain, () -> .4, () -> -.5, () -> false),
        new WaitCommand(.05)
      ),
      new DriveForCm(drivetrain, 200, .35),
      new InstantCommand(() -> shooter.setWheels(.875)),
      new ParallelRaceGroup(
        new VisAlign(drivetrain, shooter, ll, () -> true, () -> false, () -> 0),
        new SequentialCommandGroup(
          new WaitCommand(1),
          new InstantCommand(() -> indexer.setHopper(1)),
          new RunIndexer(indexer, () -> .2, () -> 1).withTimeout(4)
        )
      ),
      new InstantCommand(() -> indexer.setIntake(0)),
      new InstantCommand(() -> shooter.setPivotTarget(0)),
      new InstantCommand(() -> shooter.setWheels(0))
    );
  }
}
