package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkMax mainShooterMotor; // make sure one of these is inverted
    private RelativeEncoder mainShooterEncoder;
    CANSparkMax followerShooterMotor;

    public Shooter() {
        mainShooterMotor = new CANSparkMax(ShooterConstants.FORWARD_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        mainShooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        mainShooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);

        mainShooterEncoder = mainShooterMotor.getEncoder();

        followerShooterMotor = new CANSparkMax(ShooterConstants.BACK_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        followerShooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        followerShooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        followerShooterMotor.follow(mainShooterMotor, true);
    }

    /**
     * 
     * @param speed turns the shoot
     */
    public void setState(boolean state) {
        if (state) {
            mainShooterMotor.set(1);
        } else {
            mainShooterMotor.set(0);
        }
    }

    public void stopAllMotion() {
        mainShooterMotor.stopMotor();
    }

    public boolean readyToShoot() {
        return mainShooterEncoder.getVelocity() >= ShooterConstants.MIN_SHOOTING_VELOCITY;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Shooter Up to Speed", readyToShoot());
    }
}
