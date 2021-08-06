package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Side panel hosts either:
- The Monty Hall Host figure just standing there at the beginning
- Or the actual simulation buttons (add door, run simulation, etc)
 */
public class SidePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;
    private static final int HOSTHEIGHT = 500;
    private static final int HOSTWIDTH = 200;

    private GameShow gameShow;

    private JButton addGoatDoorButton;
    private JButton addCarDoorButton;
    private JButton saveDoorsButton;
    private JButton loadDoorsButton;

    public SidePanel(GameShow gameShow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 1));
        setBackground(Color.lightGray);
        this.gameShow = gameShow;

        addGoatDoorButton = createButton("Add door with goat");
        addCarDoorButton = createButton("Add door with car");
        saveDoorsButton = createButton("Save current gameshow setup");
        loadDoorsButton = createButton("Load previously saved gameshow setup");

        add(addGoatDoorButton);
        add(Box.createVerticalStrut(80));
        addGoatDoorButton.addActionListener(this);
        add(addCarDoorButton);
        add(Box.createVerticalStrut(80));
        addCarDoorButton.addActionListener(this);
        add(saveDoorsButton);
        add(Box.createVerticalStrut(80));
        saveDoorsButton.addActionListener(this);
        add(loadDoorsButton);
        loadDoorsButton.addActionListener(this);

    }

    protected JButton createButton(String label) {
        JButton button = new JButton(label);
        button = customizeButton(button);
        return button;
    }

    private JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int nextDoorID = gameShow.getLargestDoorID() + 1;
        Simulation simulation = new Simulation();

        if (e.getSource() == addCarDoorButton) {
            Prize prize = new Car("red");
            gameShow.addDoor(new Door(nextDoorID, prize));
        } else if (e.getSource() == addGoatDoorButton) {
            Prize prize = new Goat();
            gameShow.addDoor(new Door(nextDoorID, prize));
        } else if (e.getSource() == saveDoorsButton) {
            simulation.saveGameShow(gameShow);
        } else if (e.getSource() == loadDoorsButton) {
            gameShow = simulation.loadGameShow();
        }
    }
}
