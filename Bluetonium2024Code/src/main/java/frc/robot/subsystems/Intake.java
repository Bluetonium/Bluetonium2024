package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private CANSparkFlex mainIntakeMotor;

    public Intake() {
        mainIntakeMotor = new CANSparkFlex(IntakeConstants.INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        mainIntakeMotor.restoreFactoryDefaults();
        mainIntakeMotor.setInverted(true);

        mainIntakeMotor.setSmartCurrentLimit(IntakeConstants.INTAKE_CURRENT_LIMIT);
        mainIntakeMotor.setIdleMode(IntakeConstants.INTAKE_IDLE_MODE);
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
    public void turnOnIntake() {
        mainIntakeMotor.set(0.25);
    }

    public void turnOffIntake() {
        mainIntakeMotor.set(0);
    }

    public void shoot() {
        mainIntakeMotor.set(0.5);
    }

    @Override
    public void periodic() {
    }
}
