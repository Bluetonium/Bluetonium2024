package frc.robot.commands.auton.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;

public class IntakeNoteSequence extends SequentialCommandGroup {
    public IntakeNoteSequence(Arm arm, Swerve swerve, Intake intake) {
        addCommands(new PutArmIntoPosition(arm),
                new IntakeNote(intake, swerve));// probably change to use the other intake note thing and jsut have path
                                                // planner do the drive forward thing
    }
}
