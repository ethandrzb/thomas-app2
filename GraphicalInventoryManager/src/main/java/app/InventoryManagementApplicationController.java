/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.ApplicationStateSerializer;
import logic.Inventory;
import logic.InventoryItem;
import logic.InventoryValidator;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class InventoryManagementApplicationController
{
    private enum searchByOption
    {
        NAME("By Name"), SERIAL("By Serial");

        private final String optionText;

        searchByOption(String optionText)
        {
            this.optionText = optionText;
        }
    }
    private searchByOption selectedSearchOption = searchByOption.NAME;

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
    private ComboBox<searchByOption> searchModeComboBox;

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
    }

    @FXML
    public void addItemButtonPressed()
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
    public void clearInventoryMenuItemSelected()
    {
        // Clear inventory
        inventory.clear();
    }

    @FXML
    public void saveMenuItemSelected()
    {
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Display FileChooser to get path to destination file
        File chosenFile = getFileChooser().showSaveDialog(inventoryTableView.getScene().getWindow());

        // User closed FileChooser without selecting a file
        if(chosenFile == null)
        {
            return;
        }

        // Export inventory in selected format
        try
        {
            serializer.saveInventory(inventory, chosenFile);
        }
        catch(IOException | UnsupportedOperationException e)
        {
            displayErrorDialog("Failed to export inventory to requested file",
                    "Please check the file to which you are attempting to save the inventory and try again.");
        }
    }

    @FXML
    public void loadMenuItemSelected()
    {
        Inventory loadedInventory;
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Display FileChooser to get path to source inventory
        File chosenFile = getFileChooser().showOpenDialog(inventoryTableView.getScene().getWindow());

        // User closed FileChooser without selecting a file
        if(chosenFile == null)
        {
            return;
        }

        try
        {
            // Attempt to import inventory
            loadedInventory = serializer.loadInventory(chosenFile);

            // Replace current inventory with imported inventory
            if(loadedInventory != null)
            {
                inventory = loadedInventory;
            }
            else
            {
                displayErrorDialog("Unable to load inventory",
                        "One or more validations failed while attempting " +
                                "to load an inventory from the requested file.");

                return;
            }

            // Reattach TableView sorting and filtering
            initSortableFilteredListAndSearchFunction();
        }
        catch(IOException | UnsupportedOperationException e)
        {
            displayErrorDialog("Failed to load requested file",
                    "Please check the file you are attempting to load and try again.");
        }
    }

    private FileChooser getFileChooser()
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV Files", "*.txt"),
                new FileChooser.ExtensionFilter("HTML Files", "*.html"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        return fileChooser;
    }

    @FXML
    public void removeSelectedItemsMenuItemSelected()
    {
        // Remove them from the inventory
        inventory.removeItems(inventoryTableView.getSelectionModel().getSelectedItems());
    }

    @FXML
    public void initialize()
    {
        // Init blank inventory
        inventory = new Inventory();

        // Initialize filtered list and associated listeners
        initSortableFilteredListAndSearchFunction();

        // Dummy data
        inventory.addItem("item 1", "A-XXX-XXX-XXX", 1200.123);
        inventory.addItem("item 2", "A-XXX-XXX-XXW", 654.45);
        inventory.addItem("item 3", "A-XXX-XXX-XXV", 9.09);

        // Init search mode ComboBox
        initSearchModeComboBox();

        // Init TableView
        initTableView();
    }

    private void initSortableFilteredListAndSearchFunction()
    {
        SortedList<InventoryItem> sortedFilteredList;

        // Wrap all inventory items in FilteredList for search function
        FilteredList<InventoryItem> filteredList = new FilteredList<>(inventory.inventoryItemsProperty());

        // Display all items by default (no filtering)
        filteredList.setPredicate(i -> true);

        // Add listener to search TextField to update search criteria
        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(i ->
                {
                    // Display all items when nothing is entered in search box
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }

                    // Case INSENSITIVE searching
                    return switch(selectedSearchOption)
                    {
                        case NAME -> i.getName().toUpperCase().contains(newValue.toUpperCase());
                        case SERIAL -> i.getSerial().toUpperCase().contains(newValue.toUpperCase());
                    };
                }));
        // Reinitialize TableView sort function
        // Wrapping the data in a FilteredList removed the TableView's ability to sort it
        sortedFilteredList = new SortedList<>(filteredList);
        sortedFilteredList.comparatorProperty().bind(inventoryTableView.comparatorProperty());

        // Init TableView change listener
        inventory.inventoryItemsProperty().addListener((ListChangeListener<InventoryItem>) c ->
                inventoryTableView.setItems(sortedFilteredList));

        // Force add items to table
        inventoryTableView.setItems(sortedFilteredList);
    }

    private void initSearchModeComboBox()
    {
        // Add options to ComboBox
        searchModeComboBox.getItems().add(searchByOption.NAME);
        searchModeComboBox.getItems().add(searchByOption.SERIAL);

        // Select NAME by default
        searchModeComboBox.getSelectionModel().select(searchByOption.NAME);

        // Use human-readable option names instead of enum names
        initCustomComboBoxOptionText();

        // Add change listener to ComboBox
        searchModeComboBox.setOnAction((event ->
                selectedSearchOption = searchModeComboBox.getSelectionModel().getSelectedItem()));
    }

    private void initCustomComboBoxOptionText()
    {
        // Create CellFactory for ComboBox that uses the String stored in the searchByOption enum
        // instead of the name of the enum itself
        Callback<ListView<searchByOption>, ListCell<searchByOption>> searchModeComboBoxCellFactory =
                param -> new ListCell<>()
                {
                    @Override
                    protected void updateItem(searchByOption item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (item != null && !empty) {
                            setText(item.optionText);
                        }
                    }
                };

        // Apply CellFactory to ComboBox button
        searchModeComboBox.setButtonCell(searchModeComboBoxCellFactory.call(null));

        // Apply CellFactory to ComboBox options
        searchModeComboBox.setCellFactory(searchModeComboBoxCellFactory);
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

    private void displayErrorDialog(String title, String message)
    {
        // Create new Alert
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set title
        alert.setTitle(title);

        // Set content
        alert.setContentText(message);

        // Show Alert
        alert.show();
    }
}