package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {
    private Arm arm;

    public AimAtSpeaker(Arm arm) {
        addRequirements(arm);
        this.arm = arm;
    }

    @Override
    public void initialize() {
        arm.setSpeakerPosition();
    }

    @Override
    public boolean isFinished() {
        return arm.isInPosition();
    }
}
