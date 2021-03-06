package ui.gui;

import model.Door;
import model.GameShow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/*
A panel where all doors are displayed. Can be 3 doors (standard) but can also be an arbitrary number

Door img: http://clipart-library.com/clip-art/41-415526_door-png-download-png-image-with-transparent-background.htm
 */

// https://stackoverflow.com/questions/4634107/is-there-any-way-to-add-a-mouselistener-to-a-graphic-object?noredirect=1&lq=1
/*
Idea:
Hold a list of Rectangle2Ds, that will be the same size as GameShow.getDoors(), with the Rectangle2D items in there.

1) Constructor has:
for door in door gameshow:

2) PaintComponent will call:
- for (Rectangle2D door: listofDoors) {
      g.fill(door);
}



- Make all the rectangles into a Rectangle2D object, using
-   Rectangle2D door = new Rectangle2D.Double(x, y, width, height) and

- the styling

Goat picture: https://www.cleanpng.com/png-goat-cartoon-animal-grey-goat-head-pull-material-f-188538/download-png.html
Car picture:

NEED A LIST OF LABELS AGAIN
 */

public class DoorPanel extends JComponent implements MouseListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1500;
    private static final int DOORHEIGHT = 175;
    private static final int DOORWIDTH = 100;
    private static final int DOOR_START_XPOS = 90;
    private static final int DOOR_START_YPOS = 90;
    private GameShow gameshow;
    private ButtonsPanel buttonsPanel;
    private List<Rectangle2D> listOfGraphicDoors;
    private List<JLabel> listOfLabelDoors;
    private ImageIcon goatIcon;
    private ImageIcon carIcon;

//    Rectangle2D rectangle2D = new Rectangle2D.Double(70, 400, 100, 100);

    // constructor
    public DoorPanel(GameShow gameshow) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.lightGray);
        setLayout(null);
        this.gameshow = gameshow;
        addMouseListener(this);
        listOfGraphicDoors = new ArrayList<>();
        listOfLabelDoors = new ArrayList<>();
        goatIcon = new ImageIcon("src/main/Images/goat_resized.png");
        carIcon = new ImageIcon("src/main/Images/car.png");

    }

    // MODIFIES: this
    // EFFECTS: constantly repaints the doors in the gameshow
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        drawSingleEllipse(g);
        drawDoors(g);
        drawLegend(g);
        for (JLabel label: listOfLabelDoors) {
            this.add(label);
        }
    }

//    private void drawSingleEllipse(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.fill(rectangle2D);
//    }

    private void drawLegend(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("Gameshow Panel", 265, 50);


    }

//    private void drawGraph(Graphics g, Float switchWinProbability, Float dontSwitchWinProbability) {
//        g.drawRect(630, 90, 50, (int) (switchWinProbability * 100));
//        g.fillRect(630, 90, 50, (int) (switchWinProbability * 100));
//        g.drawRect(700, 90, 50, (int) (dontSwitchWinProbability * 100));
//        g.fillRect(700, 90, 50, (int) (dontSwitchWinProbability * 100));
//        g.setColor(Color.green);
//
//    }


