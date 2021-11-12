/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.io.File;
import java.nio.file.Path;

public class ApplicationStateSerializer
{
    public Inventory loadInventory(Path path)
    {
        // Get file extension from path and call appropriate load function
        // *.txt ==> loadFromTSV
        // *.html ==> loadFromHTML
        // *.json ==> loadFromJSON

        return null;
    }

    public Inventory loadFromTSV(File file)
    {
        // Make sure valid header is present
        // If not, throw IllegalArgumentException

        // While there are still lines to be read:
            // Read line and split by '\t'
            // Make sure array returned by split operation is 3 elements long
            // If not, throw IllegalArgumentException

            // Check if first element is valid serial
            // If not, display error message indicating that this could be caused by
            // a formatting issue or two items sharing the same serial number

            // Check if second element is valid name
            // If not, display error message

            // Remove dollar sign from start of third element
            // Check if third element is valid value
            // If not, display error message

            // Create new InventoryItem with these values and add it to the buffer

        // return new Inventory object
        return null;
    }
}
