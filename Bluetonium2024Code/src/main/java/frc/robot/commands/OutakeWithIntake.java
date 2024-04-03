package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class OutakeWithIntake extends Command {
    private Intake intake;

    public OutakeWithIntake(Intake intake) {
        addRequirements(intake);
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.reverseIntake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.turnOffIntake();
    }
}
