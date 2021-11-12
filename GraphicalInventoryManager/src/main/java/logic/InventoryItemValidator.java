/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

public class InventoryItemValidator
{
    public boolean isValidItemName(String name)
    {
        // Check if name is empty
            // If so, return false
            // If not, check if name is between 2 and 256 characters long.
                // If so, return true
                // Else, return false

        return false;
    }

    public boolean isValidSerial(String serial)
    {
        // Get set of all serials in use

        // Check if serial matches the format A-XXX-XXX-XXX (A must be a letter, X can be a letter or a digit)
            // If so, check if serial is already in use
                // If so, return false
                // If not, return true.

        return false;
    }

    // Might be unnecessary if handled by method in TextField/TableView
    // If you do choose to use one of those methods,
    // don't forget to make them display error messages if the input is invalid.
    public boolean isValidMonetaryValue(String monetaryValue)
    {
        // Check if monetaryValue is a decimal number
        // with at least one number to the left of the decimal point and two numbers right of it.

        return false;
    }
}
