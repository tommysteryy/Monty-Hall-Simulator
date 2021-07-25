package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameShow {

    private List<Door> doors;
    private Host host;

    public GameShow() {
        doors = new ArrayList<>();
        host = new Host();
    }

    // MODIFIES: this
    // EFFECT: if door is not in doors, adds door to doors in Gameshow, and return true.
    //         if already in doors, return false and do nothing.
    public boolean addDoor(Door door) {

        for (Door d: doors) {
            if (d.getId() == door.getId()) {
                return false;
            }
        }

        this.doors.add(door);
        return true;

    }

    // MODIFIES: this
    // EFFECT: if door is in doors, remove door from doors in Gameshow, and return true.
    //         if not in doors, return false and do nothing.
    public boolean removeDoor(int doorID) {
        if (this.containsDoor(doorID)) {
            doors.remove(this.find(doorID));
            return true;
        }
        return false;
    }

    // REQUIRES: door to be in doors, and no other door is selected
    // MODIFIES: this, door
    // EFFECT: if door is in doors, and no other door is selected, select that door and return true
    public boolean selectDoor(int doorID) {
        if (!this.atLeastOneSelected()) {
            this.find(doorID).select();
            return true;
        }
        return false;

    }

    // REQUIRES: some door to already be selected
    // MODIFIES: this, door
    // EFFECT: if door matches the door currently selected, then it unselects it and returns true
    //         if there is no door currently selected, returns false and does nothing
    //         if that door was not selected previously, then it also returns false and does nothing
    public boolean unselectDoor(int doorID) {
        if (this.atLeastOneSelected() && this.containsDoor(doorID) && this.currentSelectedDoor().getId() == doorID) {
            this.find(doorID).unselect();
            return true;
        }
        return false;
    }

    // MODIFIES: this, door
    // EFFECT: opens the door with a certain doorID
    public boolean openDoor(int doorID) {
        if (this.containsDoor(doorID)) {
            this.find(doorID).open();
            return true;
        }
        return false;
    }

    // MODIFIES: this, door
    // EFFECT: closes the door with a certain doorID
    public boolean closeDoor(int doorID) {
        if (this.containsDoor(doorID)) {
            this.find(doorID).close();
            return true;
        }
        return false;
    }


    /// HELPER FUNCTIONS

    // EFFECT: checks if any door is currently selected
    public boolean atLeastOneSelected() {
        for (Door d: doors) {
            if (d.isSelected()) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: Currently has a door selected
    // EFFECT: returns the currently selected door in doors
    public Door currentSelectedDoor() {
        for (Door d: doors) {
            if (d.isSelected()) {
                return d;
            }
        }
        return null;
    }

    // EFFECT: checks if doors contains this particular doorID
    public boolean containsDoor(int doorID) {
        for (Door d: doors) {
            if (d.getId() == doorID) {
                return true;
            }
        }
        return false;
    }


    // REQUIRES: door to be in doors (or else will return null)
    // EFFECT: returns the FIRST full door object in this.doors with certain doorID
    public Door find(int doorID) {
        for (Door d: doors) {
            if (d.getId() == doorID) {
                return d;
            }
        }
        return null;
    }

    // EFFECT: returns the nunber of items in the doors
    public int getSize() {
        return doors.size();
    }
}


