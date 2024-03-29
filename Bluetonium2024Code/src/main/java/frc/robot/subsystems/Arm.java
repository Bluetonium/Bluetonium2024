package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ArmConstants;
import frc.robot.constants.Constants.AutonConstants;

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
     * Resets the encoder back to zero
     */

    public void zeroArm() {
        armEncoder.setPosition(0.0);
    }

    /**
     * 
     * @return Returns whether or not the hard limit is being pressed down
     */

    public boolean hardLimitReached() {
        return armMotor.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen).isPressed();
    }

    public void switchToBreak() {
        armMotor.setIdleMode(IdleMode.kBrake);
    }

    /**
     * 
     * @param angle Rotate to have the arm go in Rotation2d
     */
    public void setArmAngle(double angle) {
        armController.setReference(angle, ControlType.kPosition);
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

    public boolean isAtAngle(double angle) {
        return Math.abs((angle - getArmAngle()) / angle) <= AutonConstants.ALIGNMENT_TOLERACE;
    }
}