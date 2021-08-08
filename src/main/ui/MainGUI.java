package ui;


/*
The full game panel you'll see here
 */

import model.Door;
import model.GameShow;
import model.Goat;
import ui.doortools.MainTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainGUI extends JFrame {
    private GameShow gameShow;
    private JPanel doorPanel;
    private TextPanel textPanel;
    private SidePanel sidePanel;
    private JScrollPane scrollPane;

    public MainGUI() {
        super("Monty Hall Simulation App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        gameShow = new GameShow();
        gameShow.setupStandardGameShow();
//        for (int i = 4; i < 15; i++) {
//            gameShow.addDoor(new Door(i, new Goat()));
//        }
        doorPanel = new DoorPanel(gameShow);
        textPanel = new TextPanel();
        sidePanel = new SidePanel(gameShow);

        scrollPane = new JScrollPane(doorPanel);
        scrollPane.setPreferredSize(new Dimension(800, 1000));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        add(textPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }


    public static void main(String[] args) {
        new MainGUI();
    }


}
