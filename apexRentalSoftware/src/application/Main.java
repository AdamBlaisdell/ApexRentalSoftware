/*
 * Apex Rental Software 
 * Author: Adam Blaisdell
 * Last Edit: 10/27/2021
 */

package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.Timestamp;

public class Main extends Application {

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {

		int tablex = 190;
		int tabley = 15;
		int tablew = 640;
		int tableh = 370;
		String defaultPrompt = "Apex Rental Software Increment 4 ";

		try {

			// set the title
			primaryStage.setTitle("Apex Rental Software");

			// create panes
			Pane rentalPane = new Pane();
			Pane customerPane = new Pane();
			Pane itemPane = new Pane();
			Pane vendorPane = new Pane();

			// create scenes
			Scene rentalScene = new Scene(rentalPane, 850, 600);
			Scene customerScene = new Scene(customerPane, 850, 600);
			Scene itemScene = new Scene(itemPane, 850, 600);
			Scene vendorScene = new Scene(vendorPane, 850, 600);

			// create DAO connections
			RentalDAO rentalDAO = new RentalDAO();
			CustomerDAO customerDAO = new CustomerDAO();
			ItemDAO itemDAO = new ItemDAO();
			VendorDAO vendorDAO = new VendorDAO();

			// create tableviews
			TableView<Rental> rentalTable = new TableView<>();
			TableView<Customer> customerTable = new TableView<>();
			TableView<Item> itemTable = new TableView<>();
			TableView<Vendor> vendorTable = new TableView<>();

			// create buttons
			Button rentalButton = new Button("Rentals");
			Button customerButton = new Button("Customers");
			Button itemButton = new Button("Inventory");
			Button vendorButton = new Button("Vendors");
			Button deleteButton = new Button("Delete");
			Button rentalInsertButton = new Button("Insert");
			Button vendorInsertButton = new Button("Insert");
			Button rentalReturnButton = new Button("Return");

			// create combo boxes
			ComboBox<Customer> rentalCustomerBox = new ComboBox<>();
			ComboBox<Item> rentalItemBox = new ComboBox<>();
			ComboBox<Vendor> itemVendorBox = new ComboBox<>();

			// create labels
			Label outputLabel = new Label(defaultPrompt);
			outputLabel.setId("outputLabel");
			Label vendorNameLabel = new Label("Name");
			
			// create text boxes
			TextField vendorNameField = new TextField("Name");
			TextField vendorAddressField = new TextField("Address");
			TextField vendorCityField = new TextField("City");
			TextField vendorStateField = new TextField("State");
			TextField vendorWebsiteField = new TextField("Website");
			TextField vendorPhoneField = new TextField("Phone");
			
			TextField itemNameField = new TextField("Name");
			TextField itemSerialField = new TextField("Serial");
			TextField itemCostField = new TextField("Cost");
			
			// element layout
			rentalButton.setLayoutX(30);
			rentalButton.setLayoutY(40);
			customerButton.setLayoutX(30);
			customerButton.setLayoutY(80);
			itemButton.setLayoutX(30);
			itemButton.setLayoutY(120);
			vendorButton.setLayoutX(30);
			vendorButton.setLayoutY(160);
			deleteButton.setLayoutX(190);
			deleteButton.setLayoutY(390);
			rentalInsertButton.setLayoutX(600);
			rentalInsertButton.setLayoutY(440);
			vendorInsertButton.setLayoutX(500);
			vendorInsertButton.setLayoutY(500);
			rentalReturnButton.setLayoutX(315);
			rentalReturnButton.setLayoutY(390);

			outputLabel.setLayoutX(300);
			outputLabel.setLayoutY(530);

			rentalCustomerBox.setLayoutX(190);
			rentalCustomerBox.setLayoutY(440);
			rentalItemBox.setLayoutX(395);
			rentalItemBox.setLayoutY(440);
			
			vendorNameLabel.setLayoutX(190);
			vendorNameLabel.setLayoutY(420);
			
			vendorStateField.setMaxWidth(50);
			vendorNameField.setLayoutX(190);
			vendorNameField.setLayoutY(450);
			vendorAddressField.setLayoutX(340);
			vendorAddressField.setLayoutY(450);
			vendorCityField.setLayoutX(490);
			vendorCityField.setLayoutY(450);
			vendorStateField.setLayoutX(640);
			vendorStateField.setLayoutY(450);
			vendorWebsiteField.setLayoutX(190);
			vendorWebsiteField.setLayoutY(500);
			vendorPhoneField.setLayoutX(340);
			vendorPhoneField.setLayoutY(500);
			
			itemNameField.setLayoutX(315);
			itemNameField.setLayoutY(450);
			itemNameField.setMaxWidth(100);
			itemSerialField.setLayoutX(420);
			itemSerialField.setLayoutY(450);
			itemCostField.setLayoutX(570);
			itemCostField.setLayoutY(450);
			itemCostField.setMaxWidth(100);
			
			itemVendorBox.setLayoutX(190);
			itemVendorBox.setLayoutY(450);
			itemVendorBox.setMaxWidth(120);
			

			// RENTAL TABLE
			TableColumn<Rental, Number> rentalIDColumn = new TableColumn<>("Rental ID");
			rentalIDColumn.setCellValueFactory(cellData -> cellData.getValue().rentalIDProperty());
			TableColumn<Rental, String> rentalCustomerNameColumn = new TableColumn<>("Customer");
			rentalCustomerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
			TableColumn<Rental, String> rentalVendorNameColumn = new TableColumn<>("Vendor");
			rentalVendorNameColumn.setCellValueFactory(cellData -> cellData.getValue().vendorNameProperty());
			TableColumn<Rental, String> rentalItemNameColumn = new TableColumn<>("Item");
			rentalItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
			TableColumn<Rental, String> rentalSerialColumn = new TableColumn<>("Serial #");
			rentalSerialColumn.setCellValueFactory(cellData -> cellData.getValue().serialProperty());
			TableColumn<Rental, Timestamp> rentalDateColumn = new TableColumn<>("Date");
			rentalDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			TableColumn<Rental, Boolean> rentalReturnedColumn = new TableColumn<>("Returned");
			rentalReturnedColumn.setCellValueFactory(cellData -> cellData.getValue().returnedProperty());
			// add columns to table
			rentalTable.getColumns().addAll(rentalIDColumn, rentalCustomerNameColumn, rentalVendorNameColumn,
					rentalItemNameColumn, rentalSerialColumn, rentalDateColumn, rentalReturnedColumn);
			rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of rentals from DAO
			ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
			// create rental observable list for table
			ObservableList<Rental> rentalObsList = FXCollections.observableArrayList();
			// add rentals to observable list from item array list
			for (Rental aRental : rentalList) {
				rentalObsList.add(aRental);
			}
			// populate table with observable list
			rentalTable.setItems(rentalObsList);
			// table layout
			rentalTable.setLayoutX(tablex);
			rentalTable.setLayoutY(tabley);
			rentalTable.setPrefWidth(tablew);
			rentalTable.setPrefHeight(tableh);

			// CUSTOMER TABLE
			TableColumn<Customer, Number> customerIDColumn = new TableColumn<>("Customer ID");
			customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
			TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Name");
			customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			TableColumn<Customer, String> customerAddressColumn = new TableColumn<>("Address");
			customerAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
			TableColumn<Customer, String> customerCityColumn = new TableColumn<>("City");
			customerCityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
			TableColumn<Customer, String> customerStateColumn = new TableColumn<>("State");
			customerStateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
			TableColumn<Customer, String> customerPhoneColumn = new TableColumn<>("Phone #");
			customerPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asString("%.0f"));
			// add columns to table
			customerTable.getColumns().addAll(customerIDColumn, customerNameColumn, customerAddressColumn,
					customerCityColumn, customerStateColumn, customerPhoneColumn);
			customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of customers from DAO
			ArrayList<Customer> customerList = (ArrayList<Customer>) customerDAO.selectAllCustomers();
			// create customer observable list for table
			ObservableList<Customer> customerObsList = FXCollections.observableArrayList();
			// add customers to observable list from item array list
			for (Customer aCustomer : customerList) {
				customerObsList.add(aCustomer);
			}
			// populate table with observable list
			customerTable.setItems(customerObsList);
			// table layout
			customerTable.setLayoutX(tablex);
			customerTable.setLayoutY(tabley);
			customerTable.setPrefWidth(tablew);
			customerTable.setPrefHeight(tableh);

			// ITEM TABLE
			TableColumn<Item, Number> itemIDColumn = new TableColumn<>("Item ID");
			itemIDColumn.setCellValueFactory(cellData -> cellData.getValue().itemIDProperty());
			TableColumn<Item, String> itemVendorNameColumn = new TableColumn<>("Vendor");
			itemVendorNameColumn.setCellValueFactory(cellData -> cellData.getValue().vendorNameProperty());
			TableColumn<Item, String> itemNameColumn = new TableColumn<>("Name");
			itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			TableColumn<Item, String> serialColumn = new TableColumn<>("Serial#");
			serialColumn.setCellValueFactory(cellData -> cellData.getValue().serialProperty());
			TableColumn<Item, Boolean> stockedColumn = new TableColumn<>("Stocked");
			stockedColumn.setCellValueFactory(cellData -> cellData.getValue().stockedProperty());
			TableColumn<Item, Number> costColumn = new TableColumn<>("Cost");
			costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());
			// add columns to table
			itemTable.getColumns().addAll(itemIDColumn, itemVendorNameColumn, itemNameColumn, serialColumn,
					stockedColumn, costColumn);
			itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of items from DAO
			ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
			// create item observable list for table
			ObservableList<Item> itemObsList = FXCollections.observableArrayList();
			// add items to observable list from item array list
			for (Item aItem : itemList) {
				itemObsList.add(aItem);
			}
			// populate table with observable list
			itemTable.setItems(itemObsList);
			// table layout
			itemTable.setLayoutX(tablex);
			itemTable.setLayoutY(tabley);
			itemTable.setPrefWidth(tablew);
			itemTable.setPrefHeight(tableh);

