package ui;

import model.Car;
import model.Door;
import model.GameShow;
import model.Goat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private final Car carRed = new Car("red");
    private final Goat goat = new Goat();
    private final Door door1C = new Door(1, carRed);
    private final Door door2G = new Door(2, goat);
    private final Door door3G = new Door(3, goat);

    private Scanner input = new Scanner(System.in);

    public Simulation() {}

    public void runStandardGameSimulations() {

        GameShow sampleGameShow = new GameShow();
        List<Door> listofStandardDoors = new ArrayList<>();
        List<Door> listToUse = Arrays.asList(door1C, door2G, door3G);
        listofStandardDoors.addAll(listToUse);
        sampleGameShow.setupGameShow(listofStandardDoors);

        System.out.println("Now that you have seen the gameshow, we will run some simulations. \n "
                + "How many times would you like to run the simulation? Please input a number between 100 and 1000.\n");
        Integer numTimesToRunSimulation = Integer.parseInt(input.next());
        while (numTimesToRunSimulation > 1000 || numTimesToRunSimulation < 100) {
            System.out.println("That was not between the range of 100 and 1000. Please try again.");
            numTimesToRunSimulation = Integer.parseInt(input.next());
        }

        runTheSimulationLoop(sampleGameShow, numTimesToRunSimulation);
    }

    public void runTheSimulationLoop(GameShow gameShow, int numTimesToRunSimulation) {
        System.out.println("Okay, starting now...");
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

        System.out.println("Okay! The results are in:\n \t -Switching everytime: Win probability = "
                + switchWinProbability + "\t -Not Switching every time: Win probability = " + dontSwitchWinProbability);
    }
}
