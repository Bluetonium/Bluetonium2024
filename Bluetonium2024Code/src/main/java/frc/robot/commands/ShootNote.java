package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShootNote extends Command {
    private Intake intake;
    private Shooter shooter;
    private Timer waitAfterShoot;

    public ShootNote(Intake intake, Shooter shooter) {
        addRequirements(intake, shooter);
        this.intake = intake;
        this.shooter = shooter;
        waitAfterShoot = new Timer();
    }

    @Override
    public void initialize() {
        shooter.setState(true);
    }

    @Override
    public void execute() {
        if (shooter.readyToShoot()) {
            intake.shoot();
        }
    }

    @Override
    public boolean isFinished() {
        if (!intake.isNoteInIntake()) {
            waitAfterShoot.start();
        }
        return waitAfterShoot.hasElapsed(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setState(false);
        intake.turnOffIntake();
    }
}