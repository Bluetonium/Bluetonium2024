package frc.robot.commands.auton.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class PutArmIntoPosition extends Command {
    private Arm arm;

    public PutArmIntoPosition(Arm arm) {
        addRequirements(arm);
        this.arm = arm;
    }

    @Override
    public void initialize() {
        arm.setIdlePosition();
    }

    @Override
    public boolean isFinished() {
        return arm.isInPosition();
    }

}
