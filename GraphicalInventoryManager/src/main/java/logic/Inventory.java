/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Inventory
{
    private final ObservableList<InventoryItem> inventoryItems;

    public Inventory()
    {
        inventoryItems = FXCollections.observableList(new ArrayList<>());
    }

    public Inventory(List<InventoryItem> items)
    {
        this();

        // Add all items in items to inventoryItems
        inventoryItems.addAll(items);
    }

    public void addItem(String name, String serial, double value)
    {
        // Create new inventory item and add it to the inventory
        inventoryItems.add(new InventoryItem(name, serial, value));
    }

    public List<InventoryItem> getItems()
    {
        return new ArrayList<>(inventoryItems);
    }

    public void removeItem(InventoryItem item)
    {
        // Remove item from inventory
        inventoryItems.remove(item);
    }

    public Set<String> getSerialsInUse()
    {
        HashSet<String> serialsInUse = new HashSet<>();

        // Generate a quickly searchable set of the serial numbers of each InventoryItem
        for(InventoryItem item : inventoryItems)
        {
            serialsInUse.add(item.getSerial());
        }

        return serialsInUse;
    }

    public void removeMultipleItems(List<InventoryItem> items)
    {
        // Remove all items in items from inventoryItems
        inventoryItems.removeAll(items);
    }

    public void clearInventory()
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
