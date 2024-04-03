package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private double desiredAngle = 0;
    private CANSparkMax armMotor; // right Arm
    private RelativeEncoder armEncoder;
    private boolean isZeroed = false;
    private SparkPIDController armController;

    public Arm() {
        armMotor = new CANSparkMax(ArmConstants.ARM_MOTOR_ID,
                MotorType.kBrushless);
        armMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        armMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        armMotor.setInverted(false);

        armEncoder = armMotor.getEncoder();
        armEncoder.setVelocityConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);
        armEncoder.setPositionConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);

        armController = armMotor.getPIDController();
        armController.setP(ArmConstants.ARM_KP);
        armController.setI(ArmConstants.ARM_KI);
        armController.setD(ArmConstants.ARM_KD);

    }

    /**
     * 
     * @param speed Speed of the arm in a [-1,1] range
     */
    public void setArmSpeed(double speed) {
        armMotor.set(speed);
    }

    /**
     * 
     * @param angle Rotate to have the arm go in Rotation2d
     */
    public void setArmAngle(double angle) {
        desiredAngle = angle;
        armController.setReference(desiredAngle, ControlType.kPosition);
    }

    public void setAmpPosition() {
        desiredAngle = ArmConstants.AMP_SCORING_POSITION;
        armController.setReference(desiredAngle, ControlType.kPosition);
    }

    public void setSpeakerPosition() {
        desiredAngle = ArmConstants.SPEAKER_SCORING_POSITION;
        armController.setReference(desiredAngle, ControlType.kPosition);
    }

    public void setIdlePosition() {
        desiredAngle = 0;
        armController.setReference(desiredAngle, ControlType.kPosition);
    }

    public boolean isInPosition() {
        return Math.abs(armEncoder.getPosition() - desiredAngle) < 1;
    }

    public void zeroArm() {
        isZeroed = true;
        armEncoder.setPosition(0);
    }

    public boolean isZeroed() {
        return isZeroed;
    }

    /**
     * Stops all the motors
     */
    public void stopAllMotion() {
        armMotor.stopMotor();
    }
}