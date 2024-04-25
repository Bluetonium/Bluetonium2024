package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;

public final class Constants {

    public static final class MiscConstants {
        public static final int PIGEON_ID = 14;
        public static final String CANIVORE_NAME = "CANTivore";

        public static final String LIMELIGHT_NAME = "limelight";
        public static final int AMP_PIPELINE = 0;
        public static final int CENTER_SPEAKER_PIPELINE = 1;
        public static final int LOCALIZATION_PIPELINE = 2;

        public static final int BEAM_BREAK_PORT = 0;

        private MiscConstants() {
        }
    }

    public static final class ControllerConstants {
        public static final double STICK_DEADBAND = 0.1;

        public static final double TRIGGER_PULL_THRESHOLD = 0.3;
        public static final int DRIVER_CONTROLLER_PORT = 0;

        public static final int ARM_CONTROLLER_PORT = 1;

        public static final double OVERRIDE_TIME = 2;

        private ControllerConstants() {
        }
    }

    public static final class ArmControls {
        public static final int LIFT_ARM_AXIS = XboxController.Axis.kLeftY.value;
        public static final int SHOOT = XboxController.Axis.kRightTrigger.value;
        public static final int INTAKE = XboxController.Axis.kLeftTrigger.value;
        public static final int REV_SHOOTER_FAST = XboxController.Button.kY.value;

        public static final int OUTAKE_WITH_INTAKE = XboxController.Button.kLeftBumper.value;

        public static final int ZERO_ARM_POSITION = XboxController.Button.kX.value;
        // TODO make this not a dogshit control scheme
        public static final int STOW_ARM = XboxController.Button.kA.value;
        public static final int GO_TO_AMP_POSITION = XboxController.Button.kB.value;

        private ArmControls() {
        }
    }

    public static final class ChassisControls {
        public static final int ZERO_GYRO_BUTTON = PS4Controller.Button.kTriangle.value;
        public static final int ALIGN_TO_AMP_BUTTON = PS4Controller.Button.kSquare.value;
        public static final int DRIVE_ROBOT_RELATIVE = PS4Controller.Button.kCircle.value;

        public static final int TRANSLATION_AXIS = PS4Controller.Axis.kLeftY.value;
        public static final int STRAFE_AXIS = PS4Controller.Axis.kLeftX.value;
        public static final int ROTATION_AXIS = PS4Controller.Axis.kRightX.value;

        private ChassisControls() {
        }
    }

    public static final class IntakeConstants {
        public static final int INTAKE_MOTOR_ID = 15;
        public static final int INTAKE_CURRENT_LIMIT = 30;
        public static final IdleMode INTAKE_IDLE_MODE = IdleMode.kBrake;

        private IntakeConstants() {
        }
    }

    public static final class ShooterConstants {
        public static final int SHOOT_MOTOR_ID = 14;
        public static final int SHOOTER_CURRENT_LIMIT = 30;
        public static final IdleMode SHOOTER_IDLE_MODE = IdleMode.kBrake;
        public static final int SPEAKER_SHOOTING_VELOCITY = 5000;
        public static final int AMP_SHOOTING_VELOCITY = 1000;

        private ShooterConstants() {
        }
    }

    public static final class ArmConstants {
        public static final int ARM_MOTOR_ID = 13;
        public static final int ARM_CURRENT_LIMIT = 40;
        public static final IdleMode ARM_IDLE_MODE = IdleMode.kBrake;
        public static final double ARM_GEAR_RATIO = 4096 / 14.0;

        public static final double ALIGNMENT_TOLERACE = 0.1;// in rotations

        public static final double AMP_SCORING_POSITION = 0.2;// TODO set this up
        public static final double SPEAKER_SCORING_POSITION = 0.2;// TODO set this up
        public static final double ARM_SPEED = 0.1; // TODO: change this value; it is far too high for the PID

        public static final double ARM_KP = 0.9;
        public static final double ARM_KI = 0;
        public static final double ARM_KD = 0.0002;
        public static final double ARM_FF = 0.0005;

        private ArmConstants() {
        }
    }

    public static final class SwerveConstants {
        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int DRIVE_MOTOR_ID = 2;

            public static final int ANGLE_MOTOR_ID = 3;
            public static final int CAN_CODER_ID = 4;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.123046875);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);

            private Mod0() {
            }
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int DRIVE_MOTOR_ID = 5;

            public static final int ANGLE_MOTOR_ID = 6;
            public static final int CAN_CODER_ID = 7;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(-0.4189453125);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);

            private Mod1() {
            }
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int DRIVE_MOTOR_ID = 8;

            public static final int ANGLE_MOTOR_ID = 9;
            public static final int CAN_CODER_ID = 10;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.268310546875);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);

            private Mod2() {
            }
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int DRIVE_MOTOR_ID = 11;
            public static final int ANGLE_MOTOR_ID = 12;
            public static final int CAN_CODER_ID = 13;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(-0.38623046875);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);

            private Mod3() {
            }
        }

        /* Drivetrain Constants */
        public static final double TRACK_WIDTH = Units.inchesToMeters(23.5);

        public static final double WHEEL_BASE = Units.inchesToMeters(23.5);
        public static final double BASE_RADIUS = Math.sqrt(Math.pow(TRACK_WIDTH / 2, 2) + Math.pow(WHEEL_BASE / 2, 2));

        /*
         * Swerve Kinematics
         * No need to ever change this unless you are not doing a traditional
         * rectangular/square 4 module swerve
         */
        public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
                new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0), // front left
                new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0), // front right
                new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0), // back left
                new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0));// back right
        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = NeoVortexSwerveConstants.CHOSEN_RATIO;

        public static final double ANGLE_GEAR_RATIO = NeoVortexSwerveConstants.ANGLE_GEAR_RATIO;
        /* Swerve Current Limiting */
        public static final int ANGLE_CURRENT_LIMIT = 40;

        public static final int DRIVE_CURRENT_LIMIT = 70;
        /*
         * These values are used by the drive to ramp in open loop and closed loop
         * driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
         */
        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;
        /* Drive Motor PID Values */
        public static final double DRIVE_KP = 2e-4; // TO: This must be tuned to specific robot

        public static final double DRIVE_KI = 0.0;
        public static final double DRIVE_PD = 0.0;

        public static final double DRIVE_FF = 0.00015;
        /* Swerve Profiling Values */
        /** Meters per Second */

        public static final double MAX_SPEED = 5;

        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = 5.0;

        /* Idle Modes */
        public static final IdleMode ANGLE_IDLE_MODE = IdleMode.kCoast;

        public static final IdleMode DRIVE_IDLE_MODE = IdleMode.kBrake;

        private SwerveConstants() {
        }

    }

    private Constants() {
    }

}
