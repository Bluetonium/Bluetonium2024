package frc.robot.subsystems;

import java.util.Optional;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveModule;
import frc.robot.constants.Constants.SwerveConstants;

public class Swerve extends SubsystemBase {
    private SwerveDriveOdometry swerveOdometry;
    private SwerveModule[] mSwerveMods;
    private Pigeon2 gyro;

    public Swerve(Pigeon2 gyro) {
        this.gyro = gyro;
        mSwerveMods = new SwerveModule[] {
                new SwerveModule(0, SwerveConstants.Mod0.constants),
                new SwerveModule(1, SwerveConstants.Mod1.constants),
                new SwerveModule(2, SwerveConstants.Mod2.constants),
                new SwerveModule(3, SwerveConstants.Mod3.constants)
        };

        swerveOdometry = new SwerveDriveOdometry(SwerveConstants.swerveKinematics, getGyroYaw(), getModulePositions());

        AutoBuilder.configureHolonomic(
                this::getPose,
                this::setPose,
                this::getRobotRelativeSpeeds,
                this::driveRobotReleative, new HolonomicPathFollowerConfig(SwerveConstants.MAX_SPEED,
                        SwerveConstants.BASE_RADIUS, new ReplanningConfig()),
                () -> {
                    Optional<Alliance> alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                }, this);
    }

    /**
     * 
     * @param translation   the translational speed of the rboot in m/s
     * @param rotation      the rotational speed of robot in rad/s
     * @param fieldRelative wether to be head or headless
     * @param isOpenLoop    wether to run the wheels open loop or closed loop
     */
    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = SwerveConstants.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                        translation.getX(),
                        translation.getY(),
                        rotation,
                        getHeading())
                        : new ChassisSpeeds(
                                translation.getX(),
                                translation.getY(),
                                rotation));

        setModuleStates(swerveModuleStates, isOpenLoop);
    }

    /**
     * 
     * @return returns the current speed of the robot in ChassisSpeeds
     */
    public ChassisSpeeds getRobotRelativeSpeeds() {
        return SwerveConstants.swerveKinematics.toChassisSpeeds(getModuleStates());
    }

    /**
     * 
     * @param chassisSpeeds the speeds to drive the robot at in ChassisSpeeds
     */
    public void driveRobotReleative(ChassisSpeeds chassisSpeeds) {
        SwerveModuleState[] swerveModuleStates = SwerveConstants.swerveKinematics.toSwerveModuleStates(chassisSpeeds);
        setModuleStates(swerveModuleStates, false);
    }

    /**
     * 
     * @param desiredStates Desired swerve module state
     * @param isOpenLoop    Wether the wheels should be run in open loop mode
     */
    public void setModuleStates(SwerveModuleState[] desiredStates, boolean isOpenLoop) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConstants.MAX_SPEED);

        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], isOpenLoop);
        }
    }

    /**
     * 
     * @return Returns the current modules states
     */
    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (SwerveModule mod : mSwerveMods) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    /**
     * 
     * @return Returns the current module positions
     */
    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (SwerveModule mod : mSwerveMods) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    /**
     * 
     * @return Returns the robots positions in meters
     */
    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    /**
     * 
     * @param pose Sets the position of the odometry
     */
    public void setPose(Pose2d pose) {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
    }

    /**
     * 
     * @return Gets the current heading of the robot
     */
    public Rotation2d getHeading() {
        return getPose().getRotation();
    }

    /**
     * 
     * @param heading Sets the current heading of the robot
     */
    public void setHeading(Rotation2d heading) {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(),
                new Pose2d(getPose().getTranslation(), heading));
    }

    /**
     * Zeroes the robots heading
     */
    public void zeroHeading() {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(),
                new Pose2d(getPose().getTranslation(), new Rotation2d()));
    }

    /**
     * 
     * @return Current yaw of the robot
     */
    public Rotation2d getGyroYaw() {
        return Rotation2d.fromDegrees(gyro.getYaw().getValue());
    }

    /**
     * Resets all the modules positions to absolute
     */
    public void resetModulesToAbsolute() {
        for (SwerveModule mod : mSwerveMods) {
            mod.resetToAbsolute();
        }
    }

    /**
     * Stops all the motion of the robot
     */
    public void stopAllMotion() {
        for (SwerveModule mod : mSwerveMods) {
            mod.stopAllMotion();
        }
    }

    @Override
    public void periodic() {
        swerveOdometry.update(getGyroYaw(), getModulePositions());

        for (SwerveModule mod : mSwerveMods) {
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Angle", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
        }
    }
}