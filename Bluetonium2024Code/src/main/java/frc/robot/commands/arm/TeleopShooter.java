package frc.robot.commands.arm;

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
        if (shooterButton.getAsBoolean()) {
            shooter.setShooterVelocity(1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopAllMotion();
    }
}
