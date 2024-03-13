package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.math.Conversions;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.AutonConstants;
import frc.robot.constants.Constants.SensorConstants;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {
    private Arm arm;

    public AimAtSpeaker(Arm arm) {
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if (!LimelightHelpers.getTV(SensorConstants.LIMELIGHT_NAME))
            return;
        arm.setArmAngle(getDesiredArmAngle());
    }

    @Override
    public boolean isFinished() {
        return Math.abs(getDesiredArmAngle().getRotations() - arm.getArmAngle()) <= AutonConstants.ALIGNMENT_TOLERACE;
    }

    private Rotation2d getDesiredArmAngle() {// set this to be the fancy equation
        Pose3d targetLocation = LimelightHelpers.getTargetPose3d_RobotSpace(SensorConstants.LIMELIGHT_NAME);
        double distance = Conversions.metersToInches(targetLocation.getX());
        return Rotation2d
                .fromDegrees(-0.0021 * Math.pow(distance, 2) + 0.6573 * distance - 7.7554);
    }

}
