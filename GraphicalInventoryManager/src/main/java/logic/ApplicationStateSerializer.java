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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Formatter;

public class ApplicationStateSerializer
{
    public Inventory loadInventory(File file) throws IOException
    {
        // Get file extension from path
        String extension = file.toString().split("\\.")[1].toLowerCase();

        // Call appropriate load function
        return switch(extension)
        {
            case "txt" -> loadFromTSV(file);
            case "html" -> loadFromHTML(file);
            case "json" -> loadFromJSON(file);

            default -> throw new UnsupportedOperationException();
        };
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

    public void saveInventory(Inventory inventory, File file) throws FileNotFoundException
    {
        // Get file extension from path
        String extension = file.toString().split("\\.")[1].toLowerCase();

        // Call appropriate save function
        switch(extension)
        {
            case "txt" -> saveToTSV(inventory, file);
            case "html" -> saveToHTML(inventory, file);
            case "json" -> saveToJSON(inventory, file);

            default -> throw new UnsupportedOperationException();
        }
    }

    public void saveToTSV(Inventory inventory, File file)
    {
        // Attempt to create new file

        // Write TSV header "Serial Number\tName\tValue"

        // Write serial, name, and value of each item in inventory as tab separated line
    }

    public void saveToHTML(Inventory inventory, File file) throws FileNotFoundException
    {
        final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        String tableRowFormat = """
                    <tr>
                       <td>%s</td>
                       <td>%s</td>
                       <td>%s</td>
                    </tr>
                """;

        // Attempt to create new file
        try(Formatter output = new Formatter(file))
        {
            // Write HTML header
            output.format("""
                    <!DOCTYPE html>
                    <html lang="en">

                    <style>
                         table, th, td
                         {
                              border:1px solid black;
                         }
                    </style>

                    <head>
                         <meta charset="utf-8">
                         <meta name = "author" content = "Ethan Thomas">
                         <title>Inventory</title>
                    </head>
                    """);

            // Begin HTML document body
            output.format("%s%n", "<body>");

            // Write table header
            output.format("%s%n", """
                    <table style="width:50%">
                        <tr>
                             <th>Serial Number</th>
                             <th>Name</th>
                             <th>Value</th>
                        </tr>
                          """);
            // Write inventory items
            for(InventoryItem item : inventory.inventoryItemsProperty().get())
            {
                output.format(tableRowFormat, item.getName(), item.getSerial(), currencyFormat.format(item.getValue()));
            }

            // End HTML table body
            output.format("%s%n", "</table>");

            // End HTML document body
            output.format("%s%n", "</body>");

            // End HTML document
            output.format("%s%n", "</html>");
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Unable to create new file at " + file.getAbsolutePath());
            throw new FileNotFoundException();
        }
    }

    public void saveToJSON(Inventory inventory, File file)
    {
        // Attempt to create new file

        // Use GSON to convert inventory to JSON

        // Write JSON to file (if necessary)
    }
}
