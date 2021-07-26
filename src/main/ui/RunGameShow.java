package ui;

import model.Car;
import model.Door;
import model.GameShow;
import model.Goat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunGameShow {

    private Scanner input = new Scanner(System.in);
    private GameShow gameShow = new GameShow();
    private final Car carRed = new Car("red");
    private final Goat goat = new Goat();
    private String name;

    private final Door door1C = new Door(1, carRed);
    private final Door door2G = new Door(2, goat);
    private final Door door3G = new Door(3, goat);


    public RunGameShow() {
    }

    public void gameIntro() {
        setupGameShowAndDoors();
        System.out.println("Welcome to the game show! I am Monty Hall, your host for today. \n"
                + "What is your name?");
        name = input.next();
        System.out.println("Hello " + name + ", let me explain how this game will work. \n");
        String command = "not yet";
        while (!command.equals("r")) {
            System.out.println("There are three doors:");
            System.out.println(gameShow.presentDoors());
            System.out.println("Behind each door is a prize - two have a goat, one of them has a  new red car.\n");
            System.out.println("Soon, you will pick your door. Then, I will show you what's behind one of the other"
                    + " doors and then I will offer you a special chance to change your door selection.");
            System.out.println("Then, once locked in, we will reveal your prize, and you get the prize behind your"
                    + " door. It will make more sense as we go, I promise.");
            System.out.println("Ready? \n \t - r (Ready!) \n \t - n (Not yet, tell me again!)");
            command = input.next();
            while (!command.equals("n") && !command.equals("r")) {
                System.out.println("Try again. Type r for 'Ready!' and n for 'Not yet!'");
                command = input.next();
            }
        }
        System.out.println("Sweet! Let's go over to the game.");
    }

    public void oneRegularGame() {
        setupGameShowAndDoors();
        System.out.println("Here are your three doors: \n");
        System.out.println(gameShow.presentDoors());
        selectFirstDoor();
        int doorChoice = gameShow.currentSelectedDoor().getId();
        System.out.println(gameShow.presentDoors());
        System.out.println("Interesting pick... alright. Let me go back there and check it out! \n.\n.\n."
                + "Okay, let me show you something.");
        Door doorToReveal = gameShow.nonSelectedGoatDoor();
        gameShow.openDoor(doorToReveal.getId());
        System.out.println(gameShow.presentDoors());
        switchDoors(doorChoice, doorToReveal.getId()); // have to supply to method because it depends on previous things
        System.out.println(gameShow.presentDoors());
        System.out.println("Alright! It is locked in.");
        revealGameShowPrize();
        boolean wantToPlayAgain = wantToPlayAgain();
        if (wantToPlayAgain) {
            oneRegularGame();
        } else {
            System.out.println("Thank you, and goodbye!");
        }
    }

    public void setupGameShowAndDoors() {
        gameShow.clearDoors();
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);
        gameShow.randomizeDoors();
        gameShow.closeAllDoors();
        gameShow.unselectAllDoors();
    }

    public void revealGameShowPrize() {
        System.out.println("And now.. to reveal your prize! \n");
        gameShow.currentSelectedDoor().open();
        System.out.println(gameShow.presentDoors());

        if (gameShow.currentSelectedDoor().prizeIsCar()) {
            System.out.println("Congratulations! You made the right call. The beautiful car is yours!");
        } else if (gameShow.currentSelectedDoor().prizeIsGoat()) {
            System.out.println("Unfortunately, you did not get the car... but hey! You'll come back better.");
        }
    }

    public boolean wantToPlayAgain() {
        System.out.println("Would you like to play again?\n - y (Yes!) \t - n (No, maybe next time)");
        String restartCommand = input.next();
        while (!restartCommand.equals("y") && !restartCommand.equals("n")) {
            System.out.println("That was not a valid choice, please pick again:");
            restartCommand = input.next();
        }
        return restartCommand.equals("y");
    }

    public void selectFirstDoor() {
        System.out.println("Which one do you want to select? Choose using the door number. ");
        String doorChoiceInput = input.next();
        while (!doorChoiceInput.equals("1") && !doorChoiceInput.equals("2") && !doorChoiceInput.equals("3")) {
            System.out.println("That was not a valid door number. Pick again.");
            doorChoiceInput = input.next();
        }
        int doorChoice = Integer.parseInt(doorChoiceInput);
        gameShow.selectDoor(doorChoice);
    }

    public void switchDoors(int doorChoice, int doorToReveal) {

        System.out.println("Now, this is unprecedented, but I will offer you an exclusive chance to switch your door"
                + "to the other door. What does your GUT tell you," + name + "? \n");
        System.out.println("\t- s (Switch!) \t- n (No switch!)");
        String switchDecision = input.next();
        while (!switchDecision.equals("s") && !switchDecision.equals("n")) {
            System.out.println("That was not a valid choice, please pick again:");
            switchDecision = input.next();
        }
        List<Door> doorsDontWant = new ArrayList<>();
        doorsDontWant.add(gameShow.find(doorChoice));
        doorsDontWant.add(gameShow.find(doorToReveal));
        if (switchDecision.equals("s")) {
            Door doorToSwitchTo = gameShow.randomDoorThatIsntInTheList(doorsDontWant);
            gameShow.unselectDoor(doorChoice);
            gameShow.selectDoor(doorToSwitchTo.getId());
        }
    }
}