package ui;

import model.GameShow;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
A smaller bar below the DoorPanel (same width) that rolls the text/instructions by

 */
public class TextPanel extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 200;
    private static final int LABELWIDTH = 200;
    private static final int LABELHEIGHT = 150;
    private static final String FONT = "Arial";
    private JLabel displayedText;
    private JButton buttonForNext;


    public TextPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);
        displayedText = new JLabel("TEST LABEL");
        displayedText.setPreferredSize(new Dimension(LABELWIDTH, LABELHEIGHT));
        displayedText.setFont(Font.getFont(FONT));
        add(displayedText);

        buttonForNext = createButton(">>");
        buttonForNext.addActionListener(this);
        add(buttonForNext);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        displayedText.setText("Next String");
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
}
