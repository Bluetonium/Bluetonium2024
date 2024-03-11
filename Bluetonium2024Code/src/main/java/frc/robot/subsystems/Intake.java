package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;
import frc.robot.constants.Constants.SensorConstants;

public class Intake extends SubsystemBase {
    private CANSparkMax mainIntakeMotor;
    private DigitalInput proximitySensor;
    CANSparkMax followerIntakeMotor;

    public Intake() {
        mainIntakeMotor = new CANSparkMax(IntakeConstants.FORWARD_INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        mainIntakeMotor.setInverted(true);

        mainIntakeMotor.setSmartCurrentLimit(IntakeConstants.INTAKE_CURRENT_LIMIT);
        mainIntakeMotor.setIdleMode(IntakeConstants.INTAKE_IDLE_MODE);

        followerIntakeMotor = new CANSparkMax(IntakeConstants.BACK_INTAKE_MOTOR_ID, MotorType.kBrushless);
        followerIntakeMotor.setSmartCurrentLimit(IntakeConstants.INTAKE_CURRENT_LIMIT);
        followerIntakeMotor.setIdleMode(IntakeConstants.INTAKE_IDLE_MODE);
        followerIntakeMotor.follow(mainIntakeMotor, true);

        proximitySensor = new DigitalInput(SensorConstants.PROXIMITY_SENSOR_PORT);
    }

    /**
     * Stops all the motors
     */
    public void stopAllMotion() {
        mainIntakeMotor.stopMotor();
    }

    /**
     * 
     * @param active if the motor should be spinning or not
     */
    public void setState(boolean state) {
        if (state) {
            mainIntakeMotor.set(1);
        } else {
            mainIntakeMotor.set(0);
        }
    }

    /**
     * 
     * @return If a note is currently in the intake
     */
    public boolean hasNote() {
        return proximitySensor.get();
    }
}
