package frc.robot.commands.arm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Arm;

public class TeleopArm extends Command {
    private Arm arm;
    private DoubleSupplier moveArmAxis;

    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
    }

    @Override
    public void execute() {
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble() / 2,
                Constants.ControllerConstants.STICK_DEADBAND);

        arm.setArmSpeed(armSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }

}
