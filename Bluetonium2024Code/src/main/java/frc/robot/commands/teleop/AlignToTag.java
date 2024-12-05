package frc.robot.commands.teleop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve;

public class AlignToTag extends PIDCommand {
    private PIDController pid;

    private static final double P = 0.2;
    private static final double I = 0;
    private static final double D = 0.02;

    public AlignToTag(Limelight limelight, Swerve swerve, DoubleSupplier translationAxis) {
        super(new PIDController(P, I, D), limelight::getTx, 0.0,
                output -> swerve.drive(
                        new Translation2d(MathUtil.applyDeadband(translationAxis.getAsDouble(),
                                ControllerConstants.STICK_DEADBAND), -output),
                        0, false,
                        true),
                swerve);

        pid = getController();
        pid.setTolerance(3);
        /*
         * SmartDashboard.putNumber("alignmentSpeed", 0.2);
         * SmartDashboard.putNumber("errorRange", 1);
         * 
         * SmartDashboard.putNumber("P", 0.3);
         * SmartDashboard.putNumber("I", 2000.0);
         * SmartDashboard.putNumber("D", 0.0);
         * 
         * SmartDashboard.updateValues();
         */
    }

    /*
     * @Override
     * public void execute() {
     * double p = SmartDashboard.getNumber("P", 0.0);
     * double i = SmartDashboard.getNumber("I", 0.0);
     * double d = SmartDashboard.getNumber("D", 0.0);
     * 
     * pid.setPID(p, i, d);
     * 
     * super.execute();
     * 
     * }
     */
}
