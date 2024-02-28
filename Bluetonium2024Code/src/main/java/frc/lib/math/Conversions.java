package frc.lib.math;

public class Conversions {
    private Conversions() {
    }

    /**
     * @param wheelRPM      Wheel Velocity: (in Rotations per minute)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Meters per Second)
     */
    public static double rpmToMps(double wheelRPM, double circumference) {
        return (wheelRPM / 60) * circumference;
    }

    /**
     * @param wheelMPS      Wheel Velocity: (in Meters per Second)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Rotations per minute)
     */
    public static double mpsToRpm(double wheelMPS, double circumference) {
        return (wheelMPS * 60) / circumference;
    }

    /**
     * @param wheelRotations Wheel Position: (in Rotations)
     * @param circumference  Wheel Circumference: (in Meters)
     * @return Wheel Distance: (in Meters)
     */
    public static double rotationsToMeters(double wheelRotations, double circumference) {
        return wheelRotations * circumference;
    }

    /**
     * @param wheelMeters   Wheel Distance: (in Meters)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Position: (in Rotations)
     */
    public static double metersToRotations(double wheelMeters, double circumference) {
        return wheelMeters / circumference;
    }
}