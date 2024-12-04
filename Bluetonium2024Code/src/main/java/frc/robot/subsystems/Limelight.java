package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase
{
    private NetworkTable table;

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry tv;
    private NetworkTableEntry ta;

    private NetworkTableEntry pipeline;


    public Limelight(String limelightName) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        tv = table.getEntry("tv");
        ta = table.getEntry("ta");
        pipeline = table.getEntry("pipeline");
    }

    public void setPipeline(double pipeline) {
        this.pipeline.setDouble(pipeline);
    }

    public double getTx() {
        return tx.getDouble(0.0);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }
    public boolean validTargetExists() {
        return tv.getInteger(0) == 1;
    }

    public double getTa() {
        return ta.getDouble(0.0);
    }
}
