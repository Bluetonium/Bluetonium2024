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
    private NetworkTableEntry ta;

    public Limelight(String limelightName) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    public double getTx() {
        return tx.getDouble(0.0);
    }
    public void setPipeline(double pipeline) {
        table.getEntry("pipeline").setDouble(pipeline);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }
    public double getTa() {
        return ta.getDouble(0.0);
    }
}