//    public void drawRectangles(Graphics g) {
//        int numberOfRectangles = gameshow.getSize() - 1;
//        for (int i = 0; i < numberOfRectangles; i++) {
//
//            JPanel panel = new JPanel(); //create a panel
//            panel.setPreferredSize(new Dimension(DOORWIDTH, DOORHEIGHT)); //size it
//            if ((i % 2) == 0) { //color it
//                Door doorToDraw = gameshow.find((i + 1) / 2);
//                doorToDraw.draw(g);
//            } else {
//                panel.setBackground(Color.BLACK);
//            }
//
//            add(panel);//add it
//
//
//        }
//    }

    // MODIFIES: THIS
    // EFFECTS: draws the doors in multiple rows of 4 doors, equally spaced
    public void drawDoors(Graphics g) {
//        for (Door d: gameshow.getDoors()) {
//            drawDoor(g, d)
//            add(Box.createHorizontalStrut(50));
//        }
        int separation = 180;
        int numFullRows = gameshow.getSize() / 4;


        for (int i = 0; i < numFullRows; i++) {
            List<Door> doorsToDraw = gameshow.getDoors().subList((i * 4), ((i + 1) * 4));
            drawSubListOfDoors(doorsToDraw, g, i * 250 + 90);
        }

        int numberOfLeftoverDoors = gameshow.getSize() % 4;
        List<Door> doorsLeftoverToDraw = gameshow.getDoors().subList(gameshow.getSize() - numberOfLeftoverDoors,
                gameshow.getSize());
        drawSubListOfDoors(doorsLeftoverToDraw, g, numFullRows * 250 + 90);

    }

    // REQUIRES: List<Door> <= 4
    // MODIFIES: this
    // EFFECT: draws any given list of doors in a straight row
    private void drawSubListOfDoors(List<Door> doorsToDraw, Graphics g, int ypos) {
        for (int i = 0; i < (doorsToDraw.size()); i++) {
            Door doorToDraw = doorsToDraw.get(i);
            drawGraphicDoor(g, doorToDraw, 90 + i * 180, ypos);
            drawIconDoor(g, doorToDraw, 90 + i * 180, ypos);
            drawAppearanceDoor(g, doorToDraw, 90 + i * 180, ypos);
        }
    }

    // MODIFIES: this
    // EFFECT: draws a single door at a specific xpos, ypos and sets the colour
    private void drawAppearanceDoor(Graphics g, Door d, int xpos, int ypos) {

        int doorIndex = gameshow.getDoors().indexOf(d);
        JLabel doorIconClicked = listOfLabelDoors.get(doorIndex);

        if (!(d.isOpen())) {
            Color doorBaseColour = new Color(127, 81, 51);
            Color doorWindowColour = new Color(213, 176, 143);
            g.setColor(doorBaseColour);
            g.drawRect(xpos, ypos, DOORWIDTH, DOORHEIGHT);
            g.fillRect(xpos, ypos, DOORWIDTH, DOORHEIGHT);

            g.setColor(doorWindowColour);
            g.drawRect(xpos + 15, ypos + 15,25, 60);
            g.fillRect(xpos + 15, ypos + 15,25, 60);

            g.drawRect(xpos + 60, ypos + 15,25, 60);
            g.fillRect(xpos + 60, ypos + 15,25, 60);

            g.drawRect(xpos + 15, ypos + 100,25, 60);
            g.fillRect(xpos + 15, ypos + 100,25, 60);

            g.drawRect(xpos + 60, ypos + 100,25, 60);
            g.fillRect(xpos + 60, ypos + 100,25, 60);

            g.setColor(new Color(232, 229, 229));
            g.drawOval(xpos + 85, ypos + 83, 10, 10);
            g.fillOval(xpos + 85, ypos + 83, 10, 10);

            doorIconClicked.setVisible(false);

        } else if (d.isOpen()) {
            this.add(doorIconClicked);
            doorIconClicked.setVisible(true);
        }


    }

    // EFFECT: draws the Rectangle2D version of the doors, interactable with MouseEvent
    private void drawGraphicDoor(Graphics g, Door d, int xpos, int ypos) {

        Rectangle2D rectangle2D = new Rectangle2D.Double(xpos, ypos, DOORWIDTH, DOORHEIGHT);
        Graphics2D g2d = (Graphics2D) g;
//        if (d.prizeIsGoat()) {
//            g2d.setColor(Color.cyan);
//        } else if (d.prizeIsCar()) {
//            g2d.setColor(Color.magenta);
//        }
        g2d.setColor(new Color(127, 81, 51));
        g2d.fill(rectangle2D);

        g2d.setColor(new Color(211,211,211));
        g2d.fill(new Rectangle2D.Double(xpos + 6, ypos + 6, DOORWIDTH - 12, DOORHEIGHT - 12));
        if (!listOfGraphicDoors.contains(rectangle2D)) {
            listOfGraphicDoors.add(rectangle2D);
        }

    }



    private void drawIconDoor(Graphics g, Door d, int xpos, int ypos) {
        JLabel doorLabel = new JLabel();
        doorLabel.setBounds(xpos, ypos, DOORWIDTH, DOORHEIGHT);
        doorLabel.setVisible(false);
        if (d.prizeIsCar()) {
            doorLabel.setIcon(carIcon);
        } else if (d.prizeIsGoat()) {
            doorLabel.setIcon(goatIcon);
        }
        if (!(containsDoorLabel(doorLabel, xpos, ypos))) {
            listOfLabelDoors.add(doorLabel);
        }
    }

    private boolean containsDoorLabel(JLabel doorLabel, int xpos, int ypos) {
        if (listOfLabelDoors.size() != 0) {
            for (int i = 0; i < listOfLabelDoors.size(); i++) {
                Rectangle labelBounds = doorLabel.getBounds();
                Rectangle currentLabelBounds = listOfLabelDoors.get(i).getBounds();
                if (labelBounds.x == currentLabelBounds.x && labelBounds.y == currentLabelBounds.y) {
                    return true;
                }
            }
        }
        return false;


    }

    protected void clearIconAndGraphicDoors() {
        listOfGraphicDoors.clear();
        listOfLabelDoors.clear();
        this.removeAll();
    }

