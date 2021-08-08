package ui;

import model.GameShow;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

    private GameShow gameShow;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private ButtonsPanel buttonsPanel;

    private double switchWinProbability = 0;
    private double dontSwitchWinProbability = 0;



    public GraphPanel(GameShow gameShow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);
//        setVisible(false);
        this.gameShow = gameShow;
    }

    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public void showProbabilityResults(Float switchWinProbability, Float dontSwitchWinProbability) {
        this.switchWinProbability = switchWinProbability;
        this.dontSwitchWinProbability = dontSwitchWinProbability;
        repaint();
//        setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {

        g.drawRect(200, 200, 50, (int) (switchWinProbability * 100));
        g.fillRect(200, 200, 50, (int) (switchWinProbability * 100));
        g.drawRect(150, 90, 50, (int) (dontSwitchWinProbability * 100));
        g.fillRect(150, 90, 50, (int) (dontSwitchWinProbability * 100));

        Color myColour = new Color(153, 51, 255);
        g.setColor(myColour);

    }
}



