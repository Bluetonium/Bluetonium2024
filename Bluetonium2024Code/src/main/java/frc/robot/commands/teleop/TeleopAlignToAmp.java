package frc.robot.commands.teleop;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class TeleopAlignToAmp extends Command {
    private Swerve swerve;
    private DoubleSupplier robotYaw;
    private DoubleSupplier driveAxis;
    private NetworkTable limelight;
    private DoubleConsumer controllerRumble;

    private static final double KP = 0.2;

    public TeleopAlignToAmp(Swerve swerve, DoubleSupplier robotYaw, DoubleSupplier driveAxis, NetworkTable limelight) {
        addRequirements(swerve);
        this.swerve = swerve;
        this.robotYaw = robotYaw;
        this.driveAxis = driveAxis;
        this.limelight = limelight;
    }

    @Override
    public void execute() {
        if (limelight.getEntry("tv").getInteger(0) != 1) {
            controllerRumble.accept(1);
            return;
        }
        controllerRumble.accept(0);
        double xOffset = limelight.getEntry("tx").getDouble(100) * KP;
        double rotationOffset = 90 - Math.abs(robotYaw.getAsDouble() % 360) * KP;
        // TODO check this logic

        swerve.driveRobotReleative(new ChassisSpeeds(xOffset, driveAxis.getAsDouble() * 2, rotationOffset));
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopAllMotion();
    }

}
