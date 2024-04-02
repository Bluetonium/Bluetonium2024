package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class TeleopIntake extends Command {
    private Intake intake;
    private BooleanSupplier intakeButton;
    private BooleanSupplier shootButton;
    private DoubleSupplier robotYaw;
    private BooleanSupplier outakeWithIntake;
    private BooleanSupplier shooterReady;

    public TeleopIntake(Intake intake, BooleanSupplier intakeButton, BooleanSupplier shootButton,
            BooleanSupplier shooterReady, DoubleSupplier robotYaw, BooleanSupplier outakeWithIntakeButton) {
        addRequirements(intake);
        this.intake = intake;
        this.intakeButton = intakeButton;
        this.shootButton = shootButton;
        this.shooterReady = shooterReady;
        this.robotYaw = robotYaw;
        this.outakeWithIntake = outakeWithIntakeButton;
    }

    @Override
    public void execute() {
        if (intakeButton.getAsBoolean() && !intake.isNoteInIntake()) {
            intake.turnOnIntake();
        } else if (shootButton.getAsBoolean() && (robotFacingAmp() || shooterReady.getAsBoolean())) {
            intake.shoot();
        } else if (outakeWithIntake.getAsBoolean()) {
            intake.reverseIntake();
        } else {
            intake.turnOffIntake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopAllMotion();
    }

    private boolean robotFacingAmp() {
        double value = ((Math.abs(robotYaw.getAsDouble()) + 90) % 180);
        return 180 - value < 45 || value < 45;
    }
}
