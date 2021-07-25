package ui;

import model.Car;
import model.Door;
import model.GameShow;
import model.Goat;

import java.util.Scanner;

public class RunGameShow {

    private Scanner input;
    private GameShow gameShow = new GameShow();
    private Car carRed = new Car("red");
    private Goat goat = new Goat();

    private Door door1C = new Door(1, carRed);
    private Door door2G = new Door(2, goat);
    private Door door3G = new Door(3, goat);

    public RunGameShow() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        System.out.println("Type q to continue in the program:");
        command = input.next();
        while (!command.equals("q")) {
            System.out.println("Try again. Type q to continue in the program:");
            command = input.next();
        }

        System.out.println("\nSee you again!");
    }

    public void gameIntro() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);
        System.out.println("Welcome to the game show! I am Monty Hall, your host for today. \n"
                + "What is your name?");
        String name = input.next();
        System.out.println("Hello " + name + ", let me explain how this game will work. \n");
        String command = "notyet";
        while (!command.equals("r")) {
            System.out.println("There are three doors:");
            System.out.println(gameShow.presentDoors());
            System.out.println("Behind each door is a prize - two have a goat, one of them has a beautiful new red car.\n");
            System.out.println("Soon, you will pick your door. Then, I will show you the prize of one of the other doors"
                    + " and then I will offer you a special chance to change your door selection.");
            System.out.println("Then, once locked in, we will reveal your prize, and you get the prize behind your door."
                    + " It will make more sense as we go, I promise.");
            System.out.println("Ready? \n \t - r (Ready!) \n \t - n (Not yet, tell me again!)");
            command = input.next();
            while (!command.equals("n") && !command.equals("r")) {
                System.out.println("Try again. Type r for 'Ready!' and n for 'Not yet!'");
                command = input.next();
            }
        }
        System.out.println("Sweet! Let's go over to the game.");
    }


}

