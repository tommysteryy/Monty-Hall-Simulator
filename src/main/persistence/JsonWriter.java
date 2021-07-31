package persistence;

import model.GameShow;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
adapted from CPSC-210-JsonSerializationDemo found here: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class JsonWriter {

    private static final int indentation = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECT: Constructs a JsonWriter that will write to destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: THIS
    // EFFECTS: opens the PrintWriter to start writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes the PrintWriter
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: saves the converted json string into the file
    public void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECT: writes the json from the gameshow to a file
    public void write(GameShow gameShow) {
        JSONObject json = gameShow.toJson();
        saveToFile(json.toString(indentation));
    }
}
