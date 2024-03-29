package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.MiscConstants;
import frc.robot.subsystems.Swerve;

public class RotateToSpeaker extends Command {
    private Swerve swerve;
    private double offsetValue = Double.MAX_VALUE;
    //private NetworkTable limelight;

    public RotateToSpeaker(Swerve swerve) {
        addRequirements(swerve);
        this.swerve = swerve;
        //this.limelight = limelight;
    }

    @Override
    public void initialize() {
        //limelight.getEntry("pipeline").setNumber(MiscConstants.CENTER_SPEAKER_PIPELINE);
    }

    @Override
    public void execute() {
        //offsetValue = limelight.getEntry("tx").getDouble(0);
        //ChassisSpeeds desiredSpeeds = new ChassisSpeeds(0, 0, Math.copySign(0.5, offsetValue));
        //swerve.driveRobotReleative(desiredSpeeds);
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
