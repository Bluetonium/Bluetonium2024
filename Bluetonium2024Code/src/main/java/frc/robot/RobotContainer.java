package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auton.speakershoot.*;
import frc.robot.commands.teleop.*;
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
        private final Intake intake = new Intake();
        private final Shooter shooter = new Shooter();

        /* Named comamnds */
        // figured out how to add the named command for shootingSequence.java and
        // ZeroArm.java, named them their class names

        private SendableChooser<Command> autoChooser; // there it is lol

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                swerve.setDefaultCommand(
                                new TeleopSwerve(
                                                swerve,
                                                () -> -driverController.getRawAxis(ChassisControls.TRANSLATION_AXIS),
                                                () -> -driverController.getRawAxis(ChassisControls.STRAFE_AXIS),
                                                () -> -driverController.getRawAxis(ChassisControls.ROTATION_AXIS)));

                arm.setDefaultCommand(
                                new TeleopArm(arm,
                                                () -> armController.getRawAxis(ArmControls.LIFT_ARM_AXIS)));
                intake.setDefaultCommand(
                                new TeleopIntake(intake,
                                                () -> armController.getRawAxis(
                                                                ArmControls.SHOOT) >= ControllerConstants.TRIGGER_PULL_THRESHOLD,
                                                () -> armController.getRawButton(ArmControls.INTAKE),
                                                shooter::readyToShoot));
                shooter.setDefaultCommand(
                                new TeleopShooter(shooter,
                                                () -> armController.getRawButton(ArmControls.REV_SHOOTER_FAST)));

                configureButtonBindings();
                NamedCommands.registerCommand("ShootingSequence", new ShootingSequence(swerve, arm, shooter, intake));
                NamedCommands.registerCommand("shootNote", new ShootNote(intake, shooter, arm));
                NamedCommands.registerCommand("spinUpShooter", new SpinUpShooter(shooter));
                NamedCommands.registerCommand("rotateToSpeaker", new RotateToSpeaker(swerve));
                NamedCommands.registerCommand("prepareToShoot", new PrepareToShoot(swerve, arm, shooter));
                NamedCommands.registerCommand("spinUpShooter", new AimAtSpeaker(arm));

                autoChooser = new SendableChooser<>();
                SmartDashboard.putData("Auto Chooser", autoChooser);

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
                return autoChooser.getSelected();
        }
}
