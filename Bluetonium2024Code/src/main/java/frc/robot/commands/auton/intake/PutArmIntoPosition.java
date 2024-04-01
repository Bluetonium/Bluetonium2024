package frc.robot.commands.auton.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class PutArmIntoPosition extends Command {

    public PutArmIntoPosition(Arm arm) {
        addRequirements(arm);
    }

    @Override
    public void execute() {
        //do nothing rn
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
