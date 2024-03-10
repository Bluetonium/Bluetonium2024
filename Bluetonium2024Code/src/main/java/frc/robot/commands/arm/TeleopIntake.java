package frc.robot.commands.arm;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class TeleopIntake extends Command {
    private Intake intake;
    private BooleanSupplier intakeButton;
    private BooleanSupplier shootButton;

    public TeleopIntake(Intake intake, BooleanSupplier intakeButton, BooleanSupplier shootButton) {
        addRequirements(intake);
        this.intake = intake;
        this.intakeButton = intakeButton;
        this.shootButton = shootButton;
    }

    @Override
    public void execute() {
        if (intakeButton.getAsBoolean() || shootButton.getAsBoolean()) {
            intake.setIntakeState(1);
        } else {
            intake.setIntakeState(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopAllMotion();
    }
}
