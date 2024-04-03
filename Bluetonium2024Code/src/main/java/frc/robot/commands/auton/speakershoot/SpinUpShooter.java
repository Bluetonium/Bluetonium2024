package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends Command {
    /*
     * maybe make this both a command for auto and teleop, have it use the yaw to
     * determine what speed to go to
     * also probably move the what speed to go to in the actual shooter class bc
     * like thats smart
     */
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
        return shooter.readyToShoot();
    }

    @Override
    public void end(boolean interupted) {
        if (interupted) {
            shooter.setState(false);
        }
    }
}
