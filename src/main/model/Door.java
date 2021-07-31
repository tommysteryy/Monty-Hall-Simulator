package model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Door {

    private int id; // represents the door number (every door in the game must be unique)
    private Prize prize; // represents the item behind the door

    private boolean isOpen; // represents the state of the door, if its open or not
                            // If a door is open, that means the prize can be shown to the contestant
                            // if a door is closed, the door can't be shown to the contestant
    private boolean selected; // represents if the user has selected this door

    // Constructor
    public Door(int id, Prize prize) {
        this.id = id;
        this.prize = prize;
        this.isOpen = false;
        this.selected = false;
    }

    // methods

    // MODIFIES: this
    // EFFECT: sets door to open
    public void open() {
        this.isOpen = true;
    }

    // MODIFIES: this
    // EFFECT: sets door to closed
    public void close() {
        this.isOpen = false;
    }

    // MODIFIES: this
    // EFFECT: sets selected to true
    public void select() {
        this.selected = true;
    }

    // MODIFIES: this
    // EFFECT: sets selected to false
    public void unselect() {
        this.selected = false;
    }

    // GETTERS

    // EFFECT: return the id of the door
    public int getId() {
        return this.id;
    }

    // EFFECT: return true if door is open, false otherwise
    public boolean isOpen() {
        return this.isOpen;
    }

    // EFFECT: return the prize of the door
    public Prize getPrize() {
        return this.prize;
    }

    // EFFECT: return the prize of the door
    public boolean isSelected() {
        return this.selected;
    }

    // EFFECT: return true if the prize is a goat
    public boolean prizeIsGoat() {
        boolean b = this.prize instanceof Goat;
        return b;
    }

    // EFFECT: return true if the prize is a goat
    public boolean prizeIsCar() {
        boolean b = this.prize instanceof Car;
        return b;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doorID", this.id);
        jsonObject.put("prize", this.prize);
        return jsonObject;
    }
}
