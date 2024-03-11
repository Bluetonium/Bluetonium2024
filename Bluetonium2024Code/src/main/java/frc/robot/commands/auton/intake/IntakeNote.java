package frc.robot.commands.auton.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

//TODO i dont know if this actually works BUUUTT for now itll be fine

public class IntakeNote extends Command {
    Intake intake;
    public IntakeNote(Intake intake) {
        addRequirements(intake);
        this.intake = intake;
    }
    @Override
    public void execute() {
        intake.setState(true);
    }
//hasNote()
    @Override
    public boolean isFinished() {
        return intake.hasNote();
    }
    @Override
    public void end(boolean isInterupted) {
        intake.setState(false);
    }
}
