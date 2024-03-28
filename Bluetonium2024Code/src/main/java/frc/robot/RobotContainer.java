package frc.robot;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auton.deposit.Deposit;
import frc.robot.commands.auton.intake.IntakeNoteSequence;
import frc.robot.commands.auton.speakershoot.*;
import frc.robot.commands.teleop.*;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ArmControls;
import frc.robot.constants.Constants.ChassisControls;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.constants.Constants.MiscConstants;
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
        private final JoystickButton alignToAmp = new JoystickButton(driverController,
                        ChassisControls.ALIGN_TO_AMP_BUTTON);

        /* Subsystems */
        private final Swerve swerve;
        private final Arm arm;
        private final Intake intake;
        private final Shooter shooter;

        /* Other Stuff */
        private SendableChooser<Command> autoChooser; // there it is lol
        private Pigeon2 gyro;
        private NetworkTable limelight;

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                gyro = new Pigeon2(MiscConstants.PIGEON_ID, MiscConstants.CANIVORE_NAME);
                gyro.getConfigurator().apply(new Pigeon2Configuration());
                gyro.setYaw(0);
                limelight = NetworkTableInstance.getDefault().getTable(MiscConstants.LIMELIGHT_NAME);

                swerve = new Swerve(gyro);
                arm = new Arm();
                intake = new Intake();
                shooter = new Shooter();

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
                                                                ArmControls.INTAKE) > ControllerConstants.TRIGGER_PULL_THRESHOLD,
                                                () -> armController.getRawAxis(
                                                                ArmControls.SHOOT) >= ControllerConstants.TRIGGER_PULL_THRESHOLD,
                                                shooter::readyToShoot,
                                                () -> gyro.getYaw().getValue()));
                shooter.setDefaultCommand(
                                new TeleopShooter(shooter,
                                                () -> armController.getRawButton(ArmControls.REV_SHOOTER_FAST)));

                configureButtonBindings();
                NamedCommands.registerCommand("ShootingSequence",
                                new ShootingSequence(swerve, arm, shooter, intake, limelight));
                NamedCommands.registerCommand("IntakeNoteSequence", new IntakeNoteSequence(arm, swerve, intake));
                NamedCommands.registerCommand("Deposit", new Deposit(shooter, intake));

                autoChooser = AutoBuilder.buildAutoChooser();
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
                alignToAmp.whileTrue(new AlignToAmp(limelight, swerve,
                                () -> -driverController.getRawAxis(ChassisControls.STRAFE_AXIS),
                                (double amount) -> driverController.setRumble(RumbleType.kBothRumble, amount)));

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
