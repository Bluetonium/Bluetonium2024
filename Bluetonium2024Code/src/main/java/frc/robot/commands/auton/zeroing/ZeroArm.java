package frc.robot.commands.auton.zeroing;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class ZeroArm extends Command {
    Arm arm;
    public ZeroArm(Arm arm) {
        this.arm = arm;
    }
    @Override
    public void initialize() {
        arm.setArmSpeed(0.2); //motor value may need to be negative, will review in testing

    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void end(boolean isInterupted) {
        arm.setArmSpeed(0);
        arm.setArmSpeed(0);
    }
}
