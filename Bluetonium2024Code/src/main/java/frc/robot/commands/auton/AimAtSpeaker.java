package frc.robot.commands.auton;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.AutonConstants;
import frc.robot.constants.Constants.SensorConstants;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {
    private Arm arm;

    public AimAtSpeaker(Arm arm) {
        addRequirements(arm);
    }

    private Rotation2d getDesiredArmAngle() {// set this to be the fancy equation
        return new Rotation2d(0, 0);
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

}
