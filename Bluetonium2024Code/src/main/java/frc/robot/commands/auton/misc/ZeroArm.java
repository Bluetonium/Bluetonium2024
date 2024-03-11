package frc.robot.commands.auton.misc;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class ZeroArm extends Command {
    private Arm arm;

    public ZeroArm(Arm arm) {
        this.arm = arm;
    }

    @Override
    public void execute() {
        arm.setArmSpeed(-0.2);
    }

    @Override
    public void end(boolean interupted) {
        arm.setArmSpeed(0);
    }

    @Override
    public boolean isFinished() {
        if (arm.limitReached()) {
            arm.zeroArm();
            arm.setSoftLimitState(true);
            return true;
        }
        return false;
    }
}
