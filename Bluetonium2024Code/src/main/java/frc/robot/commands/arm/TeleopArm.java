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
    private BooleanSupplier shoot;
    // private ColorSensor colorSensor;

    public TeleopArm(Arm arm, DoubleSupplier moveArmAxis, BooleanSupplier intakeButton, BooleanSupplier shoot,
            DoubleSupplier shootingButton) {
        addRequirements(arm);
        this.arm = arm;
        this.moveArmAxis = moveArmAxis;
        this.intakeButton = intakeButton;
        this.shootingButton = shootingButton; // this aint right
        this.shoot = shoot;
        // colorSensor = new ColorSensor();
    }

    @Override
    public void execute() {
        double armSpeed = MathUtil.applyDeadband(moveArmAxis.getAsDouble() / 2,
                Constants.ControllerConstants.STICK_DEADBAND);

        arm.setArmSpeed(armSpeed);

        double shootSpeed = shootingButton.getAsDouble(); // revving idk man
        arm.setShooterVelocity(shootSpeed);
        // if (!colorSensor.proximityOverThreshold()) {
        if (intakeButton.getAsBoolean() || shoot.getAsBoolean()) {
            arm.setIntakeState(true);
        } else {
            arm.setIntakeState(false);
        }
        // } else {
        // armC
        // arm.setIntakeState(false);
        //

    }

    @Override
    public void end(boolean interrupted) {
        arm.stopAllMotion();
    }

}
