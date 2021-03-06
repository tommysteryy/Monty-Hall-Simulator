package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameShowTest {

    private GameShow gameShow;
    private Car carBlue = new Car("blue");
    private Car carRed = new Car("red");
    private Goat goat = new Goat();

    private Door door1C = new Door(1, carRed);
    private Door door2G = new Door(2, goat);
    private Door door3G = new Door(3, goat);
    private Door door4C = new Door(4, carBlue);
    private Door door5G = new Door(5, goat);

    @BeforeEach
    public void setup() {
        gameShow = new GameShow();
    }

    @Test
    public void testBasicThreeDoorCurrentSelection() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        gameShow.selectDoor(1);

        assertEquals(door1C, gameShow.currentSelectedDoor());

        assertFalse(gameShow.unselectDoor(2));
        assertFalse(gameShow.unselectDoor(5));

        gameShow.unselectDoor(1);
        gameShow.selectDoor(2);

        assertEquals(door2G, gameShow.currentSelectedDoor());
    }

    @Test
    public void testSelectReselect() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        gameShow.selectDoor(1);
        assertEquals(door1C, gameShow.currentSelectedDoor());

        assertFalse(gameShow.selectDoor(2));
        assertEquals(door1C, gameShow.currentSelectedDoor());
    }

    @Test
    public void testSelectUnselectNotInGameShow() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        assertTrue(gameShow.selectDoor(1));

        assertFalse(gameShow.selectDoor(4));
        assertFalse(gameShow.unselectDoor(5));
    }

    @Test
    public void testOpenCloseSelectUnselect() {
        gameShow.addDoor(door3G);
        gameShow.addDoor(door4C);
        gameShow.addDoor(door1C);

        door2G.close();
        door1C.open();
        door4C.select();
        door3G.unselect();


        assertFalse(door2G.isOpen());
        assertTrue(door4C.isSelected());
        assertTrue(gameShow.find(door4C.getId()).isSelected());
        assertFalse(gameShow.find(door3G.getId()).isSelected());

    }

    @Test
    public void testOpenCloseDoorsInGameShow() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        gameShow.closeDoor(door1C.getId());
        gameShow.openDoor(door2G.getId());

        assertFalse(gameShow.closeDoor(4));
        assertFalse(gameShow.openDoor(5));

        assertFalse(door1C.isOpen());
        assertTrue(door2G.isOpen());
        assertFalse(door3G.isOpen());
    }

    @Test
    public void testCannotAddDuplicateDoors() {
        gameShow.addDoor(door3G);
        assertTrue(gameShow.addDoor(door2G));
        assertFalse(gameShow.addDoor(door3G));

        assertTrue(gameShow.addDoor(door1C));
        assertFalse(gameShow.addDoor(door1C));

        assertEquals(3, gameShow.getSize());
    }

    @Test
    public void testAddAndRemoveDoors() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        assertTrue(gameShow.removeDoor(1));
        assertFalse(gameShow.removeDoor(1));

        assertTrue(gameShow.removeDoor(2));
        assertFalse(gameShow.removeDoor(5));

        assertEquals(1, gameShow.getSize());

    }

    @Test
    public void testNullReturns() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);

        assertNull(gameShow.find(50));
        assertEquals(door2G, gameShow.find(2));
        assertNull(gameShow.currentSelectedDoor());

    }

    @Test
    public void testPrizeReveal() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door4C);

        assertEquals("a goat", gameShow.find(2).getPrize().reveal());
        assertEquals("a red car", gameShow.find(1).getPrize().reveal());
        assertEquals("a blue car", gameShow.find(4).getPrize().reveal());

    }

    @Test
    public void testRandomizeDontAffectDoors() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door4C);

        gameShow.selectDoor(1);
        gameShow.openDoor(4);
        gameShow.randomizeDoors();

        assertEquals(3, gameShow.getSize());
        assertEquals(door2G, gameShow.find(2));
        assertEquals(door1C, gameShow.currentSelectedDoor());
        assertTrue(gameShow.find(4).isOpen());
    }

    @Test
    public void testClearDoors() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door4C);

        gameShow.clearDoors();
        assertEquals(0, gameShow.getSize());
        assertNull(gameShow.find(1));
    }

    @Test
    public void testDoorPrizeChecking() {
        assertTrue(door1C.prizeIsCar());
        assertFalse(door1C.prizeIsGoat());
        assertTrue(door2G.prizeIsGoat());
        assertFalse(door2G.prizeIsCar());
    }

    @Test
    public void testAllClosed() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door4C);
        gameShow.closeAllDoors();

        assertTrue(gameShow.allDoorsClosed());
    }

    @Test
    public void testReturnNonSelectedGoatDoor() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.unselectAllDoors();

        gameShow.selectDoor(1);
        assertEquals(door2G, gameShow.nonSelectedGoatDoor());

    }

    @Test
    public void testNonSelectedDoor() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);

        List<Door> lst1 = new ArrayList<>();
        lst1.add(door1C);
        lst1.add(door2G);

        List<Door> lst2 = new ArrayList<>();
        lst2.add(door2G);
        lst2.add(door3G);

        assertEquals(door3G, gameShow.randomDoorThatIsntInTheList(lst1));
        assertEquals(door1C, gameShow.randomDoorThatIsntInTheList(lst2));

    }

    @Test
    public void testPresentCode() {
        gameShow.addDoor(door1C);
        gameShow.addDoor(door2G);
        gameShow.addDoor(door3G);
        gameShow.closeAllDoors();

        String output = "\t Door 1: Closed\n" +
                "\t Door 2: Closed\n" +
                "\t Door 3: Closed\n";

        assertEquals(output, gameShow.presentDoors());

        gameShow.selectDoor(1);

        String output2 = "\t Door 1 (selected): Closed\n" +
                "\t Door 2: Closed\n" +
                "\t Door 3: Closed\n";

    }
}
