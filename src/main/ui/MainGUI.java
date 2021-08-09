package ui;


/*
The full game panel you'll see here
 */

import model.GameShow;
import ui.gui.ButtonsPanel;
import ui.gui.DoorPanel;
import ui.gui.GraphPanel;

import javax.swing.*;
import java.awt.*;

/*
Main GUI running class

centreOnScreen is heavily inspired by SpaceInvadersStarter in CPSC 210
 */

public class MainGUI extends JFrame {
    private GameShow gameShow;
    private DoorPanel doorPanel;
    private ButtonsPanel buttonsPanel;
    private JScrollPane scrollPane;
    private GraphPanel graphPanel;

    // constructor
    public MainGUI() {
        super("Monty Hall Simulation App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        gameShow = new GameShow();
        gameShow.setupStandardGameShow();
        doorPanel = new DoorPanel(gameShow);
        buttonsPanel = new ButtonsPanel(gameShow);
        graphPanel = new GraphPanel(gameShow);

        doorPanel.setSidePanel(buttonsPanel);
        buttonsPanel.setDoorPanel(doorPanel);
        buttonsPanel.setGraphPanel(graphPanel);
        graphPanel.setButtonsPanel(buttonsPanel);

        scrollPane = new JScrollPane(doorPanel);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        add(buttonsPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.EAST);
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  sets the location of frame so that it is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }


    // main function
    public static void main(String[] args) {
        new MainGUI();
    }


}

