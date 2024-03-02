package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.arm.TeleopArm;
import frc.robot.commands.chassis.*;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ArmControls;
import frc.robot.constants.Constants.ChassisControls;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final PS4Controller driverController = new PS4Controller(
            Constants.ControllerConstants.DRIVER_CONTROLLER_PORT);
    private final XboxController armController = new XboxController(
            Constants.ControllerConstants.ARM_CONTROLLER_PORT);

    /* Chassis driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driverController, ChassisControls.ZERO_GYRO_BUTTON);

    /* Subsystems */
    private final Swerve swerve = new Swerve();
    private final Arm arm = new Arm();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        swerve.setDefaultCommand(
                new TeleopSwerve(
                        swerve,
                        () -> -driverController.getRawAxis(ChassisControls.TRANSLATION_AXIS),
                        () -> driverController.getRawAxis(ChassisControls.STRAFE_AXIS),
                        () -> driverController.getRawAxis(ChassisControls.ROTATION_AXIS)));

        arm.setDefaultCommand(new TeleopArm(arm,
                () -> armController.getRawAxis(ArmControls.LIFT_ARM_AXIS),
                () -> armController.getRawAxis(ArmControls.INTAKE) > ControllerConstants.TRIGGER_PULL_THRESHOLD,
                () -> armController.getRawAxis(ArmControls.SHOOT) > ControllerConstants.TRIGGER_PULL_THRESHOLD,
                () -> {
                    double shootSpeed = 0;
                    if (armController.getRawButton(ArmControls.REV_SHOOTER_FAST)) {
                        shootSpeed = 1.0;
                    } else if (armController.getRawButton(ArmControls.REV_SHOOTER_SLOW)) {
                        shootSpeed = 0.5;
                    }
                    return shootSpeed;
                }, armController));
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(swerve::zeroHeading));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // PathPlannerPath path = PathPlannerPath.fromPathFile("test");
        // return AutoBuilder.followPath(path);
        return new MoveForward(swerve);
    }
}
