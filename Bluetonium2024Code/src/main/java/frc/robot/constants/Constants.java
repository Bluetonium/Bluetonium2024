package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;

public final class Constants {
    public static final class MiscConstants {
        public static final int PIGEON_ID = 14;

        private MiscConstants() {
        }
    }

    public static final class ControllerConstants {
        public static final double STICK_DEADBAND = 0.1;
        public static final int DRIVER_CONTROLLER_PORT = 0;

        private ControllerConstants() {
        }
    }

    public static final class ChassisControls {
        public static final int ZERO_GYRO_BUTTON = XboxController.Button.kA.value;
        public static final int ALIGN_TO_TAG_BUTTON = XboxController.Button.kY.value;

        public static final int TRANSLATION_AXIS = XboxController.Axis.kLeftY.value;
        public static final int STRAFE_AXIS = XboxController.Axis.kLeftX.value;
        public static final int ROTATION_AXIS = XboxController.Axis.kRightX.value;
        public static final int ROBOT_RELATIVE = XboxController.Button.kLeftBumper.value;
        public static final int FAST_MODE = XboxController.Axis.kLeftTrigger.value;

        private ChassisControls() {
        }
    }

    public static final class Swerve {
        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int DRIVE_MOTOR_ID = 2;
            public static final int ANGLE_MOTOR_ID = 3;
            public static final int CAN_CODER_ID = 4;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.944091796875);
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
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.5341796875);
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
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(-0.627685546875);
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
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.85205078125);
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

        public static final int DRIVE_CURRENT_LIMIT = 65;
        /*
         * These values are used by the drive to ramp in open loop and closed loop
         * driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
         */
        public static final double OPEN_LOOP_RAMP = 0.1;
        public static final double CLOSED_LOOP_RAMP = 0.0;
        /* Drive Motor PID Values */
        public static final double DRIVE_KP = 5.5e-4; // TO: This must be tuned to specific robot

        public static final double DRIVE_KI = 0.0;
        public static final double DRIVE_PD = 0.0;

        public static final double DRIVE_FF = 0.0008;
        /* Swerve Profiling Values */
        /** Meters per Second */

        public static final double MAX_SPEED = 5;

        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = 8.0;

        /* Idle Modes */
        public static final IdleMode ANGLE_IDLE_MODE = IdleMode.kBrake;

        public static final IdleMode DRIVE_IDLE_MODE = IdleMode.kBrake;

        private Swerve() {
        }

    }

    private Constants() {
    }

}
