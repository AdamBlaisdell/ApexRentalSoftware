package application.dao.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.sql.Timestamp;

//Rental object class with javafx properties
public class Rental {

	// attributes
	private final IntegerProperty rentalID;
	private final StringProperty customerName;
	private final StringProperty vendorName;
	private final StringProperty itemName;
	private final StringProperty serial;
	private final ObjectProperty<Timestamp> date;
	private final BooleanProperty returned;
	private final IntegerProperty customerID;
	private final IntegerProperty itemID;

	// ctor
	public Rental(Integer rentalID, String customerName, String vendorName, String itemName, String serial,
			Timestamp date, Boolean returned, Integer customerID, Integer itemID) {
		this.rentalID = new SimpleIntegerProperty(rentalID);
		this.customerName = new SimpleStringProperty(customerName);
		this.vendorName = new SimpleStringProperty(vendorName);
		this.itemName = new SimpleStringProperty(itemName);
		this.serial = new SimpleStringProperty(serial);
		this.date = new SimpleObjectProperty<Timestamp>(date);
		this.returned = new SimpleBooleanProperty(returned);
		this.customerID = new SimpleIntegerProperty(customerID);
		this.itemID = new SimpleIntegerProperty(itemID);
	}

	public final IntegerProperty rentalIDProperty() {
		return this.rentalID;
	}

	public final int getRentalID() {
		return this.rentalIDProperty().get();
	}

	public final void setRentalID(final int rentalID) {
		this.rentalIDProperty().set(rentalID);
	}

	public final StringProperty customerNameProperty() {
		return this.customerName;
	}

	public final String getCustomerName() {
		return this.customerNameProperty().get();
	}

	public final void setCustomerName(final String customerName) {
		this.customerNameProperty().set(customerName);
	}

	public final StringProperty vendorNameProperty() {
		return this.vendorName;
	}

	public final String getVendorName() {
		return this.vendorNameProperty().get();
	}

	public final void setVendorName(final String vendorName) {
		this.vendorNameProperty().set(vendorName);
	}

	public final StringProperty itemNameProperty() {
		return this.itemName;
	}

	public final String getItemName() {
		return this.itemNameProperty().get();
	}

	public final void setItemName(final String itemName) {
		this.itemNameProperty().set(itemName);
	}

	public final StringProperty serialProperty() {
		return this.serial;
	}

	public final String getSerial() {
		return this.serialProperty().get();
	}

	public final void setSerial(final String serial) {
		this.serialProperty().set(serial);
	}

	public final ObjectProperty<Timestamp> dateProperty() {
		return this.date;
	}

	public final Timestamp getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final Timestamp date) {
		this.dateProperty().set(date);
	}

	public final BooleanProperty returnedProperty() {
		return this.returned;
	}

	public final boolean isReturned() {
		return this.returnedProperty().get();
	}

	public final void setReturned(final boolean returned) {
		this.returnedProperty().set(returned);
	}

	public final IntegerProperty customerIDProperty() {
		return this.customerID;
	}

	public final int getCustomerID() {
		return this.customerIDProperty().get();
	}

	public final void setCustomerID(final int customerID) {
		this.customerIDProperty().set(customerID);
	}

	public final IntegerProperty itemIDProperty() {
		return this.itemID;
	}

	public final int getItemID() {
		return this.itemIDProperty().get();
	}

	public final void setItemID(final int itemID) {
		this.itemIDProperty().set(itemID);
	}

}
