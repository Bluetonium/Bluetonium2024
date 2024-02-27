package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private CANSparkMax leftArmMotor;
    private CANSparkMax rightArmMotor; // make sure one of these is inverted

    private CANSparkMax intakeMotor;

    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor; // make sure one of these is inverted

}
