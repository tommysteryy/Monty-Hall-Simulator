package ui;

public class Main {
    private static RunGameShow game = new RunGameShow();
    private static Simulation simulation = new Simulation();

    public static void main(String[] args) {
//        game.gameIntro();
//        game.oneRegularGame();
        simulation.runStandardGameSimulations();
    }
}
