package application.controllers;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.dao.objects.*;
import application.dao.ItemDAO;
import application.dao.VendorDAO;
import application.dao.RentalDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemSceneController implements Initializable {
	
	@FXML private Label outputLabel;
	@FXML private Button itemInsertButton;
	@FXML private Button itemUpdateButton;
	@FXML private Button itemDeleteButton;
	@FXML private TextField itemNameField;
	@FXML private TextField itemSerialField;
	@FXML private TextField itemCostField;
	@FXML private ComboBox<Vendor> itemVendorBox;
	
	@FXML private TableView<Item> itemTable;
	@FXML private TableColumn<Item, Integer> itemIDColumn;
	@FXML private TableColumn<Item, String> vendorNameColumn;
	@FXML private TableColumn<Item, String> itemNameColumn;
	@FXML private TableColumn<Item, String> itemSerialColumn;
	@FXML private TableColumn<Item, Boolean> itemStockedColumn;
	@FXML private TableColumn<Item, Integer> itemCostColumn;
	
	ItemDAO itemDAO = new ItemDAO();
	VendorDAO vendorDAO = new VendorDAO();
	RentalDAO rentalDAO = new RentalDAO();

	ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
	ObservableList<Rental> rentalObsList = FXCollections.observableArrayList();
	
	ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
	ObservableList<Item> itemObsList = FXCollections.observableArrayList();
	
	ArrayList<Vendor> vendorList = (ArrayList<Vendor>) vendorDAO.selectAllVendors();
	ObservableList<Vendor> vendorObsList = FXCollections.observableArrayList();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		outputLabel.setText("Apex Rental Software increment 6");

		itemIDColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("ItemID"));
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("vendorName"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		itemSerialColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("serial"));
		itemStockedColumn.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("stocked"));
		itemCostColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cost"));
		
		
		// populate table and lists with observable lists
		for (Item aItem : itemList) {
			itemObsList.add(aItem);
		}
		itemTable.setItems(itemObsList);
		
		for (Vendor aVendor : vendorList) {
			vendorObsList.add(aVendor);
		}
		itemVendorBox.setItems(vendorObsList);
		
		// item table selection listener
		itemTable.getSelectionModel().selectedItemProperty().addListener((observable) -> {
			Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				for (Vendor i : vendorObsList) {
					if (i.getVendorID() == selectedItem.getVendorID()) {
						itemVendorBox.getSelectionModel().select(i);
					}
					itemNameField.setText(selectedItem.getName());
					itemSerialField.setText(selectedItem.getSerial());
					itemCostField.setText(String.format("%.2f", selectedItem.getCost()));
				}
			} else {
				itemVendorBox.setValue(null);
				itemNameField.clear();
				itemSerialField.clear();
				itemCostField.clear();
			}
		});
		
	}
	
	public void itemInsertButtonEvent() {

		String itemNameToInsert = itemNameField.getText();
		String itemSerialToInsert = itemSerialField.getText();
		double itemCostToInsert = 0;
			// validation
			if(itemVendorBox.getSelectionModel().getSelectedItem() == null) {
				outputLabel.setText("Please select a vendor");
				return;
			}
			if(ControlHelper.isSpaces(itemNameField.getText())) {
				outputLabel.setText("Please enter a valid name");
				return;
			}
			if(itemCostField.getText().matches("^(\\d*\\.)?\\d+$|^$") == false) {
				outputLabel.setText("Invalid cost input");
				return;
			}
			if (ControlHelper.isSpaces(itemCostField.getText()) == false){
				if(Double.valueOf(itemCostField.getText()) > 1000000000000D) {
					outputLabel.setText("Cost is value too large");
					return;
				}
			}
			// create item to insert
			int itemVendorIDToInsert = itemVendorBox.getSelectionModel().getSelectedItem().getVendorID();
			if(ControlHelper.isSpaces(itemCostField.getText()) == false) {
				itemCostToInsert = Double.valueOf(itemCostField.getText());
				}
			Item itemToInsert = new Item(0, itemVendorIDToInsert, "", itemNameToInsert, itemSerialToInsert,
					true, itemCostToInsert);
			// insert item into database
			if (itemDAO.insertItem(itemToInsert) == true) {
				outputLabel.setText("Item inserted");
				
				// clear text fields
				itemNameField.clear();
				itemSerialField.clear();
				itemCostField.clear();
				
				// refresh item list
				itemList.clear();
				itemObsList.clear();
				ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
				for (Item aItem : itemList) {
					itemObsList.add(aItem);
				}
			} else {
				outputLabel.setText("Could not insert Item");
			}
	}
	
	public void itemUpdateButtonEvent() {
		String itemNameToInsert = itemNameField.getText();
		String itemSerialToInsert = itemSerialField.getText();
		double itemCostToInsert = 0;
		while(true) {
			if(itemTable.getSelectionModel().getSelectedItem() == null) {
				outputLabel.setText("Please select an item to update");
				return;
			}
			if(itemVendorBox.getSelectionModel().getSelectedItem() == null) {
				outputLabel.setText("Please select a vendor");
				return;
			}
			if(ControlHelper.isSpaces(itemNameField.getText())) {
				outputLabel.setText("Please enter a valid name");
				return;
			}
			if(itemCostField.getText().matches("^(\\d*\\.)?\\d+$|^$") == false) {
				outputLabel.setText("Invalid cost input");
				return;
			}
			if(Double.valueOf(itemCostField.getText()) > 1000000000000D) {
				outputLabel.setText("Cost value is too large");
				return;
			}
			int itemVendorIDToInsert = itemVendorBox.getSelectionModel().getSelectedItem().getVendorID();
			int itemIDToUpdate = itemTable.getSelectionModel().getSelectedItem().getItemID();
			if(ControlHelper.isSpaces(itemCostField.getText()) == false) {
				itemCostToInsert = Double.valueOf(itemCostField.getText());
				}
			Item itemToUpdate = new Item(itemIDToUpdate, itemVendorIDToInsert, "", itemNameToInsert, itemSerialToInsert,
					true, itemCostToInsert);
			if (itemDAO.updateItem(itemToUpdate) == true) {
				outputLabel.setText("Item updated");
				// clear text fields
				itemNameField.clear();
				itemSerialField.clear();
				itemCostField.clear();
				// refresh item list
				itemList.clear();
				itemObsList.clear();
				ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
				for (Item aItem : itemList) {
					itemObsList.add(aItem);
				}
				// refresh rental lists
				rentalList.clear();
				rentalObsList.clear();
				ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
				for (Rental aRental : rentalList) {
					rentalObsList.add(aRental);
				}
			} else {
				outputLabel.setText("Could not update Item");
			}
		}
		
	}
	
	public void itemDeleteButtonEvent() {
		if (itemTable.getSelectionModel().getSelectedItem() != null) {
			Item itemToDelete = itemTable.getSelectionModel().getSelectedItem();
			if (itemDAO.deleteItem(itemToDelete.getItemID()) == false) {
				outputLabel.setText(
						"Could not delete item\nPlease make sure to delete all rentals with this item first");
			} else {
				itemTable.getItems().removeAll(itemToDelete);
				outputLabel.setText("Item Deleted");
			}
		}
		
	}
	
}
