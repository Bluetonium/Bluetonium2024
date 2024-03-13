package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch.Type;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private CANSparkMax armMotor; // right Arm
    private RelativeEncoder armEncoder;

    private SparkPIDController armController;

    CANSparkMax followerArmMotor; // left Arm

    public Arm() {
        armMotor = new CANSparkMax(ArmConstants.ARM_MOTOR_ID,
                MotorType.kBrushless);
        armMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        armMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        armMotor.setSoftLimit(SoftLimitDirection.kForward,
                (float) (Constants.ArmConstants.ARM_FORWARD_LIMIT * (1 / Constants.ArmConstants.ARM_GEAR_RATIO)));
        armMotor.setSoftLimit(SoftLimitDirection.kReverse,
                (float) (Constants.ArmConstants.ARM_REVERSED_LIMIT * (1 / Constants.ArmConstants.ARM_GEAR_RATIO)));

        armEncoder = armMotor.getEncoder();
        armEncoder.setVelocityConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);
        armEncoder.setPositionConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);

        AbsoluteEncoder armAbsoluteEncoder = armMotor
                .getAbsoluteEncoder(com.revrobotics.SparkAbsoluteEncoder.Type.kDutyCycle);
        armAbsoluteEncoder.setPositionConversionFactor(ArmConstants.ABSOLUTE_ENCODER_CONVERSATION);
        armEncoder.setPosition(armAbsoluteEncoder.getPosition() - ArmConstants.ABSOLUTE_ENCODER_OFFSET);

        armController = armMotor.getPIDController();
    }

    /**
     * 
     * @param speed Speed of the arm in RPM
     */
    public void setArmSpeed(double speed) {
        armMotor.set(speed);
    }

    /**
     * 
     * @param angle Rotate to have the arm go in Rotation2d
     */
    public void setArmAngle(Rotation2d angle) {
        armController.setReference(angle.getRotations(), ControlType.kPosition);
    }

    /**
     * 
     * @return Returns the arm position as a Rotation2d
     */
    public double getArmAngle() {
        return armEncoder.getPosition();
    }

    /**
     * Stops all the motors
     */
    public void stopAllMotion() {
        armMotor.stopMotor();
    }

    public boolean limitReached() {
        return armMotor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm angle", getArmAngle());
    }
}