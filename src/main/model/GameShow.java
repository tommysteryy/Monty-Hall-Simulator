package model;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static ui.Simulation.fileDestination;

public class GameShow {

    private List<Door> doors;
    private Host host;

    private final Car carRed = new Car("red");
    private final Goat goat = new Goat();
    private final Door door1C = new Door(1, carRed);
    private final Door door2G = new Door(2, goat);
    private final Door door3G = new Door(3, goat);

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

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

    // MODIFIES: this
    // EFFECT: randomizes the order of the doors
    public void randomizeDoors() {
        Collections.shuffle(this.doors);
    }

    // EFFECT: returns a readable version of the current state of the GameShow
    public String presentDoors() {
        String out = new String();

        for (Door d : doors) {
            out += "\t Door " + d.getId();
            if (d.isSelected()) {
                out += " (selected):";
            } else {
                out += ":";
            }

            if (d.isOpen()) {
                out += " Open";
                out += ", the prize is " + d.getPrize().reveal();
            } else {
                out += " Closed";
            }
            out += "\n";
        }
        return out;
    }

    // REQUIRES: newly set up game (all non-selected), non-emty doors
    // EFFECT: runs 1 simulation of a game of the monty hall problem WHERE YOU NEVER SWITCH
    //         returns true if won, return false if didn't
    public boolean runSimulationOnceDontSwitch() {
        this.unselectAllDoors();
        Collections.shuffle(this.doors);
        Door firstPickDoor = this.doors.get(0);
        this.selectDoor(firstPickDoor.getId());
        return this.currentSelectedDoor().getPrize() instanceof Car;
    }

    // MODIFIES: THIS
    // EFFECT: runs a single simulation of this and returns true if they picked the car, false otherwise.
    public boolean runSimulationOnceSwitch() {
        this.unselectAllDoors();
        this.randomizeDoors();
        Door firstPickDoor = this.doors.get(0);
        this.selectDoor(firstPickDoor.getId());

        Door revealDoor = this.nonSelectedGoatDoor();

        List<Door> listOfDoorsDontWant = new ArrayList<>();
        listOfDoorsDontWant.add(firstPickDoor);
        listOfDoorsDontWant.add(revealDoor);

        Door doorToSwitchTo = this.randomDoorThatIsntInTheList(listOfDoorsDontWant);
        this.unselectDoor(firstPickDoor.getId());
        this.selectDoor(doorToSwitchTo.getId());

        return this.currentSelectedDoor().getPrize() instanceof Car;
    }


    // MODIFIES: this
    // EFFECT: sets up a standard gameshow for a normal round of simulations or games
    public void setupStandardGameShow() {
        this.clearDoors();
        this.addDoor(door1C);
        this.addDoor(door2G);
        this.addDoor(door3G);
        this.randomizeDoors();
        this.closeAllDoors();
        this.unselectAllDoors();
    }

    // MODIFIES: this
    // EFFECT: sets up the gameshow for an ARBITRARY MADE round of simulations or games.
    public void setupArbitraryGameShow(List<Door> doorsToAddToGame) {
        this.clearDoors();
        for (Door d: doorsToAddToGame) {
            this.addDoor(d);
        }
        this.randomizeDoors();
        this.closeAllDoors();
        this.unselectAllDoors();
    }

    /// HELPER FUNCTIONS (+ GETTERS AND SETTERS)

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

    // EFFECT: returns the list with all the doors in it
    public List<Door> getDoors() {
        return doors;
    }

    // EFFECT: returns a list with all the doorIDs
    public List<Integer> getDoorIDs() {
        List<Integer> result = new ArrayList<>();
        for (Door d: doors) {
            result.add(d.getId());
        }
        return result;
    }

    // MODIFIES: this
    // EFFECT: removes all doors from this.doors, good way to reset between methods
    public void clearDoors() {
        List<Door> newDoorList = new ArrayList<>();
        this.doors = newDoorList;
    }

    // EFFECT: returns true if all doors are closed
    public boolean allDoorsClosed() {
        for (Door d: doors) {
            if (d.isOpen()) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: AT LEAST one non-selected door that has a goat in doors
    // EFFECT: produces A SINGLE RANDOM DOOR THAT IS A NON-SELECTED, GOAT DOOR
    public Door nonSelectedGoatDoor() {
        ArrayList<Door> listOfGoatDoors = new ArrayList<>();

        for (Door d: doors) {
            if (!d.isSelected() && d.prizeIsGoat()) {
                listOfGoatDoors.add(d);
            }
        }
        Collections.shuffle(listOfGoatDoors);
        return listOfGoatDoors.get(0);
    }

    // REQUIRES: non-empty list
    // EFFECT: returns the third door in doors that doesn't have an id of the two provided
    //         returns null if there is not a third door that will work
    public Door randomDoorThatIsntInTheList(List<Door> doorsDontWant) {
        List<Door> availableDoorsToChoose = new ArrayList<>();
        for (Door d: doors) {
            if (!doorsDontWant.contains(d)) {
                availableDoorsToChoose.add(d);
            }
        }
        Collections.shuffle(availableDoorsToChoose);
        return availableDoorsToChoose.get(0);
    }

    // EFFECT: closes all doors in gameshow
    public void closeAllDoors() {
        for (Door d: doors) {
            d.close();
        }
    }

    // EFFECT: opens all doors in gameshow
    public void openAllDoors() {
        for (Door d: doors) {
            d.open();
        }
    }

    // EFFECT: unselects all doors
    public void unselectAllDoors() {
        for (Door d: doors) {
            d.unselect();
        }
    }

    public Integer getLargestDoorID() {
        int id = 0;
        for (Door d: doors) {
            if (d.getId() > id) {
                id = d.getId();
            }
        }
        return id;
    }

    // EFFECT: stores the doors of the gameshow as a JsonObject
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doors", doorsToJson());
        return jsonObject;
    }

    // EFFECT:stores each door in a jsonArray
    public JSONArray doorsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Door d: doors) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECT: changes itself to the loaded gameshow
    public void loadGameShow() {
        GameShow gameShow = new GameShow();
        try {
            gameShow = jsonReader.read();
            System.out.println("Loaded saved gameshow from " + fileDestination);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + fileDestination);
        }
        this.doors = gameShow.getDoors();
    }

    // EFFECTS; saves this gameshow into the file.
    public void saveGameShow() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved your custom gameshow to " + fileDestination);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + fileDestination);
        }
    }

}


