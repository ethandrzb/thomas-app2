# Graphical Inventory Manager

## User Guide

### Managing Items
* **Add a new item:** Fill in the fields labeled "Item Name", "Serial Number", and "Value" with their respective values and click the "Add Item" button.
* **Removing items:** Select the item you want to remove, then go to the "Edit" menu and click "Remove selected items".
  * Multiple items can be selected individually or by specifying a range.
    * **Individually:** Hold the Control key and click on the rows of the items you wish to select.
    * **Specify Range:** Hold the Shift key and click on the first and last items in the range you wish to select.
* **Remove all items:** Go to the "Edit" menu and click "Clear inventory".

### Editing Items
* Inventory items can be edited at any time by double-clicking the desired field of the item you wish to edit.
  * Changes are committed upon pressing the Enter key.

### Item Property Validation
* Item fields
  * **Name:** Must be a string between 2 and 256 characters long.
  * **Serial Number:** Must be a string in the format A-XXX-XXX-XXX where A is any letter and X can be a letter or a digit. All items must have unique serial numbers.
  * **Value:** Must be a decimal number greater than 0.
* Fields are automatically validated whenever an item is added to the inventory, an existing item is edited, and when an inventory is loaded from a file.

### Searching for Items
* The inventory can be searched using the search bar in the center above the table, either by name or by item serial number.
  * The search mode can be changed by using the dropdown menu to the right of the search bar and selecting the desired search mode. 
  * The table contents are automatically updated whenever the search query changes.

### Sorting Items
* Inventory items can be sorted by clicking on the table headers (e.g. Name, Serial Number, Value).
* Column sort options
  * **Not sorted** (Nothing added to column header; default sort option)
  * **Ascending order** (▲ displayed by column header)
  * **Descending order** (▼ displayed by column header)

### Saving and Loading Inventories
* **Saving an inventory:** Go to the "File" menu, click "Save", and specify the name, location, and extension (file type) of the exported inventory.
* **Loading an inventory:** Go to the "File" menu, click "Load", and specify the name, location, and extension (file type) of the inventory you'd like to load.
  * Loading an inventory will replace the current inventory with the loaded inventory. 
* **Supported file extensions:** TXT, HTML, JSON  