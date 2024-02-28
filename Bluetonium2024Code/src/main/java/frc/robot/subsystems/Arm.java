package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private CANSparkMax followerArmMotor;// left Arm
    private CANSparkMax mainArmMotor; // right Arm
    private RelativeEncoder mainArmEncoder;
    private SparkPIDController mainArmController;

    private CANSparkMax intakeMotor;

    private CANSparkMax followerShooterMotor;
    private CANSparkMax mainShooterMotor; // make sure one of these is inverted
    private SparkPIDController mainShooterController;

    public Arm() {
        setupArmMotors();
        setupIntakeMotors();
        setupShooterMotors();
    }

    private void setupArmMotors() {
        followerArmMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR,
                MotorType.kBrushless);
        followerArmMotor.setIdleMode(IdleMode.kBrake);

        mainArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        mainArmMotor.setIdleMode(IdleMode.kBrake);

        mainArmEncoder = mainArmMotor.getEncoder();
        mainArmEncoder.setPositionConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);

        mainArmController = mainArmMotor.getPIDController();

        followerArmMotor.follow(mainArmMotor, true);
    }

    private void setupShooterMotors() {
        followerShooterMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR,
                MotorType.kBrushless);
        mainShooterMotor = new CANSparkMax(ArmConstants.RIGHT_SHOOT_MOTOR,
                MotorType.kBrushless);
        followerShooterMotor.follow(mainArmMotor, true);
        mainShooterController = mainShooterMotor.getPIDController();
    }

    private void setupIntakeMotors() {
        intakeMotor = new CANSparkMax(ArmConstants.INTAKE_MOTOR_ID,
                MotorType.kBrushless);
    }

    public void resetArmPosition() {
        mainArmEncoder.setPosition(0);
    }

    /***
     * 
     * @param velocity velocity in RPM to set the shooter to
     */
    public void setShooterVelocity(double velocity) {
        mainShooterController.setReference(velocity, ControlType.kVelocity);
    }

    /**
     * 
     * @param angle angle to set the arm to should only be between [0,110] degrees
     */
    public void setArmAngle(Rotation2d angle) {
        mainArmController.setReference(angle.getRotations(), ControlType.kPosition);
    }

    /**
     * 
     * @param speed [-1,1] value for motor speed
     */
    public void setIntakeSpeed(double speed) {
        intakeMotor.set(speed);
    }

}