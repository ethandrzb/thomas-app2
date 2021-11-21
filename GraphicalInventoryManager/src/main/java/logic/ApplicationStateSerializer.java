/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

// TODO: Remove these links
// https://stackoverflow.com/questions/54400414/how-to-convert-a-java-object-into-an-html-table/54400838
// http://scrumbucket.org/converting-a-pojo-into-html/
// http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/ReflectionToStringBuilder.html

public class ApplicationStateSerializer
{
    public Inventory loadInventory(File file) throws IOException
    {
        InventoryValidator validator = new InventoryValidator();

        // Get file extension from path
        String extension = file.toString().split("\\.")[1].toLowerCase();

        // Call appropriate load function
        Inventory inventory = switch(extension)
        {
            case "txt" -> loadFromTSV(file);
            case "html" -> loadFromHTML(file);
            case "json" -> loadFromJSON(file);

            default -> throw new UnsupportedOperationException();
        };

        // Validate loaded inventory
        // Return null if inventory is invalid
        return validator.validateInventory(inventory) ? inventory : null;
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

    public Inventory loadFromHTML(File file) throws IOException
    {
        Inventory inventory = new Inventory();
        InventoryValidator validator = new InventoryValidator();

        String name;
        String serial;
        String value;

        try
        {
            // Attempt to open file
            Document doc = Jsoup.parse(file, "UTF-8", "");

            // Get table rows
            Elements rows = doc.select("tr");

            for(Element row : rows)
            {
                Elements columns = row.select("td");

                if(!columns.isEmpty())
                {
                    // Load data from current table row
                    name = columns.get(0).text();
                    serial = columns.get(1).text();
                    value = currencyToNumericalString(columns.get(2).text());

                    // Validate inputs before attempting to add the item to the inventory
                    if(validator.validateAllInputs(inventory, name, serial, value))
                    {
                        inventory.addItem(name, serial, Double.parseDouble(value));
                    }
                    else
                    {
                        return null;
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Unable to open file at " + file.getAbsolutePath());
            throw new IOException();
        }

        return inventory;
    }

    private String currencyToNumericalString(String currency)
    {
        // Remove dollar sign from value, if it exists
        if(currency.startsWith("$"))
        {
            currency = currency.substring(1);
        }

        // Remove commas, if they exist
        currency = currency.replace(",", "");

        return currency;
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
