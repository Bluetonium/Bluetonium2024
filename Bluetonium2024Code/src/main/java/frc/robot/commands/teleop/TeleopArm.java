package frc.robot.commands.teleop;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class TeleopArm extends Command {

    private Arm arm;
    private DoubleSupplier moveArmAxis;
    private BooleanSupplier zeroArm;
    private BooleanSupplier stowArm;
    private BooleanSupplier ampPosition;
    private DoubleConsumer controllerRumble;

    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis, BooleanSupplier zeroArm, BooleanSupplier stowArm, BooleanSupplier ampPosition, DoubleConsumer controllerRumble) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
        this.zeroArm = zeroArm;
        this.stowArm = stowArm;
        this.ampPosition = ampPosition;
        this.controllerRumble = controllerRumble;
    }

    @Override
    public void execute() {
        if(zeroArm.getAsBoolean()) {
            arm.zeroArm();
        }

        if(stowArm.getAsBoolean()) {
            if(arm.isArmZeroed()) {
            arm.setArmAngle(0);
            } else {
                controllerRumble.accept(1);
            }
        } else if (ampPosition.getAsBoolean()) {
            if(arm.isArmZeroed()) {
            arm.setArmAngle(ArmConstants.AMP_SCORING_POSOTION);
            } else {
                controllerRumble.accept(1);
            }
        }
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble() / 2,
                Constants.ControllerConstants.STICK_DEADBAND);
        if(armSpeed != 0) {
        arm.setArmSpeed(armSpeed);
        }

    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }
}
