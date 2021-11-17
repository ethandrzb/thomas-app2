/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.util.HashSet;

public class InventoryValidator
{
    public boolean isValidItemName(String name)
    {
        // Check if name is empty
        if(!name.isEmpty())
        {
            // If not, check if name is between 2 and 256 characters long.
            return (name.length() >= 2) && (name.length() <= 256);
        }

        return false;
    }

    public boolean isValidSerial(Inventory inventory, String serial)
    {
        // Get set of all serials in use
        HashSet<String> serialsInUse = (HashSet<String>) inventory.getSerialsInUse();

        // Check if serial matches the format A-XXX-XXX-XXX (A must be a letter, X can be a letter or a digit)
        // TODO: Debug this regex check
        if(serial != null && serial.matches("[a-zA-Z](-[a-zA-Z0-9]{3}){3}"))
        {
            return serialsInUse.contains(serial);
        }

        return false;
    }

    // Might be unnecessary if handled by method in TextField/TableView
    // If you do choose to use one of those methods,
    // don't forget to make them display error messages if the input is invalid.
    public boolean isValidMonetaryValue(String monetaryValue)
    {
        // Check if monetaryValue is a positive decimal number, possibly with digits to the right of the decimal point.

        if((monetaryValue != null) && (monetaryValue.matches("\\d+\\.?\\d?\\d?")))
        {
            return Double.parseDouble(monetaryValue) > 0;
        }

        return false;
    }

    public boolean validateAllInputs(String name, String serial, String value)
    {
        // Validate name
        // If invalid, display error message

        // Validate serial
        // If invalid, display error message

        // Validate value
        // If invalid, display error message

        // Return false if any validations failed, otherwise return true

        return false;
    }

    private boolean validateInventory(Inventory inventory)
    {
        // Validate all item names in object
            // If any item names are invalid, display an error message
            // indicating the existence of an invalid name and include the first invalid name

        // Validate all item serials
            // If any item serials are invalid, display an error message
            // indicating the existence of an invalid serial and include the first invalid serial

        // Validate all item values
            // If any item values are invalid, display an error message
            // indicating the existence of an invalid name and include the first invalid value

        return false;
    }
}
