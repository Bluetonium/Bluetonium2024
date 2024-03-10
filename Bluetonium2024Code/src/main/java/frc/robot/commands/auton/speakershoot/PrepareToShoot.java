package frc.robot.commands.auton.speakershoot;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.SensorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

public class PrepareToShoot extends ParallelCommandGroup {
    public PrepareToShoot(Swerve swerve, Arm arm, Shooter shooter) {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        if (alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red) {
            LimelightHelpers.setPriorityTagID(SensorConstants.LIMELIGHT_NAME, 3);
        } else {
            LimelightHelpers.setPriorityTagID(SensorConstants.LIMELIGHT_NAME, 7);
        }

        addCommands(new AimAtSpeaker(arm),
                new RotateToSpeaker(swerve),
                new SpinUpShooter(shooter));
    }
}
