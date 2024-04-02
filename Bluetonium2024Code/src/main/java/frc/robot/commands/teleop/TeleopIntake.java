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
    private BooleanSupplier outakeWithIntake;

    public TeleopIntake(Intake intake, BooleanSupplier intakeButton, BooleanSupplier shootButton,
            BooleanSupplier shooterReady, DoubleSupplier robotYaw, BooleanSupplier outakeWithIntakeButton) {
        addRequirements(intake);
        this.intake = intake;
        this.intakeButton = intakeButton;
        this.shootButton = shootButton;
        this.shooterReady = shooterReady;
        this.robotYaw = robotYaw;
        this.outakeWithIntake = outakeWithIntakeButton;
        shootOverrideTimer = new Timer();

    }

    @Override
    public void execute() {// TODO make the logic here easier to understand
        if (intakeButton.getAsBoolean()) {
            intake.turnOnIntake();
        } else if (shootButton.getAsBoolean()) {
            if (shooterReady.getAsBoolean() || robotFacingAmp()) {
                intake.shoot();
            } else {
                shootOverrideTimer.start();// TODO get rid of this and perhaps use smartdash board to determine override
                if (checkAndStopTimer(shootOverrideTimer, ControllerConstants.OVERRIDE_TIME)) {
                    intake.shoot();
                }
            }
        } else if (outakeWithIntake.getAsBoolean()) {
            intake.reverseIntake();
        } else {
            intake.turnOffIntake();
        }
    }

    private boolean robotFacingAmp() {
        double value = ((Math.abs(robotYaw.getAsDouble()) + 90) % 180);
        return 180 - value < 45 || value < 45;
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
