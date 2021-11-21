/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest
{
    InventoryItem item;
    String startingName = "starting name";
    String startingSerial = "A-BCD-123-XYZ";
    double startingValue = 123.45;

    @BeforeEach
    void init()
    {
        // Implicitly tests constructor
        item = new InventoryItem(startingName, startingSerial, startingValue);
    }

    @Test
    void setNameAndGetName()
    {
        // Make sure starting name is correct
        assertEquals(startingName, item.getName());

        // Change name
        item.setName("modified");

        // Make sure change was applied correctly
        assertEquals("modified", item.getName());
    }

    @Test
    void setSerialAndGetSerial()
    {
        // Make sure starting serial is correct
        assertEquals(startingSerial, item.getSerial());

        // Change serial
        item.setSerial("K-123-456-789");

        // Make sure change was applied correctly
        assertEquals("K-123-456-789", item.getSerial());
    }

    @Test
    void setValue()
    {
        // Make sure starting value is correct
        assertEquals(startingValue, item.getValue());

        // Change value
        item.setValue(654.32);

        // Make sure change was applied correctly
        assertEquals(654.32, item.getValue(), Math.pow(10, -4));
    }

    @Test
    void testToString()
    {
        String[] expected = """
                Name: starting name
                Serial: A-BCD-123-XYZ
                Value: 123.45""".split("\n");

        String[] actual = item.toString().split("\n");

        // Line-by-line comparison of expected and actual strings
        for(int i = 0; i < expected.length; i++)
        {
            assertEquals(expected[i], actual[i]);
        }
    }
}