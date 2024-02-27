package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.lib.math.Conversions;
import frc.lib.util.SwerveModuleConstants;
import frc.robot.constants.Constants;
import frc.robot.constants.NeoVortexSwerveConstants;

public class SwerveModule {
    public final int moduleNumber;
    private Rotation2d angleOffset;

    private CANSparkFlex mAngleMotor;
    public final RelativeEncoder angleMotorEncoder;
    private SparkPIDController angleMotorController;

    private CANSparkFlex mDriveMotor;
    private RelativeEncoder driveMotorEncoder;
    private SparkPIDController driveMotorController;

    private CANcoder angleEncoder;

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        /* Angle Encoder Config */
        angleEncoder = new CANcoder(moduleConstants.cancoderID);

        CANcoderConfiguration config = new CANcoderConfiguration();
        config.MagnetSensor.SensorDirection = NeoVortexSwerveConstants.cancoderInvert;
        angleEncoder.getConfigurator().apply(config);

        mAngleMotor = new CANSparkFlex(moduleConstants.angleMotorID, MotorType.kBrushless);
        mAngleMotor.setIdleMode(Constants.Swerve.ANGLE_IDLE_MODE);
        mAngleMotor.setSmartCurrentLimit(Constants.Swerve.ANGLE_CURRENT_LIMIT);
        mAngleMotor.setInverted(NeoVortexSwerveConstants.ANGLE_MOTOR_INVERT);

        angleMotorController = mAngleMotor.getPIDController();
        angleMotorController.setP(NeoVortexSwerveConstants.ANGLE_KP);
        angleMotorController.setI(NeoVortexSwerveConstants.ANGLE_KI);
        angleMotorController.setD(NeoVortexSwerveConstants.ANGLE_KD);

        angleMotorEncoder = mAngleMotor.getEncoder();
        angleMotorEncoder.setPositionConversionFactor(1 / Constants.Swerve.ANGLE_GEAR_RATIO);

        resetToAbsolute();

        /* Drive Motor Config */
        mDriveMotor = new CANSparkFlex(moduleConstants.driveMotorID, MotorType.kBrushless);
        mDriveMotor.setIdleMode(Constants.Swerve.DRIVE_IDLE_MODE);
        mDriveMotor.setSmartCurrentLimit(Constants.Swerve.DRIVE_CURRENT_LIMIT);
        mDriveMotor.setOpenLoopRampRate(Constants.Swerve.OPEN_LOOP_RAMP);
        mDriveMotor.setClosedLoopRampRate(Constants.Swerve.CLOSED_LOOP_RAMP);
        mDriveMotor.setInverted(NeoVortexSwerveConstants.DRIVE_MOTOR_INVERT);

        driveMotorController = mDriveMotor.getPIDController();
        driveMotorController.setP(Constants.Swerve.DRIVE_KP);
        driveMotorController.setI(Constants.Swerve.DRIVE_KI);
        driveMotorController.setD(Constants.Swerve.DRIVE_PD);

        driveMotorEncoder = mDriveMotor.getEncoder();
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle);

        angleMotorController.setReference(desiredState.angle.getRotations(), ControlType.kPosition);

        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double speed = (desiredState.speedMetersPerSecond / Constants.Swerve.MAX_SPEED);
            driveMotorController.setReference(speed, ControlType.kDutyCycle);
        } else {
            double velocity = Conversions.MPSToRPM(desiredState.speedMetersPerSecond,
                    NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE);
            driveMotorController.setReference(velocity, ControlType.kVelocity);
        }
    }

    public Rotation2d getCANcoder() {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValue());
    }

    public void resetToAbsolute() {
        double absolutePosition = getCANcoder().getRotations() - angleOffset.getRotations();
        angleMotorEncoder.setPosition(absolutePosition);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
                Conversions.RPMToMPS(driveMotorEncoder.getVelocity(), NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE),
                Rotation2d.fromRotations(angleMotorEncoder.getPosition()));
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                Conversions.rotationsToMeters(driveMotorEncoder.getPosition(),
                        NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE),
                Rotation2d.fromRotations(angleMotorEncoder.getPosition()));
    }
}