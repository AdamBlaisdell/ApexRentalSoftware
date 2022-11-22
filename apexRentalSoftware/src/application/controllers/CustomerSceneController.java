package application.controllers;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.dao.CustomerDAO;
import application.dao.StateDAO;
import application.dao.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerSceneController implements Initializable  {
	
	@FXML private ComboBox<State> customerStateBox;
	@FXML private Button customerInsertButton;
	@FXML private Button customerUpdateButton;
	@FXML private Button customerDeleteButton;
	@FXML private TextField customerNameField;
	@FXML private TextField customerAddressField;
	@FXML private TextField customerCityField;
	@FXML private TextField customerPhoneField;
	
	@FXML private Label outputLabel;
	@FXML private TableView<Customer> customerTable;
	@FXML private TableColumn<Customer, Integer> customerIdColumn;
	@FXML private TableColumn<Customer, String> customerNameColumn;
	@FXML private TableColumn<Customer, String> customerAddressColumn;
	@FXML private TableColumn<Customer, String> customerCityColumn;
	@FXML private TableColumn<Customer, String> customerStateColumn;
	@FXML private TableColumn<Customer, String> customerPhoneColumn;
	
	CustomerDAO customerDAO = new CustomerDAO();
	StateDAO stateDAO = new StateDAO();
	
	ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.selectAllCustomers();
	ObservableList<Customer> customerObsList = FXCollections.observableArrayList();
	
	ArrayList<State> stateList = (ArrayList<State>) stateDAO.selectAllStates();
	ObservableList<State> stateObsList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		outputLabel.setText("Apex Rental Software increment 6");

		customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
		customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
		customerCityColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
		customerStateColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("state"));
		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
		
		
		// populate table and lists with observable lists
		for (Customer aCustomer : customerList) {
			customerObsList.add(aCustomer);
		}
		customerTable.setItems(customerObsList);
		
		for (State aState : stateList) {
			stateObsList.add(aState);
		}
		customerStateBox.setItems(stateObsList);
		
		
		// customer table selection listener
		customerTable.getSelectionModel().selectedItemProperty().addListener((observable) -> {
			Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
			if (selectedCustomer != null) {
				for (State i : stateObsList) {
					if (i.getStateCode().equals(selectedCustomer.getState())) {
						customerStateBox.getSelectionModel().select(i);
					}
					customerNameField.setText(selectedCustomer.getName());
					customerAddressField.setText(selectedCustomer.getAddress());
					customerCityField.setText(selectedCustomer.getCity());
					customerPhoneField.setText(selectedCustomer.getPhone());
				}
			} else {
				customerStateBox.setValue(null);
				customerNameField.clear();
				customerAddressField.clear();
				customerCityField.clear();
				customerPhoneField.clear();
			}
		});
		
	}
	
	public void customerInsertButtonEvent() {
				// get user input
				String customerPhoneToInsert = customerPhoneField.getText().replace("-", "").replace("(", "")
						.replace(")", "").replace(" ", "");
				String customerNameToInsert = customerNameField.getText();
				String customerAddressToInsert = customerAddressField.getText();
				String customerCityToInsert = customerCityField.getText();
				String customerStateToInsert = "";
				if (customerStateBox.getSelectionModel().getSelectedItem() != null) {
					customerStateToInsert = customerStateBox.getSelectionModel().getSelectedItem().getStateCode();
				}

					// validation
					if (ControlHelper.isSpaces(customerNameToInsert)) {
						outputLabel.setText("Please enter a valid name");
						return;
					}
					if (customerPhoneToInsert.matches("\\d{10}|^$") == false) {
						outputLabel.setText("Please enter a valid phone number");
						return;
					}

					// format phone input
					customerPhoneToInsert = customerPhoneToInsert.replaceFirst("(\\d{3})(\\d{3})(\\d+)",
							"($1) $2-$3");
					// create customer to insert
					Customer customerToInsert = new Customer(0, customerNameToInsert, customerAddressToInsert,
							customerCityToInsert, customerStateToInsert, customerPhoneToInsert);
					// insert customer into database
					if (customerDAO.insertCustomer(customerToInsert) == true) {
						outputLabel.setText("Customer inserted");
						
						// clear text fields
						customerNameField.clear();
						customerAddressField.clear();
						customerCityField.clear();
						customerPhoneField.clear();
						customerStateBox.valueProperty().set(null);
						customerStateBox.setPromptText("State");
						// refresh customer list
						customerList.clear();
						customerObsList.clear();
						ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.selectAllCustomers();
						for (Customer aCustomer : customerList) {
							customerObsList.add(aCustomer);
						}
					} else {
						outputLabel.setText("Could not insert Customer");
					}
	}
	
	
	public void customerUpdateButtonEvent() {
		
		// get user input
		String customerPhoneToUpdate = customerPhoneField.getText().replace("-", "").replace("(", "")
				.replace(")", "").replace(" ", "");
		String customerNameToUpdate = customerNameField.getText();
		String customerAddressToUpdate = customerAddressField.getText();
		String customerCityToUpdate = customerCityField.getText();
		String customerStateToUpdate = "";
		if (customerStateBox.getSelectionModel().getSelectedItem() != null) {
			customerStateToUpdate = customerStateBox.getSelectionModel().getSelectedItem().getStateCode();
		}

		while (true) {
			// validation
			if (customerTable.getSelectionModel().getSelectedItem() == null) {
				outputLabel.setText("Please select a customer to update");
				return;
			}
			if (ControlHelper.isSpaces(customerNameToUpdate)) {
				outputLabel.setText("Please enter a valid name");
				return;
			}
			if (customerPhoneToUpdate.matches("\\d{10}|^$") == false) {
				outputLabel.setText("Please enter a valid phone number");
				return;
			}

			int customerIDToUpdate = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
			// format phone input
			customerPhoneToUpdate = customerPhoneToUpdate.replaceFirst("(\\d{3})(\\d{3})(\\d+)",
					"($1) $2-$3");
			// create customer to update
			Customer customerToUpdate = new Customer(customerIDToUpdate, customerNameToUpdate,
					customerAddressToUpdate, customerCityToUpdate, customerStateToUpdate,
					customerPhoneToUpdate);

			// update customer in database
			if (customerDAO.updateCustomer(customerToUpdate) == true) {
				outputLabel.setText("Customer updated");
				// clear text fields
				customerNameField.clear();
				customerAddressField.clear();
				customerCityField.clear();
				customerPhoneField.clear();
				customerStateBox.valueProperty().set(null);
				customerStateBox.setPromptText("State");
				// refresh customer list
				customerList.clear();
				customerObsList.clear();
				ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.selectAllCustomers();
				for (Customer aCustomer : customerList) {
					customerObsList.add(aCustomer);
				}

			} else {
				outputLabel.setText("Could not update Customer");
			}
		}
		
	}
	
	public void customerDeleteButtonEvent() {
		
		if (customerTable.getSelectionModel().getSelectedItem() != null) {
			Customer customerToDelete = customerTable.getSelectionModel().getSelectedItem();
			
			if (customerDAO.deleteCustomer(customerToDelete.getCustomerID()) == false) {
				outputLabel.setText(
						"Could not delete customer\nPlease make sure to delete all rentals with this customer first");
			} else {
				customerTable.getItems().removeAll(customerToDelete);
				outputLabel.setText("Customer Deleted");
			}
		}
		
	}
	
}
