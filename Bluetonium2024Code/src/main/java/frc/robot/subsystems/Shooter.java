package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkFlex mainShooterMotor; // make sure one of these is inverted
    private RelativeEncoder mainShooterEncoder;
    private SparkPIDController mainShooterController;

    public Shooter() {
        mainShooterMotor = new CANSparkFlex(ShooterConstants.FORWARD_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        mainShooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        mainShooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        mainShooterMotor.setInverted(false);

        mainShooterEncoder = mainShooterMotor.getEncoder();

        mainShooterController = mainShooterMotor.getPIDController();

        CANSparkFlex followerShooterMotor = new CANSparkFlex(ShooterConstants.BACK_SHOOT_MOTOR_ID,
                MotorType.kBrushless);
        followerShooterMotor.setSmartCurrentLimit(ShooterConstants.SHOOTER_CURRENT_LIMIT);
        followerShooterMotor.setIdleMode(ShooterConstants.SHOOTER_IDLE_MODE);
        followerShooterMotor.follow(mainShooterMotor, true);
        followerShooterMotor.close();
    }

    /**
     * 
     * @param speed turns the shoot
     */
    public void setState(boolean state) {
        SmartDashboard.putBoolean("Shoooot", state);
        if (state) {
            mainShooterMotor.set(1);
            // mainShooterController.setReference(ShooterConstants.DESIRED_SHOOTING_VELOCITY,
            // ControlType.kVelocity);
        } else {
            mainShooterMotor.set(0);
            // mainShooterController.setReference(0, ControlType.kVelocity);
        }
    }

    public void stopAllMotion() {
        // mainShooterMotor.stopMotor();
    }

    public boolean readyToShoot() {
        return mainShooterEncoder.getVelocity() >= ShooterConstants.MIN_SHOOTING_VELOCITY;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Shooter Up to Speed", readyToShoot());
    }
}
