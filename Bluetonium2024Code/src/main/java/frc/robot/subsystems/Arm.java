package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.constants.Constants.ArmConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private CANSparkMax leftArmMotor;
    private CANSparkMax rightArmMotor; // make sure one of these is inverted
    private RelativeEncoder rightArmEncoder;

    private CANSparkMax intakeMotor;

    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor; // make sure one of these is inverted

    public Arm() {
        /*
         * leftArmMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR,
         * MotorType.kBrushless);
         * leftArmMotor.setIdleMode(IdleMode.kBrake);
         * rightArmMotor = new CANSparkMax(ArmConstants.RIGHT_ARM_MOTOR_ID,
         * MotorType.kBrushless);
         * rightArmMotor.setIdleMode(IdleMode.kBrake);
         * rightArmEncoder = rightArmMotor.getEncoder();
         * rightArmEncoder.setPositionConversionFactor(1 / ArmConstants.ARM_GEAR_RATIO);
         * 
         * leftArmMotor.follow(rightArmMotor, true);
         * 
         * intakeMotor = new CANSparkMax(ArmConstants.INTAKE_MOTOR_ID,
         * MotorType.kBrushless);
         * 
         * leftShooterMotor = new CANSparkMax(ArmConstants.LEFT_SHOOT_MOTOR,
         * MotorType.kBrushless);
         * rightShooterMotor = new CANSparkMax(ArmConstants.RIGHT_SHOOT_MOTOR,
         * MotorType.kBrushless);
         * 
         * leftArmMotor.follow(rightArmMotor, true);
         */
    }

    public void resetArmPosition() {

    }

}
