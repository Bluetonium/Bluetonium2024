package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.math.Conversions;
import frc.lib.util.SwerveModuleConstants;
import frc.robot.constants.NeoVortexSwerveConstants;
import frc.robot.constants.Constants.MiscConstants;
import frc.robot.constants.Constants.SwerveConstants;

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
        angleEncoder = new CANcoder(moduleConstants.cancoderID, MiscConstants.CANIVORE_NAME);

        CANcoderConfiguration config = new CANcoderConfiguration();
        config.MagnetSensor.SensorDirection = NeoVortexSwerveConstants.CAN_CODER_INVERT;
        angleEncoder.getConfigurator().apply(config);

        angleMotor = new CANSparkFlex(moduleConstants.angleMotorID, MotorType.kBrushless);
        angleMotor.setIdleMode(SwerveConstants.ANGLE_IDLE_MODE);
        angleMotor.setSmartCurrentLimit(SwerveConstants.ANGLE_CURRENT_LIMIT);
        angleMotor.setInverted(NeoVortexSwerveConstants.ANGLE_MOTOR_INVERT);

        angleMotorController = angleMotor.getPIDController();
        angleMotorController.setP(NeoVortexSwerveConstants.ANGLE_KP);
        angleMotorController.setI(NeoVortexSwerveConstants.ANGLE_KI);
        angleMotorController.setD(NeoVortexSwerveConstants.ANGLE_KD);

        angleMotorEncoder = angleMotor.getEncoder();
        angleMotorEncoder.setPositionConversionFactor(1 / SwerveConstants.ANGLE_GEAR_RATIO);
        angleMotorEncoder.setVelocityConversionFactor(1 / SwerveConstants.ANGLE_GEAR_RATIO);

        resetToAbsolute();

        /* Drive Motor Config */
        driveMotor = new CANSparkFlex(moduleConstants.driveMotorID, MotorType.kBrushless);
        driveMotor.setIdleMode(SwerveConstants.DRIVE_IDLE_MODE);
        driveMotor.setSmartCurrentLimit(SwerveConstants.DRIVE_CURRENT_LIMIT);
        driveMotor.setOpenLoopRampRate(SwerveConstants.OPEN_LOOP_RAMP);
        driveMotor.setClosedLoopRampRate(SwerveConstants.CLOSED_LOOP_RAMP);
        driveMotor.setInverted(NeoVortexSwerveConstants.DRIVE_MOTOR_INVERT);

        driveMotorController = driveMotor.getPIDController();
        driveMotorController.setP(SwerveConstants.DRIVE_KP);
        driveMotorController.setI(SwerveConstants.DRIVE_KI);
        driveMotorController.setD(SwerveConstants.DRIVE_PD);
        driveMotorController.setFF(SwerveConstants.DRIVE_FF);

        driveMotorEncoder = driveMotor.getEncoder();
        driveMotorEncoder.setPositionConversionFactor(1 / SwerveConstants.DRIVE_GEAR_RATIO);
        driveMotorEncoder.setVelocityConversionFactor(1 / SwerveConstants.DRIVE_GEAR_RATIO);
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle);
        angleMotorController.setReference(desiredState.angle.getRotations(), ControlType.kPosition);
        setSpeed(desiredState, isOpenLoop);
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
            double speed = (desiredState.speedMetersPerSecond / SwerveConstants.MAX_SPEED);
            driveMotorController.setReference(speed, ControlType.kDutyCycle);
        } else {
            double velocity = Conversions.mpsToRpm(desiredState.speedMetersPerSecond,
                    NeoVortexSwerveConstants.WHEEL_CIRCUMFERENCE);

            SmartDashboard.putNumber("Module " + moduleNumber + " desired velocity", desiredState.speedMetersPerSecond);
            driveMotorController.setReference(velocity, ControlType.kVelocity);
        }
    }

    public void stopAllMotion() {
        angleMotor.stopMotor();
        driveMotor.stopMotor();
    }
}