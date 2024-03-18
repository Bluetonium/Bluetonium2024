package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.subsystems.Intake;

public class TeleopIntake extends Command {
    private Intake intake;
    private BooleanSupplier intakeButton;
    private BooleanSupplier shootButton;
    private Timer shootOverrideTimer;
    private BooleanSupplier shooterReady;
    private DoubleSupplier robotYaw;

    public TeleopIntake(Intake intake, BooleanSupplier intakeButton, BooleanSupplier shootButton,
            BooleanSupplier shooterReady, DoubleSupplier robotYaw) {
        addRequirements(intake);
        this.intake = intake;
        this.intakeButton = intakeButton;
        this.shootButton = shootButton;
        this.shooterReady = shooterReady;
        this.robotYaw = robotYaw;
        shootOverrideTimer = new Timer();
    }

    @Override
    public void execute() {
        if (intakeButton.getAsBoolean()) {
            intake.turnOnIntake();
        } else if (shootButton.getAsBoolean()) {
            double value = ((Math.abs(robotYaw.getAsDouble()) + 90) % 180);
            boolean ampInView = 180 - ((Math.abs(robotYaw.getAsDouble()) + 90) % 180) < 45 || value < 45;
            if (shooterReady.getAsBoolean() || ampInView) {
                intake.shoot();
            } else {
                shootOverrideTimer.start();
                if (checkAndStopTimer(shootOverrideTimer, ControllerConstants.OVERRIDE_TIME)) {
                    intake.shoot();
                }
            }
        } else {
            intake.turnOffIntake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopAllMotion();
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
}
