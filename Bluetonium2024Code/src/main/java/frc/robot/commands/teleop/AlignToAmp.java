package frc.robot.commands.teleop;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.MiscConstants;
import frc.robot.subsystems.Swerve;

//make account for being angled eventually or not, we shall see how much of an issue it is
public class AlignToAmp extends Command {
    private static final double P_VALUE = 0.05;
    private NetworkTable limelight;
    private Swerve swerve;
    private DoubleSupplier moveTowardAmp;
    private DoubleConsumer rumble;

    public AlignToAmp(NetworkTable limelight, Swerve swerve, DoubleSupplier moveTowardsAmp, DoubleConsumer rumble) {
        addRequirements(swerve);
        this.limelight = limelight;
        this.swerve = swerve;
        this.moveTowardAmp = moveTowardsAmp;
        this.rumble = rumble;
    }

    @Override
    public void initialize() {
        limelight.getEntry("pipeline").setNumber(MiscConstants.AMP_PIPELINE);
    }

    @Override
    public void execute() {
        if (limelight.getEntry("tv").getInteger(0) == 0) {
            rumble.accept(1);
            return;
        }
        rumble.accept(0);
        double strafeAmount = P_VALUE * limelight.getEntry("tx").getDouble(0);
        swerve.driveRobotReleative(new ChassisSpeeds(moveTowardAmp.getAsDouble(), strafeAmount, 0));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopAllMotion();
        rumble.accept(0);
    }

}
