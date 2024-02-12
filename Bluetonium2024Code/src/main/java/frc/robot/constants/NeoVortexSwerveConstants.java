package frc.robot.constants;

import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.util.Units;

public class NeoVortexSwerveConstants {
    public static final double L1 = (8.14 / 1.0);
                /** SDS MK4i - (6.75 : 1) */
    public static final double L2 = (6.75 / 1.0);
                /** SDS MK4i - (6.12 : 1) */
    public static final double L3 = (6.12 / 1.0);

    public static final double ChosenRatio = L2;


    public static final double wheelDiameter = Units.inchesToMeters(4.0);
    public static final double wheelCircumference = wheelDiameter * Math.PI;
    public static final double angleGearRatio =  ((150.0 / 7.0) / 1.0);
    public static final double driveGearRatio = ChosenRatio;
    public static final double angleKP = 100.0;
    public static final double angleKI = 0.0;
    public static final double angleKD = 0.0;
    public static final boolean driveMotorInvert = false;
    public static final boolean angleMotorInvert = false;
    public static final SensorDirectionValue cancoderInvert = SensorDirectionValue.Clockwise_Positive; 
}
