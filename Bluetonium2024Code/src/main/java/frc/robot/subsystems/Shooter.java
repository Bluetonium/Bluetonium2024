package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkFlex shooterMotor;
    private RelativeEncoder shooterEncoder;
    private SlewRateLimiter rateLimiter;
    private boolean state;

    private DoubleSupplier robotYaw;

    public Shooter(DoubleSupplier robotYaw) {
        shooterMotor = new CANSparkFlex(ShooterConstants.SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        shooterMotor.restoreFactoryDefaults();
        shooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        shooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        shooterMotor.setInverted(false);

        shooterEncoder = shooterMotor.getEncoder();
        // TODO add the pid stuff here and pid tune it

        rateLimiter = new SlewRateLimiter(1);
        state = false;
        this.robotYaw = robotYaw;
    }

    /**
     * 
     * @param speed turns the shoot
     */
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void periodic() {
        if (state) {
            shooterMotor.set(rateLimiter.calculate(1));
        } else {
            shooterMotor.set(rateLimiter.calculate(0));
        }
    }

    public void stopAllMotion() {
        state = false;
        shooterMotor.stopMotor();
    }

    public boolean readyToShoot() {// just have this do the yaw stuff
        if (robotFacingAmp()) {
            return shooterEncoder.getVelocity() > ShooterConstants.AMP_SHOOTING_VELOCITY;
        } else {
            return shooterEncoder.getVelocity() > ShooterConstants.SPEAKER_SHOOTING_VELOCITY;
        }
    }

    private boolean robotFacingAmp() {
        double value = ((Math.abs(robotYaw.getAsDouble()) + 90) % 180);
        return 180 - value < 45 || value < 45;
    }
}
