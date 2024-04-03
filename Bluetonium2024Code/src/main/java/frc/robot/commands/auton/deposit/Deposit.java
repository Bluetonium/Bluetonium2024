package frc.robot.commands.auton.deposit;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Deposit extends Command {
    private Shooter shooter;
    private Intake intake;

    Timer timer;

    public Deposit(Shooter shooter, Intake intake) {
        addRequirements(shooter, intake);
        this.shooter = shooter;
        this.intake = intake;

        timer = new Timer();
    }

    @Override
    public void execute() {
        shooter.setState(true);
        intake.shoot();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(1.5); // better way but for now is good
        // remeber to start the timer, id porbably start it once the intake doesnt
        // detect a note anymore
    }

    @Override
    public void end(boolean interupted) {
        if (interupted) {
            shooter.setState(false);
            intake.turnOffIntake();// only turn it off if the command is intrupted rather than always?
        }
    }
}
