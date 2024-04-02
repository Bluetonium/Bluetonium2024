package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class TeleopShooter extends Command {
    private Shooter shooter;
    private BooleanSupplier shooterButton;
    private Timer turboTimer;
    private BooleanSupplier turboButton;

    public TeleopShooter(Shooter shooter, BooleanSupplier shooterButton, BooleanSupplier turboButton) {
        addRequirements(shooter);
        this.shooter = shooter;
        this.shooterButton = shooterButton;
        this.turboButton = turboButton;
        turboTimer = new Timer();
        turboTimer.start();
    }

    @Override
    public void execute() {
        if (turboTimer.hasElapsed(0.1)) {
            turboTimer.restart();
        }

        shooter.setState(shooterButton.getAsBoolean()
                || (turboButton.getAsBoolean() && turboTimer.hasElapsed(0.05) && !turboTimer.hasElapsed(0.1)));
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopAllMotion();
    }
}
