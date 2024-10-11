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

        public TeleopSwerve(Swerve swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup,
                        DoubleSupplier rotationSup, BooleanSupplier fieldRelative) {
                addRequirements(swerve);
                this.swerve = swerve;
                this.translationSup = translationSup;
                this.strafeSup = strafeSup;
                this.rotationSup = rotationSup;
                this.fieldRelative = fieldRelative;
        }

        @Override
        public void execute() {
                /* Get Values, Deadband */
                double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND);
                double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND);
                double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(),
                                Constants.ControllerConstants.STICK_DEADBAND);
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