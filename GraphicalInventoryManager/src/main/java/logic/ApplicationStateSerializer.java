/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.io.File;
import java.nio.file.Path;

// https://stackoverflow.com/questions/54400414/how-to-convert-a-java-object-into-an-html-table/54400838
// http://scrumbucket.org/converting-a-pojo-into-html/
// http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ReflectionToStringBuilder.html
// https://github.com/whimtrip/jwht-htmltopojo

public class ApplicationStateSerializer
{
    // Add enum for each supported file extension

    public Inventory loadInventory(Path path)
    {
        // Get file extension from path and call appropriate load function
        // *.txt ==> loadFromTSV
        // *.html ==> loadFromHTML
        // *.json ==> loadFromJSON

        // Validate loaded inventory
            // If any validations fail, throw an IllegalArgumentException

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

    public Inventory loadFromHTML(File file)
    {
        // Attempt to open file

        // Attempt to parse HTML with htmltopojo library

        return null;
    }

    public Inventory loadFromJSON(File file)
    {
        // Attempt to open file

        // Attempt to parse JSON with GSON

        return null;
    }

    public void saveInventory(Inventory inventory, File file)
    {
        // Get file extension from path and call appropriate save function
        // *.txt ==> saveToTSV
        // *.html ==> saveToHTML
        // *.json ==> saveToJSON
    }

    public void saveToTSV(Inventory inventory, File file)
    {
        // Attempt to create new file

        // Write TSV header "Serial Number\tName\tValue"

        // Write serial, name, and value of each item in inventory as tab separated line
    }

    public void saveToHTML(Inventory inventory, File file)
    {
        // Attempt to create new file

        // Use library function to convert inventory to HTML table

        // Add header information
            // Author
            // Title

        // Write HTML to file (if necessary)
    }

    public void saveToJSON(Inventory inventory, File file)
    {
        // Attempt to create new file

        // Use GSON to convert inventory to JSON

        // Write JSON to file (if necessary)
    }
}
