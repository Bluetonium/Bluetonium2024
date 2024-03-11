package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.LimelightHelpers;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.constants.Constants.SensorConstants;
import frc.robot.subsystems.Intake;

public class TeleopIntake extends Command {
    private Intake intake;
    private BooleanSupplier intakeButton;
    private BooleanSupplier shootButton;
    private Timer shootOverrideTimer;
    private BooleanSupplier shooterReady;

    public TeleopIntake(Intake intake, BooleanSupplier intakeButton, BooleanSupplier shootButton,
            BooleanSupplier shooterReady) {
        addRequirements(intake);
        this.intake = intake;
        this.intakeButton = intakeButton;
        this.shootButton = shootButton;
        this.shooterReady = shooterReady;
        shootOverrideTimer = new Timer();
    }

    /**
     * Checks if timer has elapsed an amount of time and stops the timer if it has
     * 
     * @param timer The timer to preform the check on
     * @param time  The time to check if its elapsed
     * @return Returns true if the the timer has elapse the amount of time
     */
    private boolean checkAndStopTimer(Timer timer, double time) {
        if (timer.hasElapsed(time)) {
            timer.reset();
            timer.stop();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() {
        if (intakeButton.getAsBoolean()) {
            intake.setState(!intake.hasNote());
        } else if (shootButton.getAsBoolean()) {
            boolean ampInView = LimelightHelpers.getFiducialID(SensorConstants.LIMELIGHT_NAME) == 5
                    || LimelightHelpers.getFiducialID(SensorConstants.LIMELIGHT_NAME) == 6;
            if (shooterReady.getAsBoolean() || ampInView) {
                intake.setState(true);
            } else {
                shootOverrideTimer.start();
                if (checkAndStopTimer(shootOverrideTimer, ControllerConstants.OVERRIDE_TIME)) {
                    intake.setState(true);
                }
            }
        } else {
            intake.setState(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopAllMotion();
    }
}
