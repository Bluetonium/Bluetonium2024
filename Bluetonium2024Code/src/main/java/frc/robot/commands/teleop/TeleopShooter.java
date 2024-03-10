package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class TeleopShooter extends Command {
    private Shooter shooter;
    private BooleanSupplier shooterButton;

    public TeleopShooter(Shooter shooter, BooleanSupplier shooterButton) {
        addRequirements(shooter);
        this.shooter = shooter;
        this.shooterButton = shooterButton;
    }

    @Override
    public void execute() {
        shooter.setShooterState(shooterButton.getAsBoolean());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopAllMotion();
    }
}
