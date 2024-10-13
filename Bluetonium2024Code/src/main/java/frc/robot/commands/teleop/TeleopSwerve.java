package frc.robot.commands.teleop;

import frc.robot.constants.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;

public class TeleopSwerve extends Command {
        private Swerve swerve;
        private DoubleSupplier translationSup;
        private DoubleSupplier strafeSup;
        private DoubleSupplier rotationSup;
        private BooleanSupplier fieldRelative;
        private DoubleSupplier fastMode;
        private double speedModifier; // higher value = slower :)

        public TeleopSwerve(Swerve swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup,
                        DoubleSupplier rotationSup, BooleanSupplier fieldRelative, DoubleSupplier fastMode) {
                addRequirements(swerve);
                this.swerve = swerve;
                this.translationSup = translationSup;
                this.strafeSup = strafeSup;
                this.rotationSup = rotationSup;
                this.fieldRelative = fieldRelative;
                this.fastMode = fastMode;
        }

        @Override
        public void execute() {
                /* Get Values, Deadband */
                if (fastMode.getAsDouble() > 0.7) {
                        speedModifier = 1.0;
                } else {
                        speedModifier = 1.7;
                }
                double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND) / speedModifier;
                double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND) / speedModifier;
                double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND) / speedModifier;
                /* Drive */
                swerve.drive(
                                new Translation2d(translationVal, strafeVal).times(Constants.Swerve.MAX_SPEED),
                                rotationVal * Constants.Swerve.MAX_ANGULAR_VELOCITY,
                                fieldRelative.getAsBoolean(),
                                true);

        }

        @Override
        public void end(boolean interrupted) {
                swerve.stopAllMotion();
        }
}