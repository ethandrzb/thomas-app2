/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationStateSerializerTest
{
    private final Path currentPath = Paths.get(System.getProperty("user.dir"));
    Inventory inventory;

    @BeforeEach
    void init()
    {
        inventory = new Inventory();

        // Add dummy data
        inventory.addItem("item 1", "A-XXX-XXX-XXX", 1200.123);
        inventory.addItem("item 2", "A-XXX-XXX-XXW", 654.45);
        inventory.addItem("item 3", "A-XXX-XXX-XXV", 9.09);
    }

    // Test that saveInventory correctly delegates export duties
    // to its helper methods and that those methods return without any errors
    // This test also generates the test data for testing loadInventory and its helper methods
    @ParameterizedTest
    @CsvSource(textBlock = """
            test.txt,
            test.html,
            test.json,
            """)
    @Order(1)
    void saveInventory(String fileName)
    {
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        try
        {
            serializer.saveInventory(inventory, new File(getPathFromFileName(fileName).toString()));
        }
        catch(IOException e)
        {
            Assertions.fail();
        }
    }

    // Verify contents of exported TSV
    @Test
    @Order(2)
    void saveToTSV()
    {
        String expected = """
                Serial Number	Name	Value
                A-XXX-XXX-XXX	item 1	$1,200.12
                A-XXX-XXX-XXW	item 2	$654.45
                A-XXX-XXX-XXV	item 3	$9.09
                """;

        allLinesEqual(expected, "test.txt");
    }

    // Verify contents of exported HTML
    @Test
    @Order(3)
    void saveToHTML()
    {
        String expected = """
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
                <body>
                <table style="width:50%">
                    <tr>
                         <th>Serial Number</th>
                         <th>Name</th>
                         <th>Value</th>
                    </tr>
                                
                    <tr>
                       <td>item 1</td>
                       <td>A-XXX-XXX-XXX</td>
                       <td>$1,200.12</td>
                    </tr>
                    <tr>
                       <td>item 2</td>
                       <td>A-XXX-XXX-XXW</td>
                       <td>$654.45</td>
                    </tr>
                    <tr>
                       <td>item 3</td>
                       <td>A-XXX-XXX-XXV</td>
                       <td>$9.09</td>
                    </tr>
                </table>
                </body>
                </html>
                """;

        allLinesEqual(expected, "test.html");
    }

    // Verify contents of exported JSON
    @Test
    @Order(4)
    void saveToJSON()
    {
        // This quote block cannot be wrapped to a new line without changing the expected data
        String expected = """
                {"inventoryItems":[{"name":"item 1","serial":"A-XXX-XXX-XXX","value":1200.12},{"name":"item 2","serial":"A-XXX-XXX-XXW","value":654.45},{"name":"item 3","serial":"A-XXX-XXX-XXV","value":9.09}]}""";
        allLinesEqual(expected, "test.json");
    }

    private void allLinesEqual(String expected, String fileName)
    {
        String[] expectedArray = expected.split("\n");

        // Attempt to read generated file and verify contents
        try(Scanner fromFile = new Scanner(new File(getPathFromFileName(fileName).toString())))
        {
            for(String line : expectedArray)
            {
                assertEquals(line, fromFile.nextLine());
            }
        }
        catch(FileNotFoundException e)
        {
            Assertions.fail();
        }
    }

    // Verify that all exported inventories can be imported correctly
    @ParameterizedTest
    @CsvSource(textBlock = """
            test.txt,
            test.html,
            test.json,
            """)
    @Order(5)
    void loadInventory(String fileName)
    {
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        try
        {
            Inventory actual = serializer.loadInventory(new File(getPathFromFileName(fileName).toString()));

            // Check length
            assertEquals(3, actual.inventoryItemsProperty().size());

            // Verify all items
            for(int i = 0; i < actual.inventoryItemsProperty().size(); i++)
            {
                // Verify name
                assertEquals(inventory.inventoryItemsProperty().get(i).getName(),
                        actual.inventoryItemsProperty().get(i).getName());

                // Verify serial
                assertEquals(inventory.inventoryItemsProperty().get(i).getSerial(),
                        actual.inventoryItemsProperty().get(i).getSerial());

                // Verify value
                assertEquals(inventory.inventoryItemsProperty().get(i).getValue(),
                        actual.inventoryItemsProperty().get(i).getValue(), Math.pow(10, -4));
            }
        }
        catch(IOException e)
        {
            Assertions.fail();
        }
    }

    private Path getPathFromFileName(String fileName)
    {
        return Paths.get(currentPath.toString(), "src", "test", "resources", "logic", fileName);
    }
}