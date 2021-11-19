/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import logic.Inventory;
import logic.InventoryItem;
import logic.InventoryValidator;

import java.text.NumberFormat;

public class InventoryManagementApplicationController
{
    private Inventory inventory;

    @FXML
    private TableView<InventoryItem> inventoryTableView;
    @FXML
    private TableColumn<InventoryItem, String> nameTableViewColumn;
    @FXML
    private TableColumn<InventoryItem, String> serialTableViewColumn;
    @FXML
    private TableColumn<InventoryItem, Double> valueTableViewColumn;

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

    // Syncs an InventoryItem's name field to the corresponding TableView cell
    public void changeNameCellEvent(TableColumn.CellEditEvent<InventoryItem, String> modifiedCell)
    {
        InventoryValidator validator = new InventoryValidator();
        InventoryItem selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();

        if(validator.isValidItemName(modifiedCell.getNewValue()))
        {
            selectedItem.setName(modifiedCell.getNewValue());
        }
        else
        {
            selectedItem.setName(modifiedCell.getOldValue());
            inventoryTableView.refresh();
        }

        System.out.println(selectedItem);
    }

    // Syncs an InventoryItem's serial field to the corresponding TableView cell
    public void changeSerialCellEvent(TableColumn.CellEditEvent<InventoryItem, String> modifiedCell)
    {
        InventoryValidator validator = new InventoryValidator();
        InventoryItem selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();

        String newValue = modifiedCell.getNewValue();

        if(validator.isValidSerial(inventory, newValue, modifiedCell.getOldValue()))
        {
            selectedItem.setSerial(newValue);
        }
        else
        {
            selectedItem.setSerial(modifiedCell.getOldValue());
            inventoryTableView.refresh();
        }

        System.out.println(selectedItem);
    }

    // Syncs an InventoryItem's value field to the corresponding TableView cell
    public void changeValueCellEvent(TableColumn.CellEditEvent<InventoryItem, Double> modifiedCell)
    {
        InventoryValidator validator = new InventoryValidator();
        InventoryItem selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();

        if(validator.isValidMonetaryValue(modifiedCell.getNewValue().toString()))
        {
            selectedItem.setValue(Double.parseDouble(modifiedCell.getNewValue().toString()));
        }
        else
        {
            selectedItem.setValue(Double.parseDouble(modifiedCell.getOldValue().toString()));
            inventoryTableView.refresh();
        }

        System.out.println(selectedItem);
    }

    @FXML
    public void addItemButtonPressed(ActionEvent event)
    {
        InventoryValidator validator = new InventoryValidator();

        String name = itemNameTextField.getText();
        String serial = itemSerialTextField.getText();
        String value = itemValueTextField.getText();

        // If all inputs are valid, create a new inventory item
        if(validator.validateAllInputs(inventory, name, serial, value))
        {
            inventory.addItem(name, serial, Double.parseDouble(value));
            clearInputTextFields();
            inventoryTableView.refresh();
        }
    }

    private void clearInputTextFields()
    {
        itemNameTextField.clear();
        itemSerialTextField.clear();
        itemValueTextField.clear();
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
        inventory.clear();
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



    @FXML
    public void initialize()
    {
        // Init blank inventory
        inventory = new Inventory();

        inventory.inventoryItemsProperty().addListener((ListChangeListener<InventoryItem>) c ->
        {
            inventoryTableView.getItems().clear();
            inventoryTableView.getItems().addAll(inventory.inventoryItemsProperty());
        });

        inventory.addItem("item 1", "A-XXX-XXX-XXX", 1200.123);
        inventory.addItem("item 2", "A-XXX-XXX-XXW", 654.45);
        inventory.addItem("item 3", "A-XXX-XXX-XXV", 9.09);

        // Init RadioMenuItemEnums

        // Select default RadioMenuItem options

        // Init toggle group change listeners

        // Init TableView
        initTableView();

        // Init TableView change listener
    }

    private void initTableView()
    {
        // Make table editable
        inventoryTableView.setEditable(true);

        // Make columns editable
        nameTableViewColumn.setEditable(true);
        serialTableViewColumn.setEditable(true);
        valueTableViewColumn.setEditable(true);

        // Bind table columns to InventoryItem fields
        nameTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
        valueTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Helper code for editing a TableView cell
        nameTableViewColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serialTableViewColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Setup StringConverter for Value column
        // Double.MIN_VALUE is safe to use to flag invalid values, because value is monetary
        // and monetary values are always greater than 0 for the purposes of this application.
        StringConverter<Double> valueColumnConverter = new StringConverter<>()
        {
            @Override
            public String toString(Double value)
            {
                if(value == Double.MIN_VALUE)
                {
                    return null;
                }

                return value.toString();
            }

            @Override
            public Double fromString(String input)
            {
                try
                {
                    return Double.parseDouble(input);
                }
                catch (IllegalArgumentException | NullPointerException e)
                {
                    return Double.MIN_VALUE;
                }
            }
        };

        nameTableViewColumn.setOnEditCommit(this::changeNameCellEvent);
        serialTableViewColumn.setOnEditCommit(this::changeSerialCellEvent);
        valueTableViewColumn.setOnEditCommit(this::changeValueCellEvent);

        // Setup Value column for editing like Name and Serial columns.
        // Format value column as currency
        valueTableViewColumn.setCellFactory(tc -> new TextFieldTableCell<>(valueColumnConverter) {

            final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

            @Override
            public void updateItem(Double price, boolean empty)
            {
                super.updateItem(price, empty);
                if (empty)
                {
                    setText(null);
                }
                else
                {
                    setText(currencyFormat.format(price));
                }
            }
        });
    }

    private void initRadioMenuItemEnums()
    {
        // Init search mode RadioMenuItem enums
    }
}
