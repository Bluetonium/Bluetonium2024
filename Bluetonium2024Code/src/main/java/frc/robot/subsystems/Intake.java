package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;
import frc.robot.constants.Constants.MiscConstants;

public class Intake extends SubsystemBase {
    private CANSparkFlex mainIntakeMotor;
    private DigitalInput beamBreak;

    public Intake() {
        mainIntakeMotor = new CANSparkFlex(IntakeConstants.INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        mainIntakeMotor.restoreFactoryDefaults();
        mainIntakeMotor.setInverted(false);

        mainIntakeMotor.setSmartCurrentLimit(IntakeConstants.INTAKE_CURRENT_LIMIT);
        mainIntakeMotor.setIdleMode(IntakeConstants.INTAKE_IDLE_MODE);

        beamBreak = new DigitalInput(MiscConstants.BEAM_BREAK_PORT);
    }

    public void stopAllMotion() {
        mainIntakeMotor.stopMotor();
    }

    public void turnOnIntake() {
        mainIntakeMotor.set(0.5);
    }

    public boolean isNoteInIntake() {
        return beamBreak.get();
    }

    public void reverseIntake() {
        mainIntakeMotor.set(-1);
    }

    public void turnOffIntake() {
        mainIntakeMotor.set(0);
    }

    public void shoot() {
        mainIntakeMotor.set(0.5);
    }
}
