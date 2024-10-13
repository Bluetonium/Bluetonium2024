package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.lib.math.Conversions;
import frc.robot.constants.Constants;
import frc.robot.constants.NeoVortexSwerveConstants;
import frc.robot.constants.SwerveModuleConstants;

public class SwerveModule {
    public final int moduleNumber;
    private Rotation2d angleOffset;

    private CANSparkFlex angleMotor;
    private final RelativeEncoder angleMotorEncoder;
    private SparkPIDController angleMotorController;

    private CANSparkFlex driveMotor;
    private RelativeEncoder driveMotorEncoder;
    private SparkPIDController driveMotorController;

    private CANcoder angleEncoder;

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;

        /* Angle Encoder Config */
        angleEncoder = new CANcoder(moduleConstants.cancoderID);

        CANcoderConfiguration config = new CANcoderConfiguration();
        config.MagnetSensor.SensorDirection = NeoVortexSwerveConstants.CAN_CODER_INVERT;
        angleEncoder.getConfigurator().apply(config);

        angleMotor = new CANSparkFlex(moduleConstants.angleMotorID, MotorType.kBrushless);
        angleMotor.setIdleMode(Constants.Swerve.ANGLE_IDLE_MODE);
        angleMotor.setSmartCurrentLimit(Constants.Swerve.ANGLE_CURRENT_LIMIT);
        angleMotor.setInverted(NeoVortexSwerveConstants.ANGLE_MOTOR_INVERT);

        angleMotorController = angleMotor.getPIDController();

        angleMotorController.setP(NeoVortexSwerveConstants.ANGLE_KP);
        angleMotorController.setI(NeoVortexSwerveConstants.ANGLE_KI);
        angleMotorController.setD(NeoVortexSwerveConstants.ANGLE_KD);
        // dawg what is this

        angleMotorEncoder = angleMotor.getEncoder();
        angleMotorEncoder.setPositionConversionFactor(1 / Constants.Swerve.ANGLE_GEAR_RATIO);
        angleMotorEncoder.setVelocityConversionFactor(1 / Constants.Swerve.ANGLE_GEAR_RATIO);

        angleMotorController.setPositionPIDWrappingEnabled(true);
        angleMotorController.setPositionPIDWrappingMinInput(0);
        angleMotorController.setPositionPIDWrappingMaxInput(1);
        angleMotorController.setFeedbackDevice(angleMotorEncoder);
        resetToAbsolute();

        /* Drive Motor Config */
        driveMotor = new CANSparkFlex(moduleConstants.driveMotorID, MotorType.kBrushless);
        driveMotor.setIdleMode(Constants.Swerve.DRIVE_IDLE_MODE);
        driveMotor.setSmartCurrentLimit(Constants.Swerve.DRIVE_CURRENT_LIMIT);
        driveMotor.setOpenLoopRampRate(Constants.Swerve.OPEN_LOOP_RAMP);
        driveMotor.setClosedLoopRampRate(Constants.Swerve.CLOSED_LOOP_RAMP);
        driveMotor.setInverted(NeoVortexSwerveConstants.DRIVE_MOTOR_INVERT);

        driveMotorController = driveMotor.getPIDController();
        driveMotorController.setP(Constants.Swerve.DRIVE_KP);
        driveMotorController.setI(Constants.Swerve.DRIVE_KI);
        driveMotorController.setD(Constants.Swerve.DRIVE_PD);
        driveMotorController.setFF(Constants.Swerve.DRIVE_FF);

        driveMotorEncoder = driveMotor.getEncoder();
        driveMotorEncoder.setPositionConversionFactor(1 / Constants.Swerve.DRIVE_GEAR_RATIO);
        driveMotorEncoder.setVelocityConversionFactor(1 / Constants.Swerve.DRIVE_GEAR_RATIO);
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle);
        angleMotorController.setReference(desiredState.angle.getRotations(), CANSparkBase.ControlType.kPosition);
        setSpeed(desiredState, isOpenLoop);
    }

    public Rotation2d getCANcoder() {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValue());
    }

    public void resetToAbsolute() {
        double absolutePosition = getCANcoder().getRotations() -
                angleOffset.getRotations();
        angleMotorEncoder.setPosition(absolutePosition);
    }

    public SwerveModuleState getState() {

        return new SwerveModuleState(
                Conversions.rpmToMps(driveMotorEncoder.getVelocity(), NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE),
                Rotation2d.fromRotations(angleMotorEncoder.getPosition()));

    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                Conversions.rotationsToMeters(driveMotorEncoder.getPosition(),
                        NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE),
                Rotation2d.fromRotations(angleMotorEncoder.getPosition()));
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double speed = (desiredState.speedMetersPerSecond / Constants.Swerve.MAX_SPEED);
            driveMotorController.setReference(speed, ControlType.kDutyCycle);
        } else {
            double velocity = Conversions.mpsToRpm(desiredState.speedMetersPerSecond,
                    NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE);
            driveMotorController.setReference(velocity, ControlType.kVelocity);

        }
    }

    public void stopAllMotion() {
        angleMotor.stopMotor();
        driveMotor.stopMotor();
    }
}