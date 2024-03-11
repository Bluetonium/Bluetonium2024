package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
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
    private CANSparkMax mainArmMotor; // right Arm
    private RelativeEncoder mainArmEncoder;

    private SparkPIDController mainArmController;

    CANSparkMax followerArmMotor; // left Arm

    public Arm() {
        mainArmMotor = new CANSparkMax(ArmConstants.LEFT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        mainArmMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        mainArmMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        mainArmMotor.setSoftLimit(SoftLimitDirection.kReverse,
                (float) (Constants.ArmConstants.ARM_REVERSED_LIMIT * Constants.ArmConstants.ARM_GEAR_RATIO));
        setSoftLimitState(false);

        mainArmEncoder = mainArmMotor.getEncoder();
        mainArmEncoder.setVelocityConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);
        mainArmEncoder.setPositionConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);
        mainArmController = mainArmMotor.getPIDController();

        followerArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        followerArmMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        followerArmMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        followerArmMotor.follow(mainArmMotor, true);
    }

    /***
     * Sets the arm position
     */
    public void zeroArm() {
        mainArmEncoder.setPosition(0);
    }

    /**
     * 
     * @param speed Speed of the arm in RPM
     */
    public void setArmSpeed(double speed) {
        mainArmMotor.set(speed);
    }

    /**
     * 
     * @param angle Rotate to have the arm go in Rotation2d
     */
    public void setArmAngle(Rotation2d angle) {
        mainArmController.setReference(angle.getRotations(), ControlType.kPosition);
    }

    /**
     * 
     * @return Returns the arm position as a Rotation2d
     */
    public double getArmAngle() {
        return mainArmEncoder.getPosition();
    }

    /**
     * Stops all the motors
     */
    public void stopAllMotion() {
        mainArmMotor.stopMotor();
    }

    public boolean limitReached() {
        return mainArmMotor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    public void setSoftLimitState(boolean state) {
        mainArmMotor.enableSoftLimit(SoftLimitDirection.kReverse, state);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm angle", getArmAngle());
    }
}