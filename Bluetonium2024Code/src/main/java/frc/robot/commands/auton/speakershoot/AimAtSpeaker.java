package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Distance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.math.Conversions;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.AutonConstants;
import frc.robot.constants.Constants.SensorConstants;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {
    private Arm arm;
    private double desiredAngle = Double.MAX_VALUE;

    public AimAtSpeaker(Arm arm) {
        addRequirements(arm);
        this.arm = arm;
    }

    @Override
    public void execute() {

        SmartDashboard.putString("Status2", "Target found");
        Rotation2d angle = getDesiredArmAngle();
        SmartDashboard.putNumber("desired Angle", angle.getRotations());
        desiredAngle = angle.getRotations();
        // arm.setArmAngle(angle.getRotations());
        arm.setArmSpeed(0.2);

    }

    @Override
    public boolean isFinished() {
        return Math.abs(desiredAngle - arm.getArmAngle()) <= 0.05;
    }

    @Override
    public void end(boolean interrupted) {
        arm.setArmSpeed(0);
    }

    private Rotation2d getDesiredArmAngle() {// set this to be the fancy equation

        Pose3d targetLocation = LimelightHelpers.getTargetPose3d_RobotSpace(SensorConstants.LIMELIGHT_NAME);
        double distance = Conversions.metersToInches(targetLocation.getZ());
        return Rotation2d
                .fromDegrees(-0.0021 * Math.pow(distance, 2) + 0.6573 * distance - 7.7554);
    }

}
