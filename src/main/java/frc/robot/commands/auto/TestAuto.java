/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TestAuto extends SequentialCommandGroup {
  /**
   * Creates a new TestAuto.
   */
  public TestAuto(Drivetrain dt, LimeLight ll, Shooter shooter, Indexer indexer) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    /*
    super(new InstantCommand(() -> shooter.setPivotTarget(60)),
          new RunShooterWheels(shooter, () -> .9).alongWith(
            new WaitCommand(4),
            new VisAlign(dt, shooter, ll).withTimeout(5),
            new RunIndexer(indexer, () -> .5, () -> 1)
          ).withTimeout(5),
          new InstantCommand(() -> shooter.setPivotTarget(0)),
          new RunShooterWheels(shooter, () -> 0)
          );*/

    /*
    super(new ParallelCommandGroup(
            new InstantCommand(() -> shooter.setWheels(.9)),
            new VisAlign(dt, shooter, ll, () -> false, () -> false),
            new SequentialCommandGroup(
              new WaitCommand(3),
              new RunIndexer(indexer, () -> .5, () -> 1).withTimeout(4),
              new RunIndexer(indexer, () -> 0, () -> 0).withTimeout(.1),
              new InstantCommand(() -> shooter.setPivotTarget(0))
            )),
            new InstantCommand(() -> shooter.setWheels(0))
            //new DriveForCm(dt, 50)
            );
    */
    super(
      new InstantCommand(() -> shooter.setWheels(.70)),
      new ParallelRaceGroup(
        new VisAlign(dt, shooter, ll, () -> true, () -> false, () -> 0),
        new SequentialCommandGroup(
          new WaitCommand(1),
          new InstantCommand(() -> indexer.setHopper(1)),
          new RunIndexer(indexer, () -> .2, () -> 1).withTimeout(2)
        )
      ),
      new InstantCommand(() -> shooter.setPivotTarget(0)),
      new InstantCommand(() -> shooter.setWheels(0)),
      new DriveForCm(dt, 150, .2)
    );
  }
}
