package frc.robot.commands.arm;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ColorSensor;

public class TeleopArm extends Command {
    private Arm arm;
    private DoubleSupplier moveArmAxis;
    private BooleanSupplier intakeButton;
    private DoubleSupplier shootingButton;
    private ColorSensor colorSensor;

    private GenericHID armController;
    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis, BooleanSupplier intakeButton, DoubleSupplier shootingButton,GenericHID armController) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
        this.intakeButton = intakeButton;
        this.shootingButton = shootingButton; //this aint right
        this.armController = armController;
    }

    @Override
    public void execute() {
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble(),
                Constants.ControllerConstants.STICK_DEADBAND);

        armSpeed *= Constants.ArmConstants.MAX_ARM_VELOCITY;
        arm.setArmSpeed(armSpeed);

        double shootSpeed = shootingButton.getAsDouble(); //revving idk man
        arm.setShooterVelocity(shootSpeed);
        if (!colorSensor.proximityOverThreshold()) {
            arm.setIntakeState(intakeButton.getAsBoolean());
        } else {
            armController.setRumble(RumbleType.kBothRumble,1.0); //this might* work (* this might not work)
        }
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }

}
