/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.scene.control.Alert;

public class InventoryValidator
{
    public boolean isValidItemName(String name)
    {
        // Check if is between 2 and 256 characters long.
        if((name.length() >= 2) && (name.length() <= 256))
        {
            return true;
        }

        displayErrorDialog("Invalid name entered",
                "Name must be between 2 and 256 characters long.");

        return false;
    }

    // Serial validation split into 2 functions to fulfil requirement for separate error messages
    // Unique serial check handled by Inventory object
    private boolean isValidSerialFormat(String serial)
    {
        return serial != null && serial.matches("[a-zA-Z](-[a-zA-Z0-9]{3}){3}");
    }

    public boolean isValidSerial(Inventory inventory, String serial, String oldSerial)
    {
        if(isValidSerialFormat(serial))
        {
            if(!inventory.containsSerial(serial.toUpperCase()) || serial.equals(oldSerial))
            {
                return true;
            }
            else
            {
                displayErrorDialog("Duplicate serial number entered",
                        "Serial numbers must be unique.");
                return false;
            }
        }
        else
        {
            displayErrorDialog("Invalid serial number entered",
                    "Serial number must be in the format A-XXX-XXX-XXX" +
                            " where A is any letter and X can be a letter or a digit.");
            return false;
        }
    }

    // Might be unnecessary if handled by method in TextField/TableView
    // If you do choose to use one of those methods,
    // don't forget to make them display error messages if the input is invalid.
    public boolean isValidMonetaryValue(String monetaryValue)
    {
        // Check if monetaryValue is a positive decimal number, possibly with digits to the right of the decimal point.
        if((monetaryValue != null)
                && (monetaryValue.matches("\\d+\\.?\\d?\\d?"))
                && (Double.parseDouble(monetaryValue) > 0))
        {
            return true;
        }

        displayErrorDialog("Invalid value entered",
                "Value must be decimal number greater than $0.00");

        return false;
    }

    public boolean validateAllInputs(Inventory inventory, String name, String serial, String value)
    {
        // Return false if any validations failed, otherwise return true

        return isValidItemName(name) && isValidSerial(inventory, serial, "") && isValidMonetaryValue(value);
    }

    private void displayErrorDialog(String title, String message)
    {
        // Create new Alert
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set title
        alert.setTitle(title);

        // Set content
        alert.setContentText(message);

        // Show Alert
        alert.show();
    }
}
