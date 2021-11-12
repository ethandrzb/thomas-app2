/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem
{
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty serial = new SimpleStringProperty();
    SimpleDoubleProperty value = new SimpleDoubleProperty();

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

    public String getValueInDollars()
    {
        return String.format("$%.2f", value.get());
    }

    public String toString()
    {
        return "Name: " + name + '\n'
                +  "Serial: " + serial + '\n'
                + "Value: " + value + '\n';
    }
}
