package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private CANSparkMax mainIntakeMotor;
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
}
