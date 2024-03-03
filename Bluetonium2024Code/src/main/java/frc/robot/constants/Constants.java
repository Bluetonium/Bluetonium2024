package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
    private Constants() {
    }

    public static final class ColorSensorConstants {
        private ColorSensorConstants() {
        }

        public static final double PROXIMITY_THRESHOLD = 1; // TODO figure out what the value should be
    }

    public static final class ControllerConstants {
        private ControllerConstants() {
        }

        public static final double STICK_DEADBAND = 0.1;
        public static final double TRIGGER_PULL_THRESHOLD = 0.3;

        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int ARM_CONTROLLER_PORT = 1;
    }

    public static final class ArmControls {
        private ArmControls() {
        }

        public static final int LIFT_ARM_AXIS = XboxController.Axis.kLeftY.value;
        public static final int REV_SHOOTER_FAST = XboxController.Button.kY.value;
        public static final int REV_SHOOTER_SLOW = XboxController.Button.kX.value;
        public static final int SHOOT = XboxController.Axis.kRightTrigger.value;
        public static final int INTAKE = XboxController.Axis.kLeftTrigger.value;
    }

    public static final class ChassisControls {
        private ChassisControls() {
        }

        public static final int ZERO_GYRO_BUTTON = PS4Controller.Button.kTriangle.value;
        public static final int TRANSLATION_AXIS = PS4Controller.Axis.kLeftY.value;
        public static final int STRAFE_AXIS = PS4Controller.Axis.kLeftX.value;
        public static final int ROTATION_AXIS = PS4Controller.Axis.kRightX.value;
    }

    public static final class ArmConstants {
        private ArmConstants() {
        }

        public static final int LEFT_ARM_MOTOR_ID = 15;
        public static final int RIGHT_ARM_MOTOR_ID = 14;
        public static final int ARM_CURRENT_LIMIT = 30;
        public static final IdleMode ARM_IDLE_MODE = IdleMode.kBrake;
        public static final double MAX_ARM_VELOCITY = 20;// RPM

        public static final double ARM_GEAR_RATIO = 4096 / 14.0;

        public static final int FORWARD_INTAKE_MOTOR_ID = 18;
        public static final int BACK_INTAKE_MOTOR_ID = 19;
        public static final int INTAKE_CURRENT_LIMIT = 30;
        public static final IdleMode INTAKE_IDLE_MODE = IdleMode.kBrake;

        public static final int FORWARD_SHOOT_MOTOR_ID = 16;
        public static final int BACK_SHOOT_MOTOR_ID = 17;
        public static final int SHOOTER_CURRENT_LIMIT = 30;
        public static final IdleMode SHOOTER_IDLE_MODE = IdleMode.kBrake;

    }

    public static final class Swerve {
        private Swerve() {
        }

        public static final int PIGEON_ID = 14;

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
                new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0), // front left
                new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0), // front right
                new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0), // back left
                new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0));// back right

        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = NeoVortexSwerveConstants.CHOSEN_RATIO;
        public static final double ANGLE_GEAR_RATIO = NeoVortexSwerveConstants.ANGLE_GEAR_RATIO;

        /* Swerve Current Limiting */
        public static final int ANGLE_CURRENT_LIMIT = 25;
        public static final int DRIVE_CURRENT_LIMIT = 35;

        /*
         * These values are used by the drive to ramp in open loop and closed loop
         * driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
         */
        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;

        /* Drive Motor PID Values */
        public static final double DRIVE_KP = 6e-5; // TO: This must be tuned to specific robot
        public static final double DRIVE_KI = 0.0;
        public static final double DRIVE_PD = 0.0;
        public static final double DRIVE_FF = 0.00884433962;

        /* Swerve Profiling Values */
        /** Meters per Second */

        public static final double MAX_SPEED = 5;
        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = 5.0;

        /* Idle Modes */
        public static final IdleMode ANGLE_IDLE_MODE = IdleMode.kCoast;
        public static final IdleMode DRIVE_IDLE_MODE = IdleMode.kBrake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            private Mod0() {
            }

            public static final int DRIVE_MOTOR_ID = 2;
            public static final int ANGLE_MOTOR_ID = 3;
            public static final int CAN_CODER_ID = 4;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(-0.123779296875);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            private Mod1() {
            }

            public static final int DRIVE_MOTOR_ID = 5;
            public static final int ANGLE_MOTOR_ID = 6;
            public static final int CAN_CODER_ID = 7;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.430908203125);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            private Mod2() {
            }

            public static final int DRIVE_MOTOR_ID = 8;
            public static final int ANGLE_MOTOR_ID = 9;
            public static final int CAN_CODER_ID = 10;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(-0.273193359375);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            private Mod3() {
            }

            public static final int DRIVE_MOTOR_ID = 11;
            public static final int ANGLE_MOTOR_ID = 12;
            public static final int CAN_CODER_ID = 13;
            public static final Rotation2d angleOffset = Rotation2d.fromRotations(0.38818359375);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(DRIVE_MOTOR_ID,
                    ANGLE_MOTOR_ID,
                    CAN_CODER_ID, angleOffset);
        }

    }

}
