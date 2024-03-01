package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private CANSparkMax mainArmMotor; // right Arm
    private SparkPIDController mainArmController;
    CANSparkMax followerArmMotor; // left Arm

    private CANSparkMax mainIntakeMotor;
    CANSparkMax followerIntakeMotor;

    private CANSparkMax mainShooterMotor; // make sure one of these is inverted
    private SparkPIDController mainShooterController;
    CANSparkMax followerShooterMotor;

    public Arm() {
        setupArmMotors();
        setupIntakeMotors();
        setupShooterMotors();
    }

    private void setupArmMotors() {
        followerArmMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        followerArmMotor.setIdleMode(IdleMode.kBrake);

        mainArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        mainArmMotor.setIdleMode(IdleMode.kBrake);

        RelativeEncoder mainArmEncoder = mainArmMotor.getEncoder();
        mainArmEncoder.setVelocityConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);

        mainArmController = mainArmMotor.getPIDController();

        followerArmMotor.follow(mainArmMotor, true);
    }

    private void setupShooterMotors() {
        followerShooterMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        mainShooterMotor = new CANSparkMax(ArmConstants.RIGHT_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        followerShooterMotor.follow(mainArmMotor, true);
        mainShooterController = mainShooterMotor.getPIDController();
    }

    private void setupIntakeMotors() {
        mainIntakeMotor = new CANSparkMax(ArmConstants.LEFT_INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        followerArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID, MotorType.kBrushless);
        followerArmMotor.follow(mainIntakeMotor);
    }

    /**
     * 
     * @param speed speed of the arm in RPM
     */
    public void setArmSpeed(double speed) {
        mainArmController.setReference(speed, ControlType.kVelocity);
    }

    /***
     * 
     * @param speed speed [-1,1]
     */
    public void setShooterVelocity(double speed) {
        mainShooterMotor.set(speed);
    }

    /**
     * 
     * @param active if the motor should be spinning or not
     */
    public void setIntakeState(boolean active) {
        if (active) {
            mainIntakeMotor.set(0.2);
        } else {
            mainIntakeMotor.set(0);
        }
    }

    public void stopAllMotion() {
        mainIntakeMotor.stopMotor();
        mainArmMotor.stopMotor();
        mainShooterMotor.stopMotor();
    }

}