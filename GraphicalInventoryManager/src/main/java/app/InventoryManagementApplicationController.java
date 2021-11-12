/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Inventory;
import logic.InventoryItem;

public class InventoryManagementApplicationController
{
    private Inventory inventory;

    @FXML
    private TableView<?> inventoryTableView;

    @FXML
    public TableColumn<InventoryItem, String> nameTableViewColumn;

    @FXML
    public TableColumn<InventoryItem, String> serialTableViewColumn;

    @FXML
    public TableColumn<InventoryItem, String> valueTableViewColumn;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private TextField itemSerialTextField;

    @FXML
    private TextField itemValueTextField;

    @FXML
    private ToggleGroup searchByToggleGroup;

    @FXML
    private TextField searchTextField;

    @FXML
    private ToggleGroup sortByToggleGroup;

    @FXML
    public void addItemButtonPressed(ActionEvent event)
    {
        // Validate all input TextFields

        // If all inputs are valid, create a new inventory item
    }

    @FXML
    public void searchInventory(ActionEvent event)
    {
        // Generate new predicate to match items in the search criteria (search string and mode)
    }

    @FXML
    public void clearInventoryMenuItemSelected(ActionEvent event)
    {
        // Clear inventory
    }

    @FXML
    public void saveMenuItemSelected(ActionEvent event)
    {
        // Display FileChooser to get path to destination file

        // Export inventory in selected format
    }

    @FXML
    public void loadMenuItemSelected(ActionEvent event)
    {
        // Display FileChooser to get path to source inventory

        // Import inventory

        // Clear current inventory
    }

    @FXML
    public void removeSelectedItemsMenuItemSelected(ActionEvent event)
    {
        // Get list of currently selected items in TableView

        // Remove them from the inventory
    }

    private void displayErrorDialog(String title, String message)
    {
        // Create new AlertBox

        // Set title

        // Set content

        // Show AlertBox
    }

    @FXML
    public void initialize()
    {
        // Init blank inventory
        inventory = new Inventory();

        // Init RadioMenuItemEnums

        // Select default RadioMenuItem options

        // Init toggle group change listeners

        // Init TableView

        // Init TableView change listener
    }

    private void initTableView()
    {
        inventoryTableView = new TableView<>();

        // Bind table columns to InventoryItem fields
        nameTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
        valueTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("valueInDollars"));

        // Why doesn't this work?
//        inventoryTableView.getItems().add(new InventoryItem("test item", "A-XXX-XXX-XXX", 12.00));
    }

    private void initRadioMenuItemEnums()
    {
        // Init sort mode RadioMenuItem enums (unnecessary if implemented via TableView)

        // Init search mode RadioMenuItem enums
    }
}
