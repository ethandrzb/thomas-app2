/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem
{
    SimpleStringProperty name;
    SimpleStringProperty serial;
    SimpleDoubleProperty value;

    public InventoryItem(String name, String serial, double value)
    {
        // Convert serial to uppercase

        // Assign instance variables
    }

    // Getters and setters for name, serial, and value

    public void setName(String name)
    {
        // Update name property with new value
    }

    public SimpleStringProperty getName()
    {
        //return name
        return null;
    }

    public void setSerial(String serial)
    {
        // Update serial property with new value
    }

    public SimpleStringProperty getSerial()
    {
        //return serial
        return null;
    }

    public void setValue(double value)
    {
        // Update value property with new value
    }

    public SimpleDoubleProperty getValue()
    {
        //return value
        return null;
    }
}
