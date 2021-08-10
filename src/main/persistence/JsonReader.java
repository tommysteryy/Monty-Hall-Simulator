package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


/*
adapted from CPSC-210-JsonSerializationDemo found here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
Methods readFile, read are heavily inspired by the original source.
 */


public class JsonReader {

    private String destination;

    public JsonReader(String fileDestination) {
        this.destination = fileDestination;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameShow read() throws IOException {
        String jsonData = readFile(destination);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameShow(jsonObject);
    }

    // EFEFCT: reads the gameshow in the jsOnObject
    private GameShow parseGameShow(JSONObject jsonObject) {
        GameShow retrievedGameShow = new GameShow();
        JSONArray doorsArray = jsonObject.getJSONArray("doors");
        for (Object door : doorsArray) {
            JSONObject doorToAdd = (JSONObject) door;
            addDoor(retrievedGameShow, doorToAdd);
        }
        return retrievedGameShow;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: gameShow
    // EFFECTS: parses each door from every JSONobject and adds it to gameShow
    private void addDoor(GameShow gameshow, JSONObject jsonObject) {
        int doorID = jsonObject.getInt("doorID");
        String prizeKeyword = jsonObject.getString("prize");
        Prize prize;
        if (prizeKeyword.equals("Goat")) {
            prize = new Goat();
        } else {
            prize = new Car("Red");
        }
        Door door = new Door(doorID, prize);
        gameshow.addDoor(door);
    }

}
