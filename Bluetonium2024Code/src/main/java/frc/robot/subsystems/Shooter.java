package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkFlex mainShooterMotor;
    private RelativeEncoder mainShooterEncoder;

    public Shooter() {
        mainShooterMotor = new CANSparkFlex(ShooterConstants.SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        mainShooterMotor.restoreFactoryDefaults();
        mainShooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        mainShooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        mainShooterMotor.setInverted(false);

        mainShooterEncoder = mainShooterMotor.getEncoder();
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
