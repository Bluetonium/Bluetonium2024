package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.math.Conversions;
import frc.robot.constants.Constants.MiscConstants;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {// This isnt work rewrite all of it
    private Arm arm;
    private double desiredAngle = Double.MAX_VALUE;
    private NetworkTable limelight;

    private static final Double[] defaultValue = new Double[3];

    public AimAtSpeaker(Arm arm, NetworkTable limelight) {
        addRequirements(arm);
        this.arm = arm;
        this.limelight = limelight;
    }

    @Override
    public void initialize() {
        limelight.getEntry("pipeline").setNumber(MiscConstants.CENTER_SPEAKER_PIPELINE);
    }

    @Override
    public void execute() {
        Rotation2d angle = getDesiredArmAngle();
        desiredAngle = angle.getRotations();
        arm.setArmAngle(angle.getRotations());
    }

    @Override
    public boolean isFinished() {
        return arm.isAtAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        arm.setArmSpeed(0);
    }

    private Rotation2d getDesiredArmAngle() {// set this to be the fancy equation
        Double[] targetLocation = limelight.getEntry("targetpose_robotspace").getDoubleArray(defaultValue);
        double distance = Conversions.metersToInches(targetLocation[2]);
        return Rotation2d
                .fromDegrees(-0.0021 * Math.pow(distance, 2) + 0.6573 * distance - 7.7554);
    }

}
