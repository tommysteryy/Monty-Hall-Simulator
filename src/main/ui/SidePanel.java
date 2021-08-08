package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/*
Side panel hosts either:
- Or the actual simulation buttons (add door, run simulation, etc)
 */
public class SidePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 800;
    private static final int HOSTHEIGHT = 500;
    private static final int HOSTWIDTH = 200;

    private GameShow gameShow;

    private JButton addGoatDoorButton;
    private JButton addCarDoorButton;
    private JButton saveDoorsButton;
    private JButton loadDoorsButton;
    private JButton runSimulationButton;

    public SidePanel(GameShow gameShow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 1));
        setBackground(Color.lightGray);
        this.gameShow = gameShow;

        addGoatDoorButton = createButton("Add door with goat");
        addCarDoorButton = createButton("Add door with car");
        saveDoorsButton = createButton("Save current gameshow setup");
        loadDoorsButton = createButton("Load previously saved gameshow setup");
        runSimulationButton = createButton("Run the simulation on current gameshow 1000 times");

        add(addGoatDoorButton);
        add(Box.createVerticalStrut(60));
        addGoatDoorButton.addActionListener(this);
        add(addCarDoorButton);
        add(Box.createVerticalStrut(60));
        addCarDoorButton.addActionListener(this);
        add(saveDoorsButton);
        add(Box.createVerticalStrut(60));
        saveDoorsButton.addActionListener(this);
        add(loadDoorsButton);
        loadDoorsButton.addActionListener(this);
        add(Box.createVerticalStrut(60));
        add(runSimulationButton);
        runSimulationButton.addActionListener(this);

    }

    protected JButton createButton(String label) {
        JButton button = new JButton(label);
        button = customizeButton(button);
        return button;
    }

    private JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusable(false);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.black);
        button.setForeground(Color.lightGray);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int nextDoorID = gameShow.getLargestDoorID() + 1;
        Simulation simulation = new Simulation();
        List<Float> loProbs;

        if (e.getSource() == addCarDoorButton) {
            Prize carPrize = new Car("red");
            gameShow.addDoor(new Door(nextDoorID, carPrize));
        } else if (e.getSource() == addGoatDoorButton) {
            Prize goatPrize = new Goat();
            gameShow.addDoor(new Door(nextDoorID, goatPrize));
        } else if (e.getSource() == saveDoorsButton) {
            simulation.saveGameShow(this.gameShow);
        } else if (e.getSource() == loadDoorsButton) {
            GameShow loadedGameShow = simulation.loadGameShow();
            this.gameShow = loadedGameShow;

        } else if (e.getSource() == runSimulationButton) {
            loProbs = simulation.runTheSimulationLoopReturnProbabilities(gameShow, 1000);
            Float switchWinProbability = loProbs.get(0);
            Float dontSwitchWinProbability = loProbs.get(1);

        }
    }
}
