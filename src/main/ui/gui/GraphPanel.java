package ui.gui;

import model.GameShow;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/*
Graph panel is on the eatside of the JFrame, houses the graph, title, and explanations
 */

public class GraphPanel extends JPanel {

    private GameShow gameShow;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;

    private ButtonsPanel buttonsPanel;

    private float switchWinProbability = 0;
    private float dontSwitchWinProbability = 0;
    private float ratioOfWinProbabilities = 0;
    private JLabel probabilityLabel;


    // Constructor
    public GraphPanel(GameShow gameShow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);
//        setVisible(false);
        this.gameShow = gameShow;
//        probabilityLabel = new JLabel();
//        setupProbabilityLabel();

    }

    // MODIFIES: this
    // EFFECT: sets up the Jlabel used to title the graphpanel
    private void setupProbabilityLabel() {


        probabilityLabel.setText("Probabilities");
        probabilityLabel.setFont(new Font("Arial", Font.BOLD, 40));

        this.add(probabilityLabel);
    }



    // MODIFIES: this
    // EFFECT: sets up the relationship between the ButtonPanel and this graph panel
    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    // REQUIRES: the two probabilities to be less than 1, or else the bar might get too big
    // MODIFIES: this
    // EFFECT: rounds the win probabilities to 3d.p and allows the panel to present it
    public void showProbabilityResults(float switchWinProbability, float dontSwitchWinProbability) {

        float resultSwitch = BigDecimal.valueOf(switchWinProbability)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN)
                .floatValue();
        float resultDontSwitch = BigDecimal.valueOf(dontSwitchWinProbability)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN)
                .floatValue();
        float ratioOfWinProbabilities = resultSwitch / resultDontSwitch;
        float resultRatioOfWinProbabilities = BigDecimal.valueOf(ratioOfWinProbabilities)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN)
                .floatValue();

        this.switchWinProbability = resultSwitch;
        this.dontSwitchWinProbability = resultDontSwitch;
        this.ratioOfWinProbabilities = resultRatioOfWinProbabilities;
        repaint();
//        setVisible(true);

    }

    // MODIFIES: this
    // EFFECT: continuously repaints/ draws the elements on the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
        if (ratioOfWinProbabilities != 0) {
            displayRatioComparison(g);
        }
        drawTitle(g);
    }

    // EFFECT: draws the title + subtitles on the top of the panel
    private void drawTitle(Graphics g) {

        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("Welcome!", 120, 50);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Build your own game!",25, 100);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("If you start to forget, click the door to show",25, 140);
        g.drawString("what is behind them.",25, 165);

        g.drawString("When you're done, press 'Run simulation'",25, 200);
        g.drawString("You may find some surprising results!",25, 225);

        g.drawString("See if you can get a consistently higher", 25, 260);
        g.drawString("win probability by not switching.", 25, 285);

        g.drawString("Enjoy!", 25, 320);
    }

    // EFFECT: draws the comment at the bottom about comparing the two choices
    private void displayRatioComparison(Graphics g) {
        g.drawString("You are " + ratioOfWinProbabilities + "x more likely to win if you switch!",
                35, 600);
    }

    // EFFECT: draws the barchart for the two win probabilities 
    private void drawGraph(Graphics g) {
        int heightOfSwitchBar = (int) (switchWinProbability * 130);
        int heightOfNotSwitchBar = (int) (dontSwitchWinProbability * 130);

        g.setColor(new Color(153, 51, 255));
        g.drawRect(106, 530 - heightOfSwitchBar,50, heightOfSwitchBar);
        g.fillRect(106, 530 - heightOfSwitchBar, 50, heightOfSwitchBar);

        g.setColor(Color.cyan);
        g.drawRect(225, 530 - heightOfNotSwitchBar, 50, heightOfNotSwitchBar);
        g.fillRect(225, 530 - heightOfNotSwitchBar, 50, heightOfNotSwitchBar);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Switch", 100, 550);
        g.drawString("Don't Switch", 200, 550);
        g.drawLine(75, 530, 325, 530);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(String.valueOf(switchWinProbability), 113, 530 - heightOfSwitchBar - 5);
        g.drawString(String.valueOf(dontSwitchWinProbability), 232, 530 - heightOfNotSwitchBar - 5);
//        g.drawRect(106 + 1, 480 - heightOfSwitchBar + 1,50 + 1, heightOfSwitchBar + 1);
//        g.drawString("Switch win probability = " + switchWinProbability, 200, 300 );;
        
    }
    
    
}



