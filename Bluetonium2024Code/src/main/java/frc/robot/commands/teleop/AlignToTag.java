package frc.robot.commands.teleop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve;

public class AlignToTag extends Command {
    private DoubleSupplier translationAxis;
    private Swerve swerve;
    private Limelight limelight;
    public AlignToTag(Limelight limelight, Swerve swerve, DoubleSupplier translationAxis) {
        addRequirements(swerve);
        this.translationAxis = translationAxis;
        this.swerve = swerve;
        this.limelight = limelight;
    }

    @Override
    public void execute() {
        double error = limelight.getTx();
        double speed = SmartDashboard.getNumber("alignmentSpeed", 0.2);
        double errorRange = SmartDashboard.getNumber("errorRange", 5);
        double strafeSpeed = (Math.abs(error) > errorRange && limelight.validTargetExists()) ? speed : 0;

        swerve.drive(new Translation2d(translationAxis.getAsDouble(), strafeSpeed), 0, false, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopAllMotion();
    }

}
