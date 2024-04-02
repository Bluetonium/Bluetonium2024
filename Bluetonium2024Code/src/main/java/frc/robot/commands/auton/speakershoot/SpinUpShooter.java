package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends Command {
    private Shooter shooter;

    public SpinUpShooter(Shooter shooter) {
        addRequirements(shooter);
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        shooter.setState(true);
    }

    @Override
    public boolean isFinished() {
        return shooter.readyToShoot(true);
    }

    @Override
    public void end(boolean interupted) {
        if (interupted) {
            shooter.setState(false);
        }
    }
}
