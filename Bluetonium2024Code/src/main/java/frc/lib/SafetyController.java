package frc.lib;

import edu.wpi.first.wpilibj.GenericHID;

class SafetyController {
    private GenericHID overrideController;
    private GenericHID mainController;
    private int overrideButton;

    public SafetyController(int overridePort, int mainPort, int overrideButton) {
        overrideController = new GenericHID(overridePort);
        mainController = new GenericHID(mainPort);
        this.overrideButton = overrideButton;
    }

    public boolean getRawButton(int button) {
        if (overrideController.getRawButton(overrideButton)) {
            return overrideController.getRawButton(button);
        } else {
            return mainController.getRawButton(button);
        }
    }

    public double getRawAxis(int axis) {
        if (overrideController.getRawButton(overrideButton)) {
            return overrideController.getRawAxis(axis);
        } else {
            return mainController.getRawAxis(axis);
        }
    }
}