//    public void placeAllDoors() {
////        for (Door d: gameshow.getDoors()) {
////            drawDoor(g, d)
////            add(Box.createHorizontalStrut(50));
////        }
//        int separation = 180;
//        int numFullRows = gameshow.getSize() / 4;
//
//
//        for (int i = 0; i < numFullRows; i++) {
//            List<Door> doorsToDraw = gameshow.getDoors().subList((i * 4), ((i + 1) * 4));
//            placeSubListOfDoors(doorsToDraw, i * 250 + 90);
//        }
//
//        int numberOfLeftoverDoors = gameshow.getSize() % 4;
//        List<Door> doorsLeftoverToDraw = gameshow.getDoors().subList(gameshow.getSize() - numberOfLeftoverDoors,
//                gameshow.getSize());
//        placeSubListOfDoors(doorsLeftoverToDraw, numFullRows * 250 + 90);
//
//    }
//
//    private void placeSubListOfDoors(List<Door> doorsToDraw, int ypos) {
//        for (int i = 0; i < (doorsToDraw.size()); i++) {
//            Door doorToDraw = doorsToDraw.get(i);
//            placeDoor(doorToDraw, 90 + i * 180, ypos);
//        }
//    }
//
//
//    private void placeDoor(Door d, int xpos, int ypos) {
//
//        testDoorImage = new JLabel();
//        doorIcon = new ImageIcon("src/main/ui/doorfinal.png");
//        testDoorImage.setIcon(doorIcon);
//        testDoorImage.setBounds(xpos, ypos, 71, 96);
//        add(testDoorImage);
//
//        if (d.prizeIsGoat()) {
//            Border border = BorderFactory.createLineBorder(Color.green);
//            testDoorImage.setBorder(border);
//        } else {
//            Border border = BorderFactory.createLineBorder(Color.cyan);
//            testDoorImage.setBorder(border);
//        }
//
//
//    }


    @Override
    public void mouseClicked(MouseEvent e) {

        for (Rectangle2D rectangle : listOfGraphicDoors) {
            if ((e.getButton() == 1) && rectangle.contains(e.getX(), e.getY())) {
                int doorNum = listOfGraphicDoors.indexOf(rectangle) + 1;
                Door doorClicked = gameshow.getDoors().get(doorNum - 1);
                if (doorClicked.isOpen()) {
                    doorClicked.close();
                } else if (!(doorClicked.isOpen())) {
                    doorClicked.open();
                }
//                System.out.println("Behind this door is " + gameshow.getDoors().get(doorNum - 1).getPrize().reveal());
                repaint();
                break;
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // MODIFIES: THIS
    // EFFECT: sets the buttonpanel - sidepanel relationship
    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public void setGameShow(GameShow gameShow) {
        this.gameshow = gameShow;
    }

}

