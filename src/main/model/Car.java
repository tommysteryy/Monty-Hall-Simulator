package model;

public class Car extends Prize {

    private String colour;

    public Car(String colour) {
        this.colour = colour;
    }

    @Override
    public String reveal() {
        return "a " + this.colour + " car";
    }
}
