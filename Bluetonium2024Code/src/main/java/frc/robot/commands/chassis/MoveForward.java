package frc.robot.commands.chassis;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class MoveForward extends Command {
    private int timer;
    private Swerve swerve;

    public MoveForward(Swerve swerve) {
        addRequirements(swerve);
        timer = 100;
        this.swerve = swerve;
    }

    @Override
    public void execute() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (SwerveModuleState state : states) {
            state.angle = new Rotation2d(0, 0);
            if (timer > 0) {
                timer--;
                state.speedMetersPerSecond = 1;
            } else {
                state.speedMetersPerSecond = 0;
            }
        }

        swerve.setModuleStates(states, true);

    }

}
