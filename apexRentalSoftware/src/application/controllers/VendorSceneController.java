package application.controllers;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import application.dao.ItemDAO;
import application.dao.StateDAO;
import application.dao.VendorDAO;
import application.dao.objects.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VendorSceneController implements Initializable{
	
	@FXML private TableView<Vendor> vendorTable;
	
	@FXML private TableColumn<Vendor, Integer> vendorIdColumn;
	@FXML private TableColumn<Vendor, String> vendorNameColumn;
	@FXML private TableColumn<Vendor, String> vendorAddressColumn;
	@FXML private TableColumn<Vendor, String> vendorCityColumn;
	@FXML private TableColumn<Vendor, String> vendorStateColumn;
	@FXML private TableColumn<Vendor, String> vendorWebsiteColumn;
	@FXML private TableColumn<Vendor, String> vendorPhoneColumn;

	@FXML private Label  outputLabel;
	@FXML private Button vendorDeleteButton;
	@FXML private Button vendorUpdateButton;
	@FXML private Button vendorInsertButton;
	@FXML private TextField vendorNameField;
	@FXML private TextField vendorAddressField;
	@FXML private TextField vendorCityField;
	@FXML private TextField vendorWebsiteField;
	@FXML private TextField vendorPhoneField;
	@FXML private ComboBox<State> vendorStateBox;
	
	VendorDAO vendorDAO = new VendorDAO();
	StateDAO stateDAO = new StateDAO();
	ItemDAO itemDAO = new ItemDAO();
	
	ArrayList<Vendor> vendorList = (ArrayList<Vendor>) vendorDAO.selectAllVendors();
	ObservableList<Vendor> vendorObsList = FXCollections.observableArrayList();
	
	ArrayList<State> stateList = (ArrayList<State>) stateDAO.selectAllStates();
	ObservableList<State> stateObsList = FXCollections.observableArrayList();
	
	ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
	ObservableList<Item> itemObsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		outputLabel.setText("Apex Rental Software increment 6");

		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<Vendor, Integer>("vendorID"));
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("name"));
		vendorAddressColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("address"));
		vendorCityColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("city"));
		vendorStateColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("state"));
		vendorWebsiteColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("website"));
		vendorPhoneColumn.setCellValueFactory(new PropertyValueFactory<Vendor, String>("phone"));
		
		// populate table and lists with observable lists
		for (Vendor aVendor : vendorList) {
			vendorObsList.add(aVendor);
		}
		vendorTable.setItems(vendorObsList);
		
		for (State aState : stateList) {
			stateObsList.add(aState);
		}
		vendorStateBox.setItems(stateObsList);
		
		// vendor table selection listener
		vendorTable.getSelectionModel().selectedItemProperty().addListener((observable) -> {
			Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();
			if (selectedVendor != null) {
				for (State i : stateObsList) {
					if (i.getStateCode().equals(selectedVendor.getState())) {
						vendorStateBox.getSelectionModel().select(i);
					}
					vendorNameField.setText(selectedVendor.getName());
					vendorAddressField.setText(selectedVendor.getAddress());
					vendorCityField.setText(selectedVendor.getCity());
					vendorPhoneField.setText(selectedVendor.getPhone());
					vendorWebsiteField.setText(selectedVendor.getWebsite());
				}
			} else {
				vendorStateBox.setValue(null);
				vendorNameField.clear();
				vendorAddressField.clear();
				vendorCityField.clear();
				vendorPhoneField.clear();
				vendorWebsiteField.clear();
			}
		});
		
	}
	
	public void vendorInsertButtonEvent() {
		
		// get user input
		String vendorPhoneToInsert = vendorPhoneField.getText().replace("-", "").replace("(", "")
				.replace(")", "").replace(" ", "");
		String vendorNameToInsert = vendorNameField.getText();
		String vendorAddressToInsert = vendorAddressField.getText();
		String vendorCityToInsert = vendorCityField.getText();
		String vendorWebsiteToInsert = vendorWebsiteField.getText();
		String vendorStateToInsert = "";
		if (vendorStateBox.getSelectionModel().getSelectedItem() != null) {
			vendorStateToInsert = vendorStateBox.getSelectionModel().getSelectedItem().getStateCode();
		}

			// validation
			if (ControlHelper.isSpaces(vendorNameField.getText())) {
				outputLabel.setText("Please enter a valid name");
				return;
			}
			if (vendorPhoneToInsert.matches("\\d{10}|^$") == false) {
				outputLabel.setText("Please enter a valid phone number");
				return;
			}
			// format phone input
			vendorPhoneToInsert = vendorPhoneToInsert.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
			// create vendor to insert
			Vendor vendorToInsert = new Vendor(0, vendorNameToInsert, vendorAddressToInsert,
					vendorCityToInsert, vendorStateToInsert, vendorWebsiteToInsert, vendorPhoneToInsert);
			if (vendorDAO.insertVendor(vendorToInsert) == true) {
				outputLabel.setText("Vendor inserted");
				
				// clear text fields
				vendorNameField.clear();
				vendorAddressField.clear();
				vendorCityField.clear();
				vendorStateBox.setValue(null);
				vendorWebsiteField.clear();
				vendorPhoneField.clear();
				
				// refresh vendor lists
				vendorList.clear();
				vendorObsList.clear();
				ArrayList<Vendor> vendorList = (ArrayList<Vendor>) vendorDAO.selectAllVendors();
				for (Vendor aVendor : vendorList) {
					vendorObsList.add(aVendor);
				}
			} else {
				outputLabel.setText("Could not insert vendor");
			}
	}	
	
	public void vendorUpdateButtonEvent() {
		// get user input
		String vendorPhoneToInsert = vendorPhoneField.getText().replace("-", "").replace("(", "")
				.replace(")", "").replace(" ", "");
		String vendorNameToInsert = vendorNameField.getText();
		String vendorAddressToInsert = vendorAddressField.getText();
		String vendorCityToInsert = vendorCityField.getText();
		String vendorWebsiteToInsert = vendorWebsiteField.getText();
		String vendorStateToInsert = "";
		if (vendorStateBox.getSelectionModel().getSelectedItem() != null) {
			vendorStateToInsert = vendorStateBox.getSelectionModel().getSelectedItem().getStateCode();
		}
			// validation
			if(vendorTable.getSelectionModel().getSelectedItem() == null) {
				outputLabel.setText("Please select a vendor to update");
				return;
			}
			if(ControlHelper.isSpaces(vendorNameField.getText())) {
				outputLabel.setText("Please enter a valid name");
				return;
			}
			if(vendorPhoneToInsert.matches("\\d{10}|^$") == false) {
				outputLabel.setText("Please enter a valid phone number");
				return;
			}
			// format phone input
			vendorPhoneToInsert = vendorPhoneToInsert.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
			// create vendor to insert
			int vendorIDToUpdate = vendorTable.getSelectionModel().getSelectedItem().getVendorID();
			Vendor vendorToUpdate = new Vendor(vendorIDToUpdate, vendorNameToInsert, vendorAddressToInsert,
					vendorCityToInsert, vendorStateToInsert, vendorWebsiteToInsert, vendorPhoneToInsert);
			if (vendorDAO.updateVendor(vendorToUpdate) == true) {
				outputLabel.setText("Vendor updated");
				
				// clear text fields
				vendorNameField.clear();
				vendorAddressField.clear();
				vendorCityField.clear();
				vendorStateBox.setValue(null);
				vendorWebsiteField.clear();
				vendorPhoneField.clear();
				
				// refresh vendor lists
				vendorList.clear();
				vendorObsList.clear();
				ArrayList<Vendor> vendorList = (ArrayList<Vendor>) vendorDAO.selectAllVendors();
				for (Vendor aVendor : vendorList) {
					vendorObsList.add(aVendor);
				}
				// refresh item list
				itemList.clear();
				itemObsList.clear();
				ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
				for (Item aItem : itemList) {
					itemObsList.add(aItem);
				}
			} else {
				outputLabel.setText("Could not update vendor");
			}
	}

	
	public void vendorDeleteButtonEvent() {
		if (vendorTable.getSelectionModel().getSelectedItem() != null) {
			Vendor vendorToDelete = vendorTable.getSelectionModel().getSelectedItem();
			if (vendorDAO.deleteVendor(vendorToDelete.getVendorID()) == false) {
				outputLabel.setText(
						"Could not delete vendor\nPlease make sure to delete all inventory items with this vendor first");
			} else {
				vendorTable.getItems().removeAll(vendorToDelete);
				outputLabel.setText("Vendor Deleted");
			}
		}
	}
	
}
