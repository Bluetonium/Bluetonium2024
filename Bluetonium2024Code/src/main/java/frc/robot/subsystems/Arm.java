package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private CANSparkMax mainArmMotor; // right Arm
    private RelativeEncoder mainArmEncoder;
    CANSparkMax followerArmMotor; // left Arm

    private CANSparkMax mainIntakeMotor;
    CANSparkMax followerIntakeMotor;

    private CANSparkMax mainShooterMotor; // make sure one of these is inverted
    CANSparkMax followerShooterMotor;

    public Arm() {
        setupArmMotors();
        setupIntakeMotors();
        setupShooterMotors();
    }

    private void setupArmMotors() {
        mainArmMotor = new CANSparkMax(ArmConstants.LEFT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        mainArmMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        mainArmMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        mainArmEncoder = mainArmMotor.getEncoder();
        mainArmEncoder.setVelocityConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);

        followerArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID,
                MotorType.kBrushless);
        followerArmMotor.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT);
        followerArmMotor.setIdleMode(ArmConstants.ARM_IDLE_MODE);
        followerArmMotor.follow(mainArmMotor, true);
    }

    private void setupShooterMotors() {
        mainShooterMotor = new CANSparkMax(ArmConstants.FORWARD_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        mainShooterMotor.setSmartCurrentLimit(ArmConstants.SHOOTER_CURRENT_LIMIT);
        mainShooterMotor.setIdleMode(ArmConstants.SHOOTER_IDLE_MODE);

        followerShooterMotor = new CANSparkMax(ArmConstants.BACK_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        followerShooterMotor.setSmartCurrentLimit(ArmConstants.SHOOTER_CURRENT_LIMIT);
        followerArmMotor.setIdleMode(ArmConstants.SHOOTER_IDLE_MODE);
        followerShooterMotor.follow(mainShooterMotor, true);
    }

    private void setupIntakeMotors() {
        mainIntakeMotor = new CANSparkMax(ArmConstants.FORWARD_INTAKE_MOTOR_ID,
                MotorType.kBrushless);
        mainIntakeMotor.setInverted(true);

        mainIntakeMotor.setSmartCurrentLimit(ArmConstants.INTAKE_CURRENT_LIMIT);
        mainIntakeMotor.setIdleMode(ArmConstants.INTAKE_IDLE_MODE);

        followerIntakeMotor = new CANSparkMax(ArmConstants.BACK_INTAKE_MOTOR_ID, MotorType.kBrushless);
        followerIntakeMotor.setSmartCurrentLimit(ArmConstants.INTAKE_CURRENT_LIMIT);
        followerIntakeMotor.setIdleMode(ArmConstants.INTAKE_IDLE_MODE);
        followerIntakeMotor.follow(mainIntakeMotor, true);
    }

    /**
     * 
     * @param speed speed of the arm in RPM
     */
    public void setArmSpeed(double speed) {
        mainArmMotor.set(speed);
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
    public void setIntakeState(double speed) {

        mainIntakeMotor.set(speed);

    }

    public void stopAllMotion() {
        mainIntakeMotor.stopMotor();
        mainArmMotor.stopMotor();
        mainShooterMotor.stopMotor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Follower shooter velocity", followerShooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Main shooter velocity", followerShooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Follower intake velocity", followerIntakeMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Main intake velocity", mainIntakeMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Follower arm velocity", followerArmMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Main arm velocity", mainArmEncoder.getVelocity()); // not sure if this is what i
                                                                                     // should use for this !

    }
}