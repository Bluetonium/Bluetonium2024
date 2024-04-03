package frc.robot;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.TriggerButton;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.OutakeWithIntake;
import frc.robot.commands.ShootNote;
import frc.robot.commands.auton.deposit.Deposit;
import frc.robot.commands.auton.intake.IntakeNoteSequence;
import frc.robot.commands.auton.speakershoot.*;
import frc.robot.commands.teleop.TeleopAlignToAmp;
import frc.robot.commands.teleop.TeleopArm;
import frc.robot.commands.teleop.TeleopShooter;
import frc.robot.commands.teleop.TeleopSwerve;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ArmControls;
import frc.robot.constants.Constants.ChassisControls;
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

        /* Chassis Driver Buttons */
        private final JoystickButton zeroGyro = new JoystickButton(driverController, ChassisControls.ZERO_GYRO_BUTTON);
        private final JoystickButton alignWithAmp = new JoystickButton(driverController,
                        ChassisControls.ALIGN_TO_AMP_BUTTON);

        /* Arm Driver Buttons */
        private final TriggerButton intakeNote = new TriggerButton(armController, ArmControls.INTAKE);
        private final TriggerButton shootNote = new TriggerButton(armController, ArmControls.SHOOT);
        private final JoystickButton outakeWithIntake = new JoystickButton(armController,
                        ArmControls.OUTAKE_WITH_INTAKE);

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
                                                () -> -driverController.getRawAxis(ChassisControls.ROTATION_AXIS),
                                                () -> driverController
                                                                .getRawButton(ChassisControls.DRIVE_ROBOT_RELATIVE)));

                arm.setDefaultCommand(
                                new TeleopArm(arm,
                                                () -> armController.getRawAxis(ArmControls.LIFT_ARM_AXIS),
                                                () -> armController.getRawButton(ArmControls.ZERO_ARM_POSITION),
                                                () -> armController.getRawButton(ArmControls.STOW_ARM),
                                                () -> armController.getRawButton(ArmControls.GO_TO_AMP_POSITION),
                                                (double value) -> armController.setRumble(RumbleType.kBothRumble,
                                                                value)));
                shooter.setDefaultCommand(
                                new TeleopShooter(shooter,
                                                () -> armController.getRawButton(ArmControls.REV_SHOOTER_FAST)));

                configureButtonBindings();
                NamedCommands.registerCommand("ShootingSequence",
                                new ShootingSequence(swerve, arm, shooter, intake));
                NamedCommands.registerCommand("IntakeNoteSequence", new IntakeNoteSequence(arm, swerve, intake));
                NamedCommands.registerCommand("Deposit", new Deposit(shooter, intake));

                autoChooser = AutoBuilder.buildAutoChooser();
                SmartDashboard.putData("Auto Chooser", autoChooser);
        }

        public Command getAutonomousCommand() {

                return autoChooser.getSelected();
        }

        private void configureButtonBindings() {

                /*
                 * TODO consider putting more stuff here such as
                 * intake command
                 * shooting command
                 *
                 */
                /* Chassis Driver Buttons */
                zeroGyro.onTrue(new InstantCommand(swerve::zeroHeading));
                alignWithAmp.whileTrue(new TeleopAlignToAmp(swerve, () -> gyro.getYaw().getValue(),
                                () -> driverController.getRawAxis(ChassisControls.TRANSLATION_AXIS), limelight));

                /* Arm Driver Buttons */
                intakeNote.whileTrue(new IntakeNote(intake));
                outakeWithIntake.whileTrue(new OutakeWithIntake(intake));
                shootNote.whileTrue(new ShootNote(intake, shooter, () -> gyro.getYaw().getValue()));

        }
}
