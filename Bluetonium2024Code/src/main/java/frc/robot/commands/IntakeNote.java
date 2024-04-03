package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {
    private Intake intake;

    public IntakeNote(Intake intake) {
        addRequirements(intake);
        this.intake = intake;
    }

    @Override
    public void execute() {
        if (!intake.isNoteInIntake()) {
            intake.turnOnIntake();
        }
    }

    @Override
    public boolean isFinished() {// TODO if we get 2 beam breaks add centering the note
        return intake.isNoteInIntake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.turnOffIntake();
    }
}
