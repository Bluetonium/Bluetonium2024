package frc.robot.commands.auton.speakershoot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class AimAtSpeaker extends Command {// This isnt work rewrite all of it
    private Arm arm;
    private double desiredAngle = Double.MAX_VALUE;

    public AimAtSpeaker(Arm arm) {
        addRequirements(arm);
        this.arm = arm;
    }

    @Override
    public void execute() {
     //do nothing
    }

    @Override
    public boolean isFinished() {
        return arm.isAtAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        arm.setArmSpeed(0);
    }

}
