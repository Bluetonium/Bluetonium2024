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
    public int moduleNumber;
    private Rotation2d angleOffset;

    private CANSparkFlex mAngleMotor;
    public RelativeEncoder angleMotorEncoder;
    private SparkPIDController angleMotorController;

    private CANSparkFlex mDriveMotor;
    private RelativeEncoder DriveMotorEncoder;
    private SparkPIDController driveMotorController;

  private CANcoder angleEncoder;
    
    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants){        
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset; 
       
        /* Angle Encoder Config */
        angleEncoder = new CANcoder(moduleConstants.cancoderID);

        CANcoderConfiguration config = new CANcoderConfiguration();
        config.MagnetSensor.SensorDirection = NeoVortexSwerveConstants.cancoderInvert;
        angleEncoder.getConfigurator().apply(config);
        

       
        mAngleMotor = new CANSparkFlex(moduleConstants.angleMotorID, MotorType.kBrushless);
        mAngleMotor.setIdleMode(Constants.Swerve.angleIdleMode);
        mAngleMotor.setSmartCurrentLimit(Constants.Swerve.angleCurrentLimit);
        //mAngleMotor.burnFlash();//commented because it doesnt allow the position conversation factor

        angleMotorController = mAngleMotor.getPIDController();
        angleMotorController.setP(NeoVortexSwerveConstants.angleKP);
        angleMotorController.setI(NeoVortexSwerveConstants.angleKI);
        angleMotorController.setD(NeoVortexSwerveConstants.angleKD);
        angleMotorEncoder = mAngleMotor.getEncoder();
        angleMotorEncoder.setPositionConversionFactor(1/Constants.Swerve.angleGearRatio);
 
        resetToAbsolute();

        /* Drive Motor Config */
        mDriveMotor = new CANSparkFlex(moduleConstants.driveMotorID, MotorType.kBrushless);
        mDriveMotor.setIdleMode(Constants.Swerve.driveIdleMode);
        mDriveMotor.setSmartCurrentLimit(Constants.Swerve.driveCurrentLimit);
        mDriveMotor.setOpenLoopRampRate(Constants.Swerve.openLoopRamp);
        mDriveMotor.setClosedLoopRampRate(Constants.Swerve.closedLoopRamp);
        //mDriveMotor.burnFlash();

        driveMotorController = mDriveMotor.getPIDController();
        driveMotorController.setP(Constants.Swerve.driveKP);
        driveMotorController.setI(Constants.Swerve.driveKI);
        driveMotorController.setD(Constants.Swerve.driveKD);
        DriveMotorEncoder = mDriveMotor.getEncoder();
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle); 
        angleMotorController.setReference(desiredState.angle.getRotations(), ControlType.kPosition);
        
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop){
        if(isOpenLoop){
            double voltage = (desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed) * 12;//i think so its between 12v
            mDriveMotor.setVoltage(voltage);
        }
        else {
            double velocity = Conversions.MPSToRPS(desiredState.speedMetersPerSecond, NeoVortexSwerveConstants.wheelCircumference);

            mDriveMotor.set(velocity);
        }
    }

    public Rotation2d getCANcoder(){
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValue());
    }

    public void resetToAbsolute(){
        double absolutePosition = getCANcoder().getRotations() - angleOffset.getRotations();
        angleMotorEncoder.setPosition(absolutePosition);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(
            Conversions.RPSToMPS(DriveMotorEncoder.getVelocity()/60, NeoVortexSwerveConstants.wheelCircumference),
            Rotation2d.fromRotations(angleMotorEncoder.getPosition())
        );
    }
    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(
            Conversions.rotationsToMeters(DriveMotorEncoder.getPosition(), NeoVortexSwerveConstants.wheelCircumference), 
            Rotation2d.fromRotations(angleMotorEncoder.getPosition())
        ); 
    }
}