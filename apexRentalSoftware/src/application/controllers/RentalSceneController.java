package application.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.dao.*;
import application.dao.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RentalSceneController implements Initializable {

	@FXML private Label outputLabel;

	@FXML private ComboBox<Item> rentalItemBox;
	@FXML private ComboBox<Customer> rentalCustomerBox;

	@FXML private Button rentalInsertButton;
	@FXML private Button rentalReturnButton;
	@FXML private Button rentalUpdateButton;
	@FXML private Button rentalDeleteButton;

	@FXML private TableView<Rental> rentalTable;
	@FXML private TableColumn<Rental, Integer> rentalIdColumn;
	@FXML private TableColumn<Rental, String> rentalCustomerNameColumn;
	@FXML private TableColumn<Rental, String> rentalVendorNameColumn;
	@FXML private TableColumn<Rental, String> rentalItemNameColumn;
	@FXML private TableColumn<Rental, String> rentalItemSerialColumn;
	@FXML private TableColumn<Rental, Timestamp> rentalDateColumn;
	@FXML private TableColumn<Rental, Boolean> rentalReturnedColumn;

	// Data Access Objects connect to database tables
	ItemDAO itemDAO = new ItemDAO();
	CustomerDAO customerDAO = new CustomerDAO();
	VendorDAO vendorDAO = new VendorDAO();
	RentalDAO rentalDAO = new RentalDAO();

	// Observable lists contain database entries
	ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
	ObservableList<Item> itemObsList = FXCollections.observableArrayList();

	ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.selectAllCustomers();
	ObservableList<Customer> customerObsList = FXCollections.observableArrayList();

	ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
	ObservableList<Rental> rentalObsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		outputLabel.setText("Apex Rental Software increment 6");

		rentalIdColumn.setCellValueFactory(new PropertyValueFactory<Rental, Integer>("rentalID"));
		rentalCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<Rental, String>("customerName"));
		rentalVendorNameColumn.setCellValueFactory(new PropertyValueFactory<Rental, String>("vendorName"));
		rentalItemNameColumn.setCellValueFactory(new PropertyValueFactory<Rental, String>("itemName"));
		rentalItemSerialColumn.setCellValueFactory(new PropertyValueFactory<Rental, String>("serial"));
		rentalDateColumn.setCellValueFactory(new PropertyValueFactory<Rental, Timestamp>("date"));
		rentalReturnedColumn.setCellValueFactory(new PropertyValueFactory<Rental, Boolean>("returned"));
		
		// format date
		rentalDateColumn.setCellFactory(new ColumnFormatter<Rental, Timestamp>(new SimpleDateFormat("MM/dd/yy h:mm a")));

		// populate table and lists with observable lists
		for (Rental aRental : rentalList) {
			rentalObsList.add(aRental);
		}
		rentalTable.setItems(rentalObsList);

		for (Item aItem : itemList) {
			itemObsList.add(aItem);
		}
		rentalItemBox.setItems(itemObsList);

		for (Customer aCustomer : customerList) {
			customerObsList.add(aCustomer);
		}
		rentalCustomerBox.setItems(customerObsList);

		// rental table selection listener
		rentalTable.getSelectionModel().selectedItemProperty().addListener((observable) -> {
			Rental selectedRental = rentalTable.getSelectionModel().getSelectedItem();
			if (selectedRental != null) {
				for (Item i : itemObsList) {
					if (i.getItemID() == selectedRental.getItemID()) {
						rentalItemBox.getSelectionModel().select(i);
					}
				}
				for (Customer c : customerObsList) {
					if (c.getCustomerID() == selectedRental.getCustomerID()) {
						rentalCustomerBox.getSelectionModel().select(c);
					}
				}
			} else {
				rentalCustomerBox.setValue(null);
				rentalItemBox.setValue(null);
			}
		});

	}

	public void insertRentalButtonEvent() {

		if (rentalCustomerBox.getSelectionModel().getSelectedItem() == null
				|| rentalItemBox.getSelectionModel().getSelectedItem() == null) {
			outputLabel.setText("Please select a customer and item to create the rental with");
			return;
		}

		Customer customerToInsert = rentalCustomerBox.getSelectionModel().getSelectedItem();
		Item itemToInsert = rentalItemBox.getSelectionModel().getSelectedItem();

		if (itemToInsert.getStocked() == false) {
			outputLabel
					.setText("Could not create rental \nPlease make sure that the item is not already being rented ");
			return;
		}

		// create rental object to insert
		int customerToInsertID = customerToInsert.getCustomerID();
		int itemIDToInsert = itemToInsert.getItemID();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Rental tempRental = new Rental(0, "null", "null", "null", "null", currentTime, false, customerToInsertID,
				itemIDToInsert);

		// insert rental if validation passed
		if (rentalDAO.insertRental(tempRental) == true) {
			outputLabel.setText("New Rental Created");

			// refresh rental lists
			rentalList.clear();
			rentalObsList.clear();
			ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
			for (Rental aRental : rentalList) {
				rentalObsList.add(aRental);
			}

			// refresh item list
			itemList.clear();
			itemObsList.clear();
			ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
			for (Item aItem : itemList) {
				itemObsList.add(aItem);
			}
		} else {
			outputLabel.setText("Could not insert Rental.");
		}
	}

	public void returnRentalButtonEvent() {
		if (rentalTable.getSelectionModel().getSelectedItem() != null
				&& rentalTable.getSelectionModel().getSelectedItem().isReturned() == false) {
			if (RentalDAO.returnRental(rentalTable.getSelectionModel().getSelectedItem()) == true) {
				outputLabel.setText("Rental Returned");

				// refresh rental lists
				rentalList.clear();
				rentalObsList.clear();
				ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
				for (Rental aRental : rentalList) {
					rentalObsList.add(aRental);
				}

				// refresh item list
				itemList.clear();
				itemObsList.clear();
				ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
				for (Item aItem : itemList) {
					itemObsList.add(aItem);
				}
			}
		} else {
			outputLabel.setText("Could not return rental");
		}

	}

	public void updateRentalButtonEvent() {
		// condition validation
		if (rentalTable.getSelectionModel().getSelectedItem() == null) {
			outputLabel.setText("Please select a rental to update");
			return;
		}
		if (rentalCustomerBox.getSelectionModel().getSelectedItem() == null
				|| rentalItemBox.getSelectionModel().getSelectedItem() == null) {
			outputLabel.setText("Please select a customer and item to update rental");
			return;
		}
		Rental selectedRental = rentalTable.getSelectionModel().getSelectedItem();
		Item itemToUpdate = rentalItemBox.getSelectionModel().getSelectedItem();

		if (selectedRental.getItemID() != itemToUpdate.getItemID() && itemToUpdate.getStocked() == false) {
			outputLabel.setText("Could not update rental\nPlease make sure new item is not already being rented");
			return;
		}
		// get user selection
		int itemIDToUpdate = itemToUpdate.getItemID();
		int customerIDToUpdate = rentalCustomerBox.getSelectionModel().getSelectedItem().getCustomerID();
		int rentalIDToUpdate = rentalTable.getSelectionModel().getSelectedItem().getRentalID();
		// create rental object to update database
		Rental tempRental = new Rental(rentalIDToUpdate, "null", "null", "null", "null", null, false,
				customerIDToUpdate, itemIDToUpdate);
		// return old item if new item is different
		if (selectedRental.getItemID() != itemToUpdate.getItemID() && selectedRental.isReturned() == false) {
			RentalDAO.returnRental(rentalTable.getSelectionModel().getSelectedItem());
		}

		// update rental
		if (rentalDAO.updateRental(tempRental) == true) {
			outputLabel.setText("Rental Updated");

			// refresh rental lists
			rentalList.clear();
			rentalObsList.clear();
			ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
			for (Rental aRental : rentalList) {
				rentalObsList.add(aRental);
			}
			// refresh item list
			itemList.clear();
			itemObsList.clear();
			ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
			for (Item aItem : itemList) {
				itemObsList.add(aItem);
			}
		} else {
			outputLabel.setText("Could not update Rental.");
		}
	}

	public void deleteRentalButtonEvent() {

		if (rentalTable.getSelectionModel().getSelectedItem() != null) {
			Rental rentalToDelete = rentalTable.getSelectionModel().getSelectedItem();

			if (rentalToDelete.isReturned() != true) {
				outputLabel.setText("Could not Delete Rental\nPlease return rental first");
				return;
			}

			if (rentalDAO.deleteRental(rentalToDelete) == true) {
				rentalTable.getItems().removeAll(rentalToDelete);
				outputLabel.setText("Rental Deleted");
			} else {
				outputLabel.setText("Could not delete Rental");
			}
		}
	}
}
