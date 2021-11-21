/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest
{
    Inventory inventory;

    @BeforeEach
    void init()
    {
        inventory = new Inventory();

        // Add dummy data
        inventory.addItem("item 1", "A-XXX-XXX-XXX", 1200.123);
        inventory.addItem("item 2", "A-XXX-XXX-XXW", 654.45);
        inventory.addItem("item 3", "A-XXX-XXX-XXV", 9.09);
    }

//    Note: inventory.inventoryItemsProperty() is implicitly tested by testing Inventory's other functions

    @Test
    void addItem()
    {
        // Check starting conditions
        assertEquals(3, inventory.inventoryItemsProperty().size());

        // Add item
        inventory.addItem("test item", "T-444-555-666", 0.01);

        // Assert that something was added to the inventory
        assertEquals(4, inventory.inventoryItemsProperty().size());

        // Assert that the item was added correctly
        assertEquals("test item", inventory.inventoryItemsProperty().get(3).getName());
        assertEquals("T-444-555-666", inventory.inventoryItemsProperty().get(3).getSerial());
        assertEquals(0.01, inventory.inventoryItemsProperty().get(3).getValue(), Math.pow(10, -4));
    }

    @Test
    void containsSerial()
    {
        // Make sure serials in use are accounted for
        assertTrue(inventory.containsSerial("A-XXX-XXX-XXX"));
        assertTrue(inventory.containsSerial("A-XXX-XXX-XXW"));
        assertTrue(inventory.containsSerial("A-XXX-XXX-XXV"));

        // Test to make sure containsSerial doesn't always return true
        // This serial is known to NOT be in the inventory
        assertFalse(inventory.containsSerial("R-333-222-111"));
    }

    @Test
    void removeItems()
    {
        ArrayList<InventoryItem> condemnedItems = new ArrayList<>();

        // Check starting conditions
        assertEquals(3, inventory.inventoryItemsProperty().size());

        // Get references to items we want to remove
        condemnedItems.add(inventory.inventoryItemsProperty().get(0));
        condemnedItems.add(inventory.inventoryItemsProperty().get(2));

        // Remove items
        inventory.removeItems(condemnedItems);

        // Assert that 2 items were removed
        assertEquals(1, inventory.inventoryItemsProperty().size());

        // Assert that the remaining item hasn't been modified
        assertEquals("item 2", inventory.inventoryItemsProperty().get(0).getName());
        assertEquals("A-XXX-XXX-XXW", inventory.inventoryItemsProperty().get(0).getSerial());
        assertEquals(654.45, inventory.inventoryItemsProperty().get(0).getValue());
    }

    @Test
    void clear()
    {
        // Check starting conditions
        assertEquals(3, inventory.inventoryItemsProperty().size());

        // Clear inventory
        inventory.clear();

        // Make sure inventory was cleared
        assertTrue(inventory.inventoryItemsProperty().isEmpty());
    }

    @Test
    void testToString()
    {
        String[] expected = """
                Name: item 1
                Serial: A-XXX-XXX-XXX
                Value: 1200.12
                                
                Name: item 2
                Serial: A-XXX-XXX-XXW
                Value: 654.45
                                
                Name: item 3
                Serial: A-XXX-XXX-XXV
                Value: 9.09
                """.split("\n");

        String[] actual = inventory.toString().split("\n");

        // Line-by-line comparison of expected and actual strings
        for(int i = 0; i < expected.length; i++)
        {
            assertEquals(expected[i], actual[i]);
        }
    }
}