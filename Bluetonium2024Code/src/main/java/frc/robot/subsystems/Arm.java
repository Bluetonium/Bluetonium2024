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

    public Arm() {
        setupArmMotors();
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

    /**
     * 
     * @param speed speed of the arm in RPM
     */
    public void setArmSpeed(double speed) {
        mainArmMotor.set(speed);
    }

    public void stopAllMotion() {
        mainArmMotor.stopMotor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Follower arm velocity", followerArmMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Main arm velocity", mainArmEncoder.getVelocity()); // not sure if this is what i
                                                                                     // should use for this
    }
}