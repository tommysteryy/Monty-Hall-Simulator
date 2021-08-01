package persistence;

import model.GameShow;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests are heavily inspired by the CPSC 210 project found here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {

    @Test
    public void testReaderEmptyDoors() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyDoors.json");
        GameShow gameshow = new GameShow();
        try {
            gameshow = jsonReader.read();
        } catch (IOException e) {
            fail("Should not have error, failed");
        }
        assertEquals(0, gameshow.getSize());

    }

    @Test
    public void testReaderFourDoors() {
        JsonReader jsonReader = new JsonReader("./data/testReaderFourDoors.json");
        GameShow gameShow = new GameShow();
        try {
            gameShow = jsonReader.read();
        } catch (IOException e) {
            fail("Should not have error, failed");
        }
        assertEquals(4, gameShow.getSize());
        assertEquals(4, gameShow.getLargestDoorID());
    }

    @Test
    public void testReaderBrokenDestination() {
        JsonReader jsonReader = new JsonReader("./data/testBROKENFileDoesntExist.json");
        GameShow gameShow = new GameShow();
        try {
            gameShow = jsonReader.read();
            fail("Shouldn't reach this point cause of exception");
        } catch (IOException e) {
            // do nothing, expected
        }
    }

}
