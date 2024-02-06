package frc.robot;


import com.ctre.phoenix6.hardware.CANcoder;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.lib.math.Conversions;
import frc.lib.util.SwerveModuleConstants;

public class SwerveModule {
    public int moduleNumber;
    private Rotation2d angleOffset;

    private CANSparkFlex mAngleMotor;
    private RelativeEncoder angleMotorEncoder;
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
        angleEncoder.getConfigurator().apply(Robot.ctreConfigs.swerveCANcoderConfig);

       
        mAngleMotor = new CANSparkFlex(moduleConstants.angleMotorID, MotorType.kBrushless);
        angleMotorController = mAngleMotor.getPIDController();
        angleMotorEncoder = mAngleMotor.getEncoder();
 
        resetToAbsolute();

        /* Drive Motor Config */
        mDriveMotor = new CANSparkFlex(moduleConstants.driveMotorID, MotorType.kBrushless);
        driveMotorController = mDriveMotor.getPIDController();
        DriveMotorEncoder = mDriveMotor.getEncoder();
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle); 
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop){
        if(isOpenLoop){
            double voltage = (desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed) * 12;//i think so its between 12v
            mDriveMotor.setVoltage(voltage);
        }
        else {
            double velocity = Conversions.MPSToRPS(desiredState.speedMetersPerSecond, Constants.Swerve.wheelCircumference);

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
            Conversions.RPSToMPS(DriveMotorEncoder.getVelocity()/60, Constants.Swerve.wheelCircumference),
            Rotation2d.fromRotations(angleMotorEncoder.getPosition())
        );
    }
    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(
            Conversions.rotationsToMeters(DriveMotorEncoder.getPosition(), Constants.Swerve.wheelCircumference), 
            Rotation2d.fromRotations(angleMotorEncoder.getPosition())
        ); 
    }
}