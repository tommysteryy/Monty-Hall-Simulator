package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private Scanner input = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final String fileDestination = "./data/customgameshow.json";
    private GameShow gameShow;

    public Simulation() {
        jsonWriter = new JsonWriter(fileDestination);
        jsonReader = new JsonReader(fileDestination);
        gameShow = new GameShow();
        gameShow.setupStandardGameShow();
    }

    public void runStandardGameSimulations() {

        GameShow sampleGameShow = new GameShow();
        sampleGameShow.setupStandardGameShow();

        System.out.println("Now that you have seen the gameshow, we will run some simulations. \n"
                + "Which tactic is better? Switching or not switching? The math behind the game says that one is much "
                + "better than the other. Let's find out which is which. \n");
        Integer numTimesToRunSimulation = askHowManyTimesToRunSimulation();
        runTheSimulationLoop(sampleGameShow, numTimesToRunSimulation);

        System.out.println("Would you like to see the conditional probability behind this?\n\t-y (Yes!) \t -n(Nah!)");
        String answer = input.next();
        while (!(answer.equals("y")) && !answer.equals("n")) {
            System.out.println("That was not a valid answer, please try again: ");
            answer = input.next();
        }
        if (answer.equals("y")) {
            showMathOfGame();
        } else {
            System.out.println("Okay! Maybe next time. Let's move to the final section of our game.");
            customGameBuilding();
        }
    }

    public void showMathOfGame() {
        // FUNCTION TO SHOW ALL THE MATH BEHIND THE 3-DOOR GAME
    }

    public void runTheSimulationLoop(GameShow gameShow, int numTimesToRunSimulation) {
        System.out.println("Okay, starting now... \n");
        float numWinsSwitch = 0;
        float numWinsDontSwitch = 0;
        for (int i = 0; i < numTimesToRunSimulation; i++) {

            gameShow.unselectAllDoors();

            if (gameShow.runSimulationOnceDontSwitch()) {
                numWinsDontSwitch += 1;
            }
            if (gameShow.runSimulationOnceSwitch()) {
                numWinsSwitch += 1;
            }
        }
        float switchWinProbability = numWinsSwitch / numTimesToRunSimulation;
        float dontSwitchWinProbability = numWinsDontSwitch / numTimesToRunSimulation;
        float multiplierForSwitching = switchWinProbability / dontSwitchWinProbability;

        System.out.println("Okay! The results are in:\n \t -Switching everytime: With this tactic, you won "
                + (int) numWinsSwitch + " times out of " + numTimesToRunSimulation + ", giving you a win probability "
                + "of " + switchWinProbability + ".\n");
        System.out.println("\t -Not Switching every time: With this tactic, you won "
                + (int) numWinsDontSwitch + " times out of " + numTimesToRunSimulation + ", giving you a win "
                + "probability of " + dontSwitchWinProbability + ".\n");
        System.out.println("This means you are " + multiplierForSwitching + " more likely to win if you switch!");
    }

    public void customGameBuilding() {
        GameShow customGameShow = new GameShow();
        System.out.println("Now, you have the chance to build your own Monty Hall game. You can fully explore"
                + " the mathematics behind it as you make more and more combinations, and it'll build your intuition.");
        System.out.println("Do you want to load a saved Monty Hall game show set up?\n\t-y (Yes!)"
                + "\t-n (No, I'll start from scratch.\n>>>");
        String loadFileAnswer = askUserForInputPlusGuards("y", "n");
        if (loadFileAnswer.equals("y")) {
            customGameShow = loadGameShow();
            System.out.println("Take a look at your doors right now.");
        } else {
            customGameShow.setupStandardGameShow();
            System.out.println("\nTake a look at the doors we have right now. This is the standard gameshow set up:");
        }
        customGameShow.openAllDoors();
        System.out.println(customGameShow.presentDoors());
        askAddDoorsWithLoop(customGameShow);
        runSimulationsOnNewGameshow(customGameShow,
                "Now, let's see how win probabilities are with your new gameshow format!");

        askIfWantToSaveAndSaving(customGameShow);
        System.out.println("That's it! Monty Hall out.");
    }

    private void askIfWantToSaveAndSaving(GameShow customGameShow) {
        System.out.println("Would you like to save your simulation?\n\t-y (Yes!)\t -n (No, it's okay)");
        String saveAnswer = askUserForInputPlusGuards("y", "n");
        if (saveAnswer.equals("y")) {
            saveGameShow(customGameShow);
        }
    }

    private void runSimulationsOnNewGameshow(GameShow customGameShow, String s) {
        System.out.println(s);
        Integer numTimesToRunSimulation = askHowManyTimesToRunSimulation();
        runTheSimulationLoop(customGameShow, numTimesToRunSimulation);
    }

    public void askAddDoorsWithLoop(GameShow gameShow) {
        System.out.println("Do you want to add a door?\n\t -y (Yes!)\t -n (No, the current game is okay)");
        String answerAdd = input.next();
        while (!(answerAdd.equals("y")) && !answerAdd.equals("n")) {
            System.out.println("That was not a valid answer, please try again: ");
            answerAdd = input.next();
        }
        while (answerAdd.equals("y")) {
            gameShow.openAllDoors();
            System.out.println("This is what we have so far: ");
            System.out.println(gameShow.presentDoors());
            addDoorsToGame(gameShow);
            System.out.println("Do you want to add another door?\n\t -y (Yes!)\t -n (No, I'm done!)");
            answerAdd = input.next();
            while (!(answerAdd.equals("y")) && !answerAdd.equals("n")) {
                System.out.println("That was not a valid answer, please try again: ");
                answerAdd = input.next();
            }
        }
        System.out.println("Great! This is the result of your DIY gameshow then!");
        gameShow.openAllDoors();
        System.out.println(gameShow.presentDoors());
    }

    public GameShow addDoorsToGame(GameShow gameShow) {
        int currentDoorID = gameShow.getLargestDoorID() + 1;
        System.out.println("You are now making Door " + currentDoorID + ". Do you want to have a goat or a "
                + "car behind this door?\n\t -g (Goat!)\t -c (Car!)");
        String prizeForDoor = input.next();
        while (!prizeForDoor.equals("g") && !prizeForDoor.equals("c")) {
            System.out.println("That was not a valid answer, please try again: ");
            prizeForDoor = input.next();
        }
        if (prizeForDoor.equals("g")) {
            Prize goat = new Goat();
            Door doorBeingAdded = new Door(currentDoorID, goat);
            gameShow.addDoor(doorBeingAdded);
        } else {
            Prize car = new Car("red");
            Door doorBeingAdded = new Door(currentDoorID, car);
            gameShow.addDoor(doorBeingAdded);
        }
        return gameShow;
    }

    public Integer askHowManyTimesToRunSimulation() {
        System.out.println("How many times would you like to run the simulation? Please input a "
                + "number between 100 and 1000.\n");
        Integer numTimesToRunSimulation = Integer.parseInt(input.next());
        while (numTimesToRunSimulation > 1000 || numTimesToRunSimulation < 100) {
            System.out.println("That was not between the range of 100 and 1000. Please try again.");
            numTimesToRunSimulation = Integer.parseInt(input.next());
        }
        return numTimesToRunSimulation;
    }

    // EFFECTS: saves the workroom to file
    public void saveGameShow(GameShow gameShow) {
        try {
            jsonWriter.open();
            jsonWriter.write(gameShow);
            jsonWriter.close();
            System.out.println("Saved your custom gameshow to " + fileDestination);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + fileDestination);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public GameShow loadGameShow() {
        GameShow gameShow = new GameShow();
        try {
            gameShow = jsonReader.read();
            System.out.println("Loaded saved gameshow from " + fileDestination);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + fileDestination);
        }
        return gameShow;
    }

    // EFFECTS: asks for user input and has to be one of the two inputs, or else will keep asking
    public String askUserForInputPlusGuards(String acceptedAnswer1, String acceptedAnswer2) {
        String answerAdd = input.next();
        while (!(answerAdd.equals(acceptedAnswer1)) && !answerAdd.equals(acceptedAnswer2)) {
            System.out.println("That was not a valid answer, please try again: ");
            answerAdd = input.next();
        }
        return answerAdd;
    }

    public List<Float> runTheSimulationLoopReturnProbabilities(GameShow gameShow, int numTimesToRunSimulation) {
//        System.out.println("Okay, starting now... \n");
        List<Float> listOfProbabilities = new ArrayList<>();
        GameShow proxyGameShow = new GameShow();

        proxyGameShow.setupArbitraryGameShow(gameShow.getDoors());

        float numWinsSwitch = 0;
        float numWinsDontSwitch = 0;
        for (int i = 0; i < numTimesToRunSimulation; i++) {

            proxyGameShow.unselectAllDoors();

            if (proxyGameShow.runSimulationOnceDontSwitch()) {
                numWinsDontSwitch += 1;
            }
            if (proxyGameShow.runSimulationOnceSwitch()) {
                numWinsSwitch += 1;
            }
        }
        float switchWinProbability = numWinsSwitch / numTimesToRunSimulation;
        float dontSwitchWinProbability = numWinsDontSwitch / numTimesToRunSimulation;

        listOfProbabilities.add(switchWinProbability);
        listOfProbabilities.add(dontSwitchWinProbability);

        return listOfProbabilities;

    }
}
