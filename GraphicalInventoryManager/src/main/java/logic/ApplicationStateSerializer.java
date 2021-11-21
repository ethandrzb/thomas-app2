/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Scanner;

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

    public Inventory loadFromTSV(File file) throws IOException
    {
        Inventory inventory = new Inventory();
        InventoryValidator validator = new InventoryValidator();

        String[] loadedFields;
        String name;
        String serial;
        String value;

        try(Scanner fromFile = new Scanner(file))
        {
            // Make sure valid header is present
            if(!fromFile.nextLine().equals("Serial Number\tName\tValue"))
            {
                return null;
            }

            while(fromFile.hasNext())
            {
                // Read line and split by '\t'
                loadedFields = fromFile.nextLine().split("\t");

                // Make sure array returned by split operation is 3 elements long
                if(loadedFields.length != 3)
                {
                    return null;
                }

                // Extract fields from loaded fields
                name = loadedFields[1];
                serial = loadedFields[0];
                value = currencyToNumericalString(loadedFields[2]);

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
        catch (IOException e)
        {
            throw new IOException();
        }

        return inventory;
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

    public Inventory loadFromJSON(File file) throws IOException
    {
        Inventory inventory;
        Gson gson = FxGson.create();

        // Attempt to open file
        try(Reader fromFile = Files.newBufferedReader(Paths.get(file.toString())))
        {
            // Attempt to parse JSON with GSON
            inventory = gson.fromJson(fromFile, Inventory.class);
        }
        catch(IOException e)
        {
            throw new IOException();
        }

        return inventory;
    }

    public void saveInventory(Inventory inventory, File file) throws IOException
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

    public void saveToTSV(Inventory inventory, File file) throws FileNotFoundException
    {
        final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        // Value -------------------|
        // Name ----------------|   |
        // Serial number ---\/  \/  \/
        String rowFormat = "%s\t%s\t%s%n";

        // Attempt to create new file
        try(Formatter output = new Formatter(file))
        {
            // Write TSV header "Serial Number\tName\tValue"
            output.format(rowFormat, "Serial Number", "Name", "Value");

            for(InventoryItem item : inventory.inventoryItemsProperty())
            {
                // Write serial, name, and value of each item in inventory as tab separated line
                output.format(rowFormat, item.getSerial(), item.getName(), currencyFormat.format(item.getValue()));
            }
        }
        catch(FileNotFoundException e)
        {
            throw new FileNotFoundException();
        }
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
            throw new FileNotFoundException();
        }
    }

    public void saveToJSON(Inventory inventory, File file) throws FileNotFoundException
    {
        Gson gson = FxGson.create();

        // Attempt to create new file
        try(Formatter output = new Formatter(file))
        {
            // Use GSON to convert inventory to JSON and write output to file
            output.format("%s", gson.toJson(inventory));
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Unable to create new file at " + file.getAbsolutePath());
            throw new FileNotFoundException();
        }
    }
}
