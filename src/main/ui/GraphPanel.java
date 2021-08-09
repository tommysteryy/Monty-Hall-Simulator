package ui;

import model.GameShow;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class GraphPanel extends JPanel {

    private GameShow gameShow;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;

    private ButtonsPanel buttonsPanel;

    private float switchWinProbability = 0;
    private float dontSwitchWinProbability = 0;
    private float ratioOfWinProbabilities = 0;
    private JLabel probabilityLabel;



    public GraphPanel(GameShow gameShow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);
//        setVisible(false);
        this.gameShow = gameShow;
        probabilityLabel = new JLabel();
        setupProbabilityLabel();

    }

    private void setupProbabilityLabel() {
        probabilityLabel.setText("Probabilities");
        probabilityLabel.setFont(new Font("Arial", Font.BOLD, 40));

        this.add(probabilityLabel);
    }

    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
        if (ratioOfWinProbabilities != 0) {
            displayRatioComparison(g);
        }
        drawTitle(g);
    }

    private void drawTitle(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Build your own game!",25, 100);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("When you're done, press 'Run simulation'",25, 140);
        g.drawString("You may find some surprising results!",25, 165);

        g.drawString("See if you can get a consistently higher",25, 200);
        g.drawString("win probability by not switching.",25, 225);

        g.drawString("Enjoy!", 25, 260);
    }

    private void displayRatioComparison(Graphics g) {
        g.drawString("You are " + ratioOfWinProbabilities + "x more likely to win if you switch!",
                25, 600);
    }

    private void drawGraph(Graphics g) {


        int heightOfSwitchBar = (int) (switchWinProbability * 100);
        int heightOfNotSwitchBar = (int) (dontSwitchWinProbability * 100);

        g.setColor(new Color(153, 51, 255));
        g.drawRect(106, 480 - heightOfSwitchBar,50, heightOfSwitchBar);
        g.fillRect(106, 480 - heightOfSwitchBar, 50, heightOfSwitchBar);


        g.setColor(Color.cyan);
        g.drawRect(225, 480 - heightOfNotSwitchBar, 50, heightOfNotSwitchBar);
        g.fillRect(225, 480 - heightOfNotSwitchBar, 50, heightOfNotSwitchBar);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 18));;
        g.drawString("Switch", 100, 500);
        g.drawString("Don't Switch", 200, 500);
        g.drawLine(75, 480, 325, 480);

        g.setFont(new Font("Arial", Font.BOLD, 14));;
        g.drawString(String.valueOf(switchWinProbability), 113, 480 - heightOfSwitchBar - 5);
        g.drawString(String.valueOf(dontSwitchWinProbability), 232, 480 - heightOfNotSwitchBar - 5);
//        g.drawRect(106 + 1, 480 - heightOfSwitchBar + 1,50 + 1, heightOfSwitchBar + 1);
//        g.drawString("Switch win probability = " + switchWinProbability, 200, 300 );;
        
    }
    
    
}



