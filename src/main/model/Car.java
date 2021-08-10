package model;

/*
A class for the prize type of car
 */

public class Car extends Prize {

    private String colour;

    // constructor
    public Car(String colour) {
        this.colour = colour;
    }


    // EFFECTS: is used to display the contents of the prize
    @Override
    public String reveal() {
        return "a " + this.colour.toLowerCase() + " car";
    }

    // EFFECTS: used to convert the name of the class into a string
    @Override
    public String toString() {
        return "Car";
    }
}
