/*
 * Apex Rental Software 
 * Author: Adam Blaisdell
 * Last Edit: 10/15/2021
 */

package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
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
		
		double tablex = 190;
		double tabley = 15;
		double tablew = 640;
		double tableh = 370;
		
		try {
			
			// set the title
			primaryStage.setTitle("Apex Rental Software");

			// create panes
			Pane rentalPane = new Pane();
			Pane customerPane = new Pane();
			Pane itemPane = new Pane();
			Pane vendorPane = new Pane();
			
			// create scenes
			Scene rentalScene = new Scene(rentalPane,850,600);
			Scene customerScene = new Scene(customerPane,850,600);	
			Scene itemScene = new Scene(itemPane,850,600);	
			Scene vendorScene = new Scene(vendorPane,850,600);	
			
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
			Button rentalButton= new Button("Rentals");
			Button customerButton = new Button("Customers");
			Button itemButton = new Button("Inventory");
			Button vendorButton = new Button("Vendors");
			
			// button layout
			rentalButton.setLayoutX(30);
			rentalButton.setLayoutY(40);
			customerButton.setLayoutX(30);
			customerButton.setLayoutY(80);
			itemButton.setLayoutX(30);
			itemButton.setLayoutY(120);
			vendorButton.setLayoutX(30);
			vendorButton.setLayoutY(160);
			
		    // BUTTON ACTION EVENTS
			// rental button event
		    EventHandler<ActionEvent> rentalButtonEvent = new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent e)
		        {
		        	if (primaryStage.getScene() != rentalScene) {
		            primaryStage.setScene(rentalScene);
					rentalPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton); }
		        }
		    };
		    rentalButton.setOnAction(rentalButtonEvent);  
		    
		    // customer button event
		    EventHandler<ActionEvent> customerButtonEvent = new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent e)
		        {
		        	if (primaryStage.getScene() != customerScene) {
		            primaryStage.setScene(customerScene);
					customerPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton); }
		        }
		    };	        
		    customerButton.setOnAction(customerButtonEvent);  
		    // item button event
		    EventHandler<ActionEvent> itemButtonEvent = new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent e)
		        {
		        	if (primaryStage.getScene() != itemScene) {
		            primaryStage.setScene(itemScene);
					itemPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton); }
		        }
		    };	        
		    itemButton.setOnAction(itemButtonEvent);  
		    // vendor button event
		    EventHandler<ActionEvent> vendorButtonEvent = new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent e)
		        {
		        	if (primaryStage.getScene() != vendorScene) {
		            primaryStage.setScene(vendorScene);
					vendorPane.getChildren().addAll(rentalButton, customerButton, itemButton, vendorButton); }
		        }
		    };	        
		    vendorButton.setOnAction(vendorButtonEvent);  
		    
		    //RENTAL TABLE
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
	        rentalTable.getColumns().addAll(rentalIDColumn, rentalCustomerNameColumn,
	        		rentalVendorNameColumn, rentalItemNameColumn, rentalSerialColumn, rentalDateColumn, rentalReturnedColumn);
	        rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        // create array list of rentals from DAO
			ArrayList<Rental> rentalList = (ArrayList<Rental>)rentalDAO.selectAllRentals();
	        // create rental observable list for table
	        ObservableList<Rental> rentalObsList = FXCollections.observableArrayList();
	        // add rentals to observable list from item array list
	        for(Rental aRental : rentalList) {
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
	        TableColumn<Customer, Number> customerPhoneColumn = new TableColumn<>("Phone #");
	        customerPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());	    
	        // add columns to table
	        customerTable.getColumns().addAll(customerIDColumn, customerNameColumn, customerAddressColumn, customerCityColumn, customerStateColumn, customerPhoneColumn);
	        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        // create array list of customers from DAO
			ArrayList<Customer> customerList = (ArrayList<Customer>)customerDAO.selectAllCustomers();
	        // create customer observable list for table
	        ObservableList<Customer> customerObsList = FXCollections.observableArrayList();
	        // add customers to observable list from item array list
	        for(Customer aCustomer : customerList) {
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
		    itemTable.getColumns().addAll(itemIDColumn, itemVendorNameColumn, itemNameColumn, serialColumn, stockedColumn, costColumn);
		    itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of items from DAO
			ArrayList<Item> itemList = (ArrayList<Item>)itemDAO.selectAllItems();
		    // create item observable list for table
		    ObservableList<Item> itemObsList = FXCollections.observableArrayList();
		    // add items to observable list from item array list
		    for(Item aItem : itemList) {
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
		    TableColumn<Vendor, Number> vendorPhoneColumn = new TableColumn<>("Phone #");
		    vendorPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		    // add columns to table
		    vendorTable.getColumns().addAll(vendorIDColumn, vendorNameColumn, vendorAddressColumn, vendorCityColumn, vendorStateColumn, vendorWebsiteColumn, vendorPhoneColumn);
		    vendorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			// create array list of vendors from DAO
			ArrayList<Vendor> vendorList = (ArrayList<Vendor>)vendorDAO.selectAllVendors();
		    // create vendor observable list for table
		    ObservableList<Vendor> vendorObsList = FXCollections.observableArrayList();
		    // add vendors to observable list from vendor array list
		    for(Vendor aVendor : vendorList) {
		    	vendorObsList.add(aVendor);
		    }	        
		    // populate table with observable list
		    vendorTable.setItems(vendorObsList);
		    // table layout
		    vendorTable.setLayoutX(tablex);
		    vendorTable.setLayoutY(tabley);
		    vendorTable.setPrefWidth(tablew);
		    vendorTable.setPrefHeight(tableh);

			// add tables to panes
			rentalPane.getChildren().addAll(rentalTable, rentalButton, customerButton, itemButton, vendorButton);
			customerPane.getChildren().add(customerTable);
			itemPane.getChildren().add(itemTable);
			vendorPane.getChildren().add(vendorTable);
			
			// add css to scenes
			rentalScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			customerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			itemScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			vendorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// set scene and show stage
			primaryStage.setScene(rentalScene);
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {	
		launch(args);
		
	}
}
