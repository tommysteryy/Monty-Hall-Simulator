package ui.doortools;

import javax.swing.*;

public class DoorTool {

    protected JButton button;
    private boolean active;

    public DoorTool() {
        active = false;
    }

    protected void createButton(String label) {
        button = new JButton(label);
        button = customizeButton(button);
    }

    private JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

}
