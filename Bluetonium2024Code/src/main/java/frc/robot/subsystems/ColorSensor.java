package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;

public class ColorSensor extends SubsystemBase {
    private ColorSensorV3 colorSensor;
    private void setupColorSensor() {
        colorSensor = new ColorSensorV3(Port.kOnboard); //bdonkers
    }
    public ColorSensor() {
        setupColorSensor();
    }
    /**
     * 
     * @return returns an integer that represents the proximity of an object to the color sensor
     */
    private int getColorSensorProximity() {
        return colorSensor.getProximity(); // yeah i felt like making it its own method despite it only being used once lol
    }
    /**
     * 
     * @return Returns "true" if the proximity is over the threshold.
     */
    public boolean ProximityOverThreshold() {
        return getColorSensorProximity()>Constants.ColorSensorConstants.PROXIMITY_THRESHOLD;
    }
}
