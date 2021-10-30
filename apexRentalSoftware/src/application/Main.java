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
		// tableview location and size
		int tablex = 190;
		int tabley = 15;
		int tablew = 640;
		int tableh = 370;
		// nav button location (x) 
		int navx= 30;
		// text box location (y)
		int line1 = 430;
		int line2 = 460;
		// default prompt text 
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
			StateDAO stateDAO = new StateDAO();

			// create navigation buttons
			Button rentalButton = new Button("Rentals");
			Button customerButton = new Button("Customers");
			Button itemButton = new Button("Inventory");
			Button vendorButton = new Button("Vendors");
			// layout	
			rentalButton.setLayoutX(navx);
			rentalButton.setLayoutY(40);
			customerButton.setLayoutX(navx);
			customerButton.setLayoutY(80);
			itemButton.setLayoutX(navx);
			itemButton.setLayoutY(120);	
			vendorButton.setLayoutX(navx);
			
			// create universal elements
			Button deleteButton = new Button("Delete");
			Label outputLabel = new Label(defaultPrompt);
			outputLabel.setId("outputLabel");
			// layout
			deleteButton.setLayoutX(190);
			deleteButton.setLayoutY(390);
			outputLabel.setLayoutX(300);
			outputLabel.setLayoutY(520);	
			vendorButton.setLayoutY(160);

			// create Rental scene elements		
			Button rentalInsertButton = new Button("Insert");	
			Button rentalReturnButton = new Button("Return");
			ComboBox<Customer> rentalCustomerBox = new ComboBox<>();
			rentalCustomerBox.setPromptText("Customer");
			ComboBox<Item> rentalItemBox = new ComboBox<>();
			rentalItemBox.setPromptText("Item");
			// layout
			rentalInsertButton.setLayoutX(600);
			rentalInsertButton.setLayoutY(line1);
			rentalReturnButton.setLayoutX(315);
			rentalReturnButton.setLayoutY(390);
			rentalCustomerBox.setLayoutX(190);
			rentalCustomerBox.setLayoutY(line1);
			rentalItemBox.setLayoutX(395);
			rentalItemBox.setLayoutY(line1);
			
			// create  Customer scene elements
			Button customerInsertButton = new Button("Insert");			
			TextField customerNameField = new TextField();
			customerNameField.setPromptText("Name");
			TextField customerAddressField = new TextField();
			customerAddressField.setPromptText("Address");
			TextField customerCityField = new TextField();
			customerCityField.setPromptText("City");
			TextField customerPhoneField = new TextField();
			customerPhoneField.setPromptText("Phone");
			ComboBox<State> customerStateBox = new ComboBox<>();
			customerStateBox.setPromptText("State");		
			// layout
			customerInsertButton.setLayoutX(345);
			customerInsertButton.setLayoutY(line2);
			customerStateBox.setMaxWidth(130);
			customerNameField.setLayoutX(190);
			customerNameField.setLayoutY(line1);
			customerAddressField.setLayoutX(340);
			customerAddressField.setLayoutY(line1);
			customerCityField.setLayoutX(490);
			customerCityField.setLayoutY(line1);
			customerStateBox.setLayoutX(640);
			customerStateBox.setLayoutY(line1);
			customerPhoneField.setLayoutX(190);
			customerPhoneField.setLayoutY(line2);
			
			// create Item scene elements
			Button itemInsertButton = new Button("Insert");
			TextField itemNameField = new TextField();
			itemNameField.setPromptText("Name");
			TextField itemSerialField = new TextField();
			itemSerialField.setPromptText("Serial Number");
			TextField itemCostField = new TextField();
			itemCostField.setPromptText("Cost");
			ComboBox<Vendor> itemVendorBox = new ComboBox<>();
			itemVendorBox.setPromptText("Vendor");
			// layout
			itemInsertButton.setLayoutX(675);
			itemInsertButton.setLayoutY(line1);
			itemNameField.setLayoutX(315);
			itemNameField.setLayoutY(line1);
			itemNameField.setMaxWidth(100);
			itemSerialField.setLayoutX(420);
			itemSerialField.setLayoutY(line1);
			itemCostField.setLayoutX(570);
			itemCostField.setLayoutY(line1);
			itemCostField.setMaxWidth(100);
			itemVendorBox.setLayoutX(190);
			itemVendorBox.setLayoutY(line1);
			itemVendorBox.setMaxWidth(120);
			
			// create Vendor scene elements
			Button vendorInsertButton = new Button("Insert");			
			TextField vendorNameField = new TextField();
			vendorNameField.setPromptText("Name");
			TextField vendorAddressField = new TextField();
			vendorAddressField.setPromptText("Address");
			TextField vendorCityField = new TextField();
			vendorCityField.setPromptText("City");			
			ComboBox<State> vendorStateBox = new ComboBox<>();
			vendorStateBox.setPromptText("State");
			TextField vendorWebsiteField = new TextField();
			vendorWebsiteField.setPromptText("Website");
			TextField vendorPhoneField = new TextField();
			vendorPhoneField.setPromptText("Phone");
			// layout
			vendorInsertButton.setLayoutX(495);
			vendorInsertButton.setLayoutY(line2);
			vendorNameField.setLayoutX(190);
			vendorNameField.setLayoutY(line1);
			vendorAddressField.setLayoutX(340);
			vendorAddressField.setLayoutY(line1);
			vendorCityField.setLayoutX(490);
			vendorCityField.setLayoutY(line1);	
			vendorStateBox.setLayoutX(640);
			vendorStateBox.setLayoutY(line1);	
			vendorStateBox.setMaxWidth(130);
			vendorWebsiteField.setLayoutX(190);
			vendorWebsiteField.setLayoutY(line2);
			vendorPhoneField.setLayoutX(340);
			vendorPhoneField.setLayoutY(line2);

			// create tableviews
			TableView<Rental> rentalTable = new TableView<>();
			TableView<Customer> customerTable = new TableView<>();
			TableView<Item> itemTable = new TableView<>();
			TableView<Vendor> vendorTable = new TableView<>();
			
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
			
			// create observable list for state combobox
			// create array list of states from DAO
			ArrayList<State> stateList = (ArrayList<State>) stateDAO.selectAllStates();
			// create state observable list for table
			ObservableList<State> stateObsList = FXCollections.observableArrayList();
			// add states to observable list from item array list
			for (State aState : stateList) {
				stateObsList.add(aState);
			}		
			// populate comboboxes with observable lists
			rentalCustomerBox.setItems(customerObsList);
			rentalItemBox.setItems(itemObsList);
			itemVendorBox.setItems(vendorObsList);
			customerStateBox.setItems(stateObsList);
			vendorStateBox.setItems(stateObsList);

			// add tables and elements to panes
			rentalPane.getChildren().addAll(rentalTable, rentalButton, customerButton, itemButton, vendorButton,
					deleteButton, outputLabel, rentalInsertButton, rentalCustomerBox, rentalItemBox,
					rentalReturnButton);
			customerPane.getChildren().addAll(customerTable, customerNameField, customerAddressField, customerCityField,
					customerStateBox, customerPhoneField, customerInsertButton);
			itemPane.getChildren().addAll(itemTable, itemNameField, itemSerialField, itemCostField, itemVendorBox,
					itemInsertButton);
			vendorPane.getChildren().addAll(vendorTable, vendorNameField, vendorAddressField, vendorCityField,
					vendorStateBox, vendorWebsiteField, vendorPhoneField, vendorInsertButton);

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
					if (rentalCustomerBox.getSelectionModel().getSelectedItem() != null
							&& rentalItemBox.getSelectionModel().getSelectedItem() != null) {
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
									"Could not insert Rental\nPlease make sure item is not already being rented");
						}
					} else {
						outputLabel.setText("Please select a customer and item.");
					}
				}
			};
			rentalInsertButton.setOnAction(rentalInsertButtonEvent);

			// return rental button event
			EventHandler<ActionEvent> returnRentalEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
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
							// refresh item lists
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
			};
			rentalReturnButton.setOnAction(returnRentalEvent);
			
			// insert customer action event
			EventHandler<ActionEvent> insertCustomerActionEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if (isSpaces(customerNameField.getText()) == false && customerStateBox.getSelectionModel().getSelectedItem() != null) {
						String customerNameToInsert = customerNameField.getText();
						String customerAddressToInsert = customerAddressField.getText();
						String customerCityToInsert = customerCityField.getText();
						String customerStateToInsert = customerStateBox.getSelectionModel().getSelectedItem().getStateCode();
						double customerPhoneToInsert = 0;
						try {
							customerPhoneToInsert = Double.valueOf(customerPhoneField.getText());
						} catch (Exception ex) {

						}

						Customer customerToInsert = new Customer(0, customerNameToInsert, customerAddressToInsert,
								customerCityToInsert, customerStateToInsert, customerPhoneToInsert);

						if (customerDAO.insertCustomer(customerToInsert) == true) {
							outputLabel.setText("Customer inserted");
							
							// clear text fields
							customerNameField.clear();
							customerAddressField.clear();
							customerCityField.clear();
							customerStateBox.setValue(null);
							customerPhoneField.clear();

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
					} else {
						outputLabel.setText("Could not insert Customer\nInvalid user input");
					}
				}
			};
			customerInsertButton.setOnAction(insertCustomerActionEvent);
			
			// insert item action event
			EventHandler<ActionEvent> insertItemActionEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(isSpaces(itemNameField.getText()) == false && itemVendorBox.getSelectionModel().getSelectedItem() != null) {
					int itemVendorIDToInsert = itemVendorBox.getSelectionModel().getSelectedItem().getVendorID();
					String itemNameToInsert = itemNameField.getText();
					String itemSerialToInsert = itemSerialField.getText();
					double itemCostToInsert = 0;
					try {
					itemCostToInsert = Double.valueOf(itemCostField.getText());
					} catch(Exception ex) {
					}
					Item itemToInsert = new Item(0, itemVendorIDToInsert, "", itemNameToInsert, itemSerialToInsert,
							true, itemCostToInsert);
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
					}else {outputLabel.setText("Invalid input");}
				}
			};
			itemInsertButton.setOnAction(insertItemActionEvent);

			// insert vendor action event
			EventHandler<ActionEvent> insertVendorActionEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(isSpaces(vendorNameField.getText()) == false) {
						String vendorNameToInsert = vendorNameField.getText();
						String vendorAddressToInsert = vendorAddressField.getText();
						String vendorCityToInsert = vendorCityField.getText();
						String vendorStateToInsert = "";
						if (vendorStateBox.getSelectionModel().getSelectedItem() != null){
						vendorStateToInsert = vendorStateBox.getSelectionModel().getSelectedItem().getStateCode();
						}
						String vendorWebsiteToInsert = vendorWebsiteField.getText();
						double vendorPhoneToInsert = 0;
						try {
						vendorPhoneToInsert = Double.valueOf(vendorPhoneField.getText());
						} catch(Exception ex){

						}
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
							outputLabel.setText("Could not insert Vendor");
						}
						} else {outputLabel.setText("Invalid input");}
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
						if (rentalToDelete.isReturned() == true) {
							if (rentalDAO.deleteRental(rentalToDelete) == false) {
								outputLabel.setText("Could not delete Rental");
							} else {
								rentalTable.getItems().removeAll(rentalToDelete);
								outputLabel.setText("Rental Deleted");
							}
						} else {
							outputLabel.setText("Could not Delete Rental, please return rental first");
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
			
		}catch(Exception e)
	{
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static boolean isSpaces(String stringToCheck) {
		if (stringToCheck.replaceAll("\\s", "") == "" || stringToCheck == null) {
			return true;
		} else {
		return false;
		}
	}
}
