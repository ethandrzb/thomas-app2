/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty serial = new SimpleStringProperty();
    private final SimpleDoubleProperty value = new SimpleDoubleProperty();

    public InventoryItem(String name, String serial, double value)
    {
        // Assign instance variables
        this.name.set(name);
        this.serial.set(serial.toUpperCase());
        this.value.set(value);
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getName()
    {
        return name.get();
    }

    public void setSerial(String serial)
    {
        this.serial.set(serial);
    }

    public String getSerial()
    {
        return serial.get();
    }

    public void setValue(double value)
    {
        this.value.set(value);
    }

    public double getValue()
    {
        return value.get();
    }

    public String toString()
    {
        return "Name: " + name.get() + '\n'
                +  "Serial: " + serial.get() + '\n'
                + "Value: " + value.get() + '\n';
    }
}
