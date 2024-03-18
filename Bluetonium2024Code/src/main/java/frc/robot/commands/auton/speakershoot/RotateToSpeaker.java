package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.MiscConstants;
import frc.robot.subsystems.Swerve;

public class RotateToSpeaker extends Command {
    private Swerve swerve;
    private double offsetValue = Double.MAX_VALUE;

    public RotateToSpeaker(Swerve swerve) {
        addRequirements(swerve);
        this.swerve = swerve;
    }

    @Override
    public void execute() {
        offsetValue = LimelightHelpers.getTX(MiscConstants.LIMELIGHT_NAME);
        ChassisSpeeds desiredSpeeds = new ChassisSpeeds(0, 0, Math.copySign(0.5, offsetValue));
        swerve.driveRobotReleative(desiredSpeeds);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(offsetValue) <= 0.5;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopAllMotion();
    }
}
