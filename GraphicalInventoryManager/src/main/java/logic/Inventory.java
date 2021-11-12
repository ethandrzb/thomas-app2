/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Inventory
{
    private ArrayList<InventoryItem> inventoryItemData;
    private ObservableList<InventoryItem> inventoryItems;

    public Inventory()
    {
        // Initialize ArrayLists
    }

    public Inventory(List<InventoryItem> items)
    {
        // Add all items in items to inventoryItems
    }

    public void addInventoryItem(String name, String serial, double value)
    {
        // Create new inventory item and add it to the inventory
    }

    public void removeItem(InventoryItem item)
    {
        // Remove item from inventory
    }

    public Set<String> getSerialsInUse()
    {
        // Generate a quickly searchable set of the serial numbers of each InventoryItem

        return Collections.emptySet();
    }

    public void removeMultipleItems(List<InventoryItem> items)
    {
        // Remove all items in items from inventoryItems
    }

    public String toString()
    {
        // Return the collective string representation of all items in the inventory

        return "";
    }
}
