package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;
import frc.robot.constants.Constants.MiscConstants;

public class Intake extends SubsystemBase {
    private CANSparkFlex mainIntakeMotor;
    private DigitalInput beamBreak;

    public Intake() {
        mainIntakeMotor = new CANSparkFlex(IntakeConstants.FORWARD_INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        mainIntakeMotor.restoreFactoryDefaults();
        mainIntakeMotor.setInverted(true);

        mainIntakeMotor.setSmartCurrentLimit(IntakeConstants.INTAKE_CURRENT_LIMIT);
        mainIntakeMotor.setIdleMode(IntakeConstants.INTAKE_IDLE_MODE);
        beamBreak = new DigitalInput(MiscConstants.PROXIMITY_SENSOR_PORT);
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

    /**
     * 
     * @return If a note is currently in the intake
     */
    public boolean hasNote() {
        return !beamBreak.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Note in Intake", hasNote());
    }
}
