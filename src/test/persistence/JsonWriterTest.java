package persistence;

import model.Car;
import model.Door;
import model.GameShow;
import model.Goat;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests are heavily inspired by the CPSC 210 project found here:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest {

    @Test
    public void testFileNameBrokenWrite() {
        JsonWriter jsonWriter = new JsonWriter("./data/this/broken8,/8.json");
        GameShow gameShow = new GameShow();
        try {
            jsonWriter.open();
            jsonWriter.write(gameShow);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // do nothing, expected;
        }
    }

    @Test
    public void testWriteEmptyDoors() {
        JsonWriter jsonWriter = new JsonWriter("./data/testWriterEmptyDoors.json");
        GameShow gameShow = new GameShow();
        try {
            jsonWriter.open();
            jsonWriter.write(gameShow);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            fail("not supposed to fail, the file name was valid");
        }
        JsonReader jsonReader = new JsonReader("./data/testWriterEmptyDoors.json");
        GameShow retrievedGameShow = new GameShow();
        try {
            retrievedGameShow = jsonReader.read();
        } catch (IOException e) {
            fail("not supposed to fail, the file name was valid");
        }
        assertEquals(0, retrievedGameShow.getSize());
    }


    @Test
    public void testWriteFourDoors() {
        JsonWriter jsonWriter = new JsonWriter("./data/testWriterFourDoors.json");
        GameShow gameShowToSave = new GameShow();
        gameShowToSave.addDoor(new Door(1, new Goat()));
        gameShowToSave.addDoor(new Door(2, new Goat()));
        gameShowToSave.addDoor(new Door(3, new Car("red")));
        gameShowToSave.addDoor(new Door(4, new Goat()));

        try {
            jsonWriter.open();
            jsonWriter.write(gameShowToSave);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            fail("Should not have failed, error");
        }

        JsonReader jsonReader = new JsonReader("./data/testWriterFourDoors.json");
        GameShow gameShow = new GameShow();
        try {
            gameShow = jsonReader.read();
        } catch (IOException e) {
            fail("Should not have error, failed");
        }
        assertEquals(4, gameShow.getSize());
        assertEquals(4, gameShow.getLargestDoorID());
        assertTrue(gameShow.containsDoor(3));
        assertFalse(gameShow.containsDoor(5));
    }
}
