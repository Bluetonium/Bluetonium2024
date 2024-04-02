package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkFlex shooterMotor;
    private RelativeEncoder shooterEncoder;
    private SlewRateLimiter rateLimiter;
    private boolean state;

    public Shooter() {
        shooterMotor = new CANSparkFlex(ShooterConstants.SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        shooterMotor.restoreFactoryDefaults();
        shooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        shooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        shooterMotor.setInverted(false);

        shooterEncoder = shooterMotor.getEncoder();

        rateLimiter = new SlewRateLimiter(1);
        state = false;
    }

    /**
     * 
     * @param speed turns the shoot
     */
    public void setState(boolean state) {
        this.state = state;
    }

    public void stopAllMotion() {
        state = false;
        shooterMotor.stopMotor();
    }

    public boolean readyToShoot(boolean speaker) {
        if (speaker) {
            return shooterEncoder.getVelocity() >= ShooterConstants.MIN_SHOOTING_VELOCITY;
        } else {
            return shooterEncoder.getVelocity() >= ShooterConstants.AMP_SHOOTING_VELOCITY;
        }
    }

    @Override
    public void periodic() {
        if (state) {
            shooterMotor.set(rateLimiter.calculate(1));
        } else {
            shooterMotor.set(rateLimiter.calculate(0));
        }
    }

}
