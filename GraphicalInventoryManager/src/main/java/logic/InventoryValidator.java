/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

public class InventoryValidator
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
