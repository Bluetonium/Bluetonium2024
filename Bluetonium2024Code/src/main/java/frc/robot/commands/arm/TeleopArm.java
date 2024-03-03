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
    private DoubleSupplier shooterSpeed;
    private BooleanSupplier shoot;
    private BooleanSupplier outTake;

    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis, BooleanSupplier intakeButton, BooleanSupplier outTake,
            BooleanSupplier shoot,
            DoubleSupplier shooterSpeed) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
        this.intakeButton = intakeButton;
        this.shooterSpeed = shooterSpeed; // this aint right
        this.shoot = shoot;
        this.outTake = outTake;
    }

    @Override
    public void execute() {
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble() / 2,
                Constants.ControllerConstants.STICK_DEADBAND);

        arm.setArmSpeed(armSpeed);

        arm.setShooterVelocity(shooterSpeed.getAsDouble());
        if (intakeButton.getAsBoolean() || shoot.getAsBoolean()) {
            arm.setIntakeState(1);
        } else if (outTake.getAsBoolean()) {
            arm.setIntakeState(-1);
        } else {
            arm.setIntakeState(0);
        }

    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }

}
