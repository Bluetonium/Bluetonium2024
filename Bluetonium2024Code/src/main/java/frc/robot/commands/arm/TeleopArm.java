package frc.robot.commands.arm;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Arm;

public class TeleopArm extends Command {
    private Arm arm;
    private DoubleSupplier moveArmAxis;
    private BooleanSupplier intakeButton;
    private DoubleSupplier shootingButton;

    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis, BooleanSupplier intakeButton, DoubleSupplier shootingButton) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
        this.intakeButton = intakeButton;
        this.shootingButton = shootingButton;
    }

    @Override
    public void execute() {
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble(),
                Constants.ControllerConstants.STICK_DEADBAND);

        armSpeed *= Constants.ArmConstants.MAX_ARM_VELOCITY;
        arm.setArmSpeed(armSpeed);

        double shootSpeed = moveArmAxis.getAsDouble();
        arm.setShooterVelocity(shootSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }

}
