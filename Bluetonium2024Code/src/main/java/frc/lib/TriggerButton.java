package frc.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.Constants.ControllerConstants;

public class TriggerButton extends Trigger {
    public TriggerButton(GenericHID controller, int axis) {
        super(() -> controller.getRawAxis(axis) >= ControllerConstants.TRIGGER_PULL_THRESHOLD);
    }
}
