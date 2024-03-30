package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

public class PrepareToShoot extends ParallelCommandGroup {
    public PrepareToShoot(Swerve swerve, Arm arm, Shooter shooter) {
        addCommands(new AimAtSpeaker(arm),
                new RotateToSpeaker(swerve),
                new SpinUpShooter(shooter));
    }
}
