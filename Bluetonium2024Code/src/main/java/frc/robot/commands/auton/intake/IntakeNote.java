package frc.robot.commands.auton.intake;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;

public class IntakeNote extends Command {
    private Timer timeoutTimer;
    private Intake intake;
    private Swerve swerve;

    private static final double TIMEOUT = 3;
    private double resistanceDifference = 0;
    private static final ChassisSpeeds SLOW_DRIVE_FORWARD = new ChassisSpeeds(0, 0.3, 0);

    public IntakeNote(Intake intake, Swerve swerve) {
        addRequirements(intake);
        this.intake = intake;
        this.swerve = swerve;
    }

    @Override
    public void execute() {
        if (timeoutTimer.hasElapsed(TIMEOUT)) {
            intake.turnOffIntake();
            swerve.stopAllMotion();
        } else {
            resistanceDifference = intake.getOutputCurrentDifference();

            intake.turnOnIntake();
            swerve.driveRobotReleative(SLOW_DRIVE_FORWARD);
        }
    }

    @Override
    public void initialize() {
        resistanceDifference = intake.getOutputCurrentDifference();
        timeoutTimer.start();
    }

    // hasNote()
    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(0.25)&&resistanceDifference>1; //TODO figure out what threshold to have it at
    }

    @Override
    public void end(boolean isInterupted) {
        intake.turnOffIntake();
        swerve.stopAllMotion();
    }
}
