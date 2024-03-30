package frc.robot.commands.auton.speakershoot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class RotateToSpeaker extends Command {
    private Swerve swerve;

    public RotateToSpeaker(Swerve swerve) {
        addRequirements(swerve);
        this.swerve = swerve;
    }


    @Override
    public void execute() {
        //do nothing
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stopAllMotion();
    }
}
