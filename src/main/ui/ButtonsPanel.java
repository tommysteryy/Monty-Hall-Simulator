package ui;

import model.*;

import javax.crypto.spec.DESedeKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;


/*
Side panel hosts either:
- Or the actual simulation buttons (add door, run simulation, etc)
 */
public class ButtonsPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int HOSTHEIGHT = 500;
    private static final int HOSTWIDTH = 200;

    private GameShow gameShow;
    private DoorPanel doorPanel;
    private GraphPanel graphPanel;

    private JButton addGoatDoorButton;
    private JButton addCarDoorButton;
    private JButton saveDoorsButton;
    private JButton loadDoorsButton;
    private JButton runSimulationButton;

    private static DecimalFormat df = new DecimalFormat("#.00");

    public ButtonsPanel(GameShow gameShow) {
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
//        add(Box.createVerticalStrut(5));
        addGoatDoorButton.addActionListener(this);
        add(addCarDoorButton);
//        add(Box.createVerticalStrut(5));
        addCarDoorButton.addActionListener(this);
        add(saveDoorsButton);
//        add(Box.createVerticalStrut(5));
        saveDoorsButton.addActionListener(this);
        add(loadDoorsButton);
        loadDoorsButton.addActionListener(this);
//        add(Box.createVerticalStrut(5));
        add(runSimulationButton);
        runSimulationButton.addActionListener(this);

    }

//    public SidePanel(GameShow gameShow) {
//        setPreferredSize(new Dimension(HEIGHT, WIDTH));
//        setLayout(new GridLayout(1, 0));
//        setBackground(Color.lightGray);
//        this.gameShow = gameShow;
//
//        addGoatDoorButton = createButton("Add door with goat");
//        addCarDoorButton = createButton("Add door with car");
//        saveDoorsButton = createButton("Save current gameshow setup");
//        loadDoorsButton = createButton("Load previously saved gameshow setup");
//        runSimulationButton = createButton("Run the simulation on current gameshow 1000 times");
//
//        add(addGoatDoorButton);
//        add(Box.createHorizontalStrut(60));
//        addGoatDoorButton.addActionListener(this);
//        add(addCarDoorButton);
//        add(Box.createHorizontalStrut(60));
//        addCarDoorButton.addActionListener(this);
//        add(saveDoorsButton);
//        add(Box.createHorizontalStrut(60));
//        saveDoorsButton.addActionListener(this);
//        add(loadDoorsButton);
//        loadDoorsButton.addActionListener(this);
//        add(Box.createHorizontalStrut(60));
//        add(runSimulationButton);
//        runSimulationButton.addActionListener(this);
//
//    }

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
            float switchWinProbability = loProbs.get(0);
            float dontSwitchWinProbability = loProbs.get(1);
            graphPanel.showProbabilityResults(switchWinProbability, dontSwitchWinProbability);

        }
        doorPanel.repaint();
    }

    public void setDoorPanel(DoorPanel dp) {
        this.doorPanel = dp;
    }

    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }
}
