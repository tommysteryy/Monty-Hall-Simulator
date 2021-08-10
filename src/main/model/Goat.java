package model;

/*
the class of the goat prize
 */
public class Goat extends Prize {

    public Goat(){}

    // effect: returns the reveal string of the prize
    @Override
    public String reveal() {
        return "a goat";
    }

    // effect: returns the string version of the prize
    @Override
    public String toString() {
        return "Goat";
    }


}
