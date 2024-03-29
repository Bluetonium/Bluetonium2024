package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

public class ShootingSequence extends SequentialCommandGroup {
    public ShootingSequence(Swerve swerve, Arm arm, Shooter shooter, Intake intake) {
        addCommands(
                new PrepareToShoot(swerve, arm, shooter),
                new ShootNote(intake, shooter, arm));
    }
}
