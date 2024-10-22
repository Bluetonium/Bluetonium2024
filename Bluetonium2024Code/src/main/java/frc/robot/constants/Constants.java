package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

public final class Constants {

    public static final class AutonConstants {
        public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
        public static final PIDConstants ANGLE_PID = new PIDConstants(0.4, 0, 0.01);

        public static final HolonomicPathFollowerConfig PATHPLANNER_CONFIG = new HolonomicPathFollowerConfig(
                TRANSLATION_PID,
                ANGLE_PID,
                4.5,
                16.6170,
                new ReplanningConfig());

        private AutonConstants() {

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

        public static final int TRANSLATION_AXIS = XboxController.Axis.kLeftY.value;
        public static final int STRAFE_AXIS = XboxController.Axis.kLeftX.value;
        public static final int ROTATION_AXIS = XboxController.Axis.kRightX.value;
        public static final int ROBOT_RELATIVE = XboxController.Button.kLeftBumper.value;
        public static final int FAST_MODE = XboxController.Axis.kLeftTrigger.value;

        private ChassisControls() {
        }
    }

    private Constants() {
    }

}
