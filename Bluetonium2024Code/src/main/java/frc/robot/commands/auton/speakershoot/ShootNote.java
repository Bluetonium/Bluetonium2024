package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShootNote extends Command {
    private static final double SHOOT_TIME = 2;// time to wait to shoot

    private Intake intake;
    private Shooter shooter;
    private Arm arm;
    private Timer timer;

    public ShootNote(Intake intake, Shooter shooter, Arm arm) {
        addRequirements(intake, shooter, arm);
        this.intake = intake;
        this.shooter = shooter;
        this.arm = arm;
        timer = new Timer();

    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        intake.setState(true);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(SHOOT_TIME);
    }

    @Override
    public void end(boolean isInterupted) {
        intake.setState(false);
        shooter.setState(false);

        if (!isInterupted) {
            arm.setIdlePosition();
        }

    }

}
