/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.*;

public class Inventory
{
    private final SimpleListProperty<InventoryItem> inventoryItems;

    public Inventory()
    {
        inventoryItems = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    }

    public void addItem(String name, String serial, double value)
    {
        // Create new inventory item and add it to the inventory
        inventoryItems.add(new InventoryItem(name, serial, value));
    }

    public SimpleListProperty<InventoryItem> inventoryItemsProperty()
    {
        return inventoryItems;
    }

    public boolean containsSerial(String serial)
    {
        HashSet<String> serialsInUse = new HashSet<>();

        // Generate a quickly searchable set of the serial numbers of each InventoryItem
        for(InventoryItem item : inventoryItems)
        {
            serialsInUse.add(item.getSerial());
        }

        return serialsInUse.contains(serial);
    }

    public void removeItems(List<InventoryItem> items)
    {
        // Remove all items in items from inventoryItems
        inventoryItems.removeAll(items);
    }

    public void clear()
    {
        // Clear inventory
        inventoryItems.clear();
    }

    public String toString()
    {
        StringBuilder output = new StringBuilder();

        // Return the collective string representation of all items in the inventory
        for(InventoryItem item : inventoryItems)
        {
            output.append(item.toString());
            output.append('\n');
        }

        return output.toString();
    }
}