			// VENDOR TABLE
			TableColumn<Vendor, Number> vendorIDColumn = new TableColumn<>("Vendor ID");
			vendorIDColumn.setCellValueFactory(cellData -> cellData.getValue().vendorIDProperty());
			TableColumn<Vendor, String> vendorNameColumn = new TableColumn<>("Name");
			vendorNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			TableColumn<Vendor, String> vendorAddressColumn = new TableColumn<>("Address");
			vendorAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
			TableColumn<Vendor, String> vendorCityColumn = new TableColumn<>("City");
			vendorCityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
			TableColumn<Vendor, String> vendorStateColumn = new TableColumn<>("State");
			vendorStateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
			TableColumn<Vendor, String> vendorWebsiteColumn = new TableColumn<>("Website");
			vendorWebsiteColumn.setCellValueFactory(cellData -> cellData.getValue().websiteProperty());
			TableColumn<Vendor, String> vendorPhoneColumn = new TableColumn<>("Phone #");
			vendorPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asString("%.0f"));
			// add columns to table
			vendorTable.getColumns().addAll(vendorIDColumn, vendorNameColumn, vendorAddressColumn, vendorCityColumn,
					vendorStateColumn, vendorWebsiteColumn, vendorPhoneColumn);
			vendorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of vendors from DAO
			ArrayList<Vendor> vendorList = (ArrayList<Vendor>) vendorDAO.selectAllVendors();
			// create vendor observable list for table
			ObservableList<Vendor> vendorObsList = FXCollections.observableArrayList();
			// add vendors to observable list from vendor array list
			for (Vendor aVendor : vendorList) {
				vendorObsList.add(aVendor);
			}
			// populate table with observable list
			vendorTable.setItems(vendorObsList);
			// table layout
			vendorTable.setLayoutX(tablex);
			vendorTable.setLayoutY(tabley);
			vendorTable.setPrefWidth(tablew);
			vendorTable.setPrefHeight(tableh);

			// populate comboboxes with observable lists
			rentalCustomerBox.setItems(customerObsList);
			rentalItemBox.setItems(itemObsList);

			// add tables and elements to panes
			rentalPane.getChildren().addAll(rentalTable, rentalButton, customerButton, itemButton, vendorButton,
					deleteButton, outputLabel, rentalInsertButton, rentalCustomerBox, rentalItemBox, rentalReturnButton);
			customerPane.getChildren().add(customerTable);
			itemPane.getChildren().addAll(itemTable, itemNameField, itemSerialField, itemCostField, itemVendorBox);
			vendorPane.getChildren().addAll(vendorTable, vendorNameField, vendorAddressField, 
					vendorCityField, vendorStateField, vendorWebsiteField, vendorPhoneField, vendorInsertButton, vendorNameLabel);

			// add css to scenes
			rentalScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			customerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			itemScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			vendorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// set scene and show stage
			primaryStage.setScene(rentalScene);
			primaryStage.show();

			// BUTTON ACTION EVENTS
			// rental button event
			EventHandler<ActionEvent> rentalButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if (primaryStage.getScene() != rentalScene) {
						primaryStage.setScene(rentalScene);
						// if elements are added to a scene they are removed from other scenes, so this
						// must occur for all scenes to prevent duplicate elements.
						rentalPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton,
								deleteButton, outputLabel);
						rentalTable.getSelectionModel().clearSelection();
						outputLabel.setText(defaultPrompt);
					}
				}
			};
			rentalButton.setOnAction(rentalButtonEvent);

			// customer button event
			EventHandler<ActionEvent> customerButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if (primaryStage.getScene() != customerScene) {
						primaryStage.setScene(customerScene);
						customerPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton,
								deleteButton, outputLabel);
						customerTable.getSelectionModel().clearSelection();
						outputLabel.setText(defaultPrompt);
					}
				}
			};
			customerButton.setOnAction(customerButtonEvent);

			// item button event
			EventHandler<ActionEvent> itemButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if (primaryStage.getScene() != itemScene) {
						primaryStage.setScene(itemScene);
						itemPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton,
								deleteButton, outputLabel);
						itemTable.getSelectionModel().clearSelection();
						outputLabel.setText(defaultPrompt);
					}
				}
			};
			itemButton.setOnAction(itemButtonEvent);

			// vendor button event
			EventHandler<ActionEvent> vendorButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if (primaryStage.getScene() != vendorScene) {
						primaryStage.setScene(vendorScene);
						vendorPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton,
								deleteButton, outputLabel);
						vendorTable.getSelectionModel().clearSelection();
						outputLabel.setText(defaultPrompt);
					}
				}
			};
			vendorButton.setOnAction(vendorButtonEvent);

			// insert rental button event
			EventHandler<ActionEvent> rentalInsertButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Customer customerToInsert = rentalCustomerBox.getSelectionModel().getSelectedItem();
					Item itemToInsert = rentalItemBox.getSelectionModel().getSelectedItem();
					if (itemToInsert.getStocked() == true) {
						int customerToInsertID = customerToInsert.getCustomerID();
						int itemIDToInsert = itemToInsert.getItemID();
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						Rental tempRental = new Rental(0, "null", "null", "null", "null", currentTime, false,
								customerToInsertID, itemIDToInsert);
						if (rentalDAO.insertRental(tempRental) == false) {
							outputLabel.setText("Could not insert Rental.");
						} else {
							outputLabel.setText("New Rental Created");
							// refresh rental lists
							rentalList.clear();
							rentalObsList.clear();
							ArrayList<Rental> rentalList = (ArrayList<Rental>) rentalDAO.selectAllRentals();
							for (Rental aRental : rentalList) {
								rentalObsList.add(aRental);
							}
							// refresh item lists
							itemList.clear();
							itemObsList.clear();
							ArrayList<Item> itemList = (ArrayList<Item>) itemDAO.selectAllItems();
							for (Item aItem : itemList) {
								itemObsList.add(aItem);
							}
						}
					} else {
						outputLabel.setText(
								"Could not insert Rental.\nPlease make sure item is not already being rented.");
					}
				}
			};
			rentalInsertButton.setOnAction(rentalInsertButtonEvent);

			// insert vendor action event
			EventHandler<ActionEvent> insertVendorActionEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Vendor vendorToInsert = new Vendor(0,"Wiha","red rd","Oviedo","FL","google.com",(double) 810290);
					if(vendorDAO.insertVendor(vendorToInsert) == true) {
						outputLabel.setText("Vendor inserted");
					} else {outputLabel.setText("Could not insert Vendor");}
				}
			};
			vendorInsertButton.setOnAction(insertVendorActionEvent);
			
			// delete button event
			EventHandler<ActionEvent> deleteButtonEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// delete from rental table
					if (primaryStage.getScene() == rentalScene
							&& rentalTable.getSelectionModel().getSelectedItem() != null) {
						Rental rentalToDelete = rentalTable.getSelectionModel().getSelectedItem();
						if (rentalDAO.deleteRental(rentalToDelete.getRentalID()) == false) {
							outputLabel.setText("Could not delete Rental");
						} else {
							rentalTable.getItems().removeAll(rentalToDelete);
							outputLabel.setText("Rental Deleted");
						}
					}
					// delete from customer table
					if (primaryStage.getScene() == customerScene
							&& customerTable.getSelectionModel().getSelectedItem() != null) {
						Customer customerToDelete = customerTable.getSelectionModel().getSelectedItem();
						if (customerDAO.deleteCustomer(customerToDelete.getCustomerID()) == false) {
							outputLabel.setText(
									"Could not delete customer.\nPlease make sure to delete all rentals with this customer first.");
						} else {
							customerTable.getItems().removeAll(customerToDelete);
							outputLabel.setText("Customer Deleted");
						}
					}
					// delete from item table
					if (primaryStage.getScene() == itemScene
							&& itemTable.getSelectionModel().getSelectedItem() != null) {
						Item itemToDelete = itemTable.getSelectionModel().getSelectedItem();
						if (itemDAO.deleteItem(itemToDelete.getItemID()) == false) {
							outputLabel.setText(
									"Could not delete item.\nPlease make sure to delete all rentals with this item first.");
						} else {
							itemTable.getItems().removeAll(itemToDelete);
							outputLabel.setText("Item Deleted");
						}
					}
					// delete from vendor table
					if (primaryStage.getScene() == vendorScene
							&& vendorTable.getSelectionModel().getSelectedItem() != null) {
						Vendor vendorToDelete = vendorTable.getSelectionModel().getSelectedItem();
						if (vendorDAO.deleteVendor(vendorToDelete.getVendorID()) == false) {
							outputLabel.setText(
									"Could not delete vendor.\nPlease make sure to delete all inventory items with this vendor first.");
						} else {
							vendorTable.getItems().removeAll(vendorToDelete);
							outputLabel.setText("Vendor Deleted");
						}
					}
				}
			};
			deleteButton.setOnAction(deleteButtonEvent);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);

	}
}
