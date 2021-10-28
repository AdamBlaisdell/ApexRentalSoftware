package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;

// Item object class with javafx properties
public class Item {

	// attributes
	private final IntegerProperty itemID;
	private final IntegerProperty vendorID;
	private final StringProperty vendorName;
	private final StringProperty name;
	private final StringProperty serial;
	private final BooleanProperty stocked;
	private final DoubleProperty cost;

	// ctor
	public Item(Integer itemID, Integer vendorID, String vendorName, String name, String serial, Boolean stocked, Double cost) {
		this.itemID = new SimpleIntegerProperty(itemID);
		this.vendorID = new SimpleIntegerProperty(vendorID);
		this.vendorName = new SimpleStringProperty(vendorName);
		this.name = new SimpleStringProperty(name);
		this.serial = new SimpleStringProperty(serial);
		this.stocked = new SimpleBooleanProperty(stocked);
		this.cost = new SimpleDoubleProperty(cost);
	}

	// getters, setters, and properties
	public Integer getItemID() {
		return this.itemID.get();
	}

	public void setItemID(Integer itemID) {
		this.itemID.set(itemID);
	}

	public IntegerProperty itemIDProperty() {
		return this.itemID;
	}
	
	public Integer getVendorID() {
		return this.vendorID.get();
	}

	public void setVendorID(Integer vendorID) {
		this.vendorID.set(vendorID);
	}

	public IntegerProperty vendorIDProperty() {
		return this.vendorID;
	}

	public String getVendorName() {
		return this.vendorName.get();
	}

	public void setVendorName(String vendorName) {
		this.vendorName.set(vendorName);
	}

	public StringProperty vendorNameProperty() {
		return this.vendorName;
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getSerial() {
		return this.serial.get();
	}

	public void setSerial(String serial) {
		this.serial.set(serial);
	}

	public StringProperty serialProperty() {
		return this.serial;
	}

	public Boolean getStocked() {
		return this.stocked.get();
	}

	public void setStocked(Boolean stocked) {
		this.stocked.set(stocked);
	}

	public BooleanProperty stockedProperty() {
		return this.stocked;
	}

	public Double getCost() {
		return this.cost.get();
	}

	public void setCost(Double cost) {
		this.cost.set(cost);
	}

	public DoubleProperty costProperty() {
		return this.cost;
	}

	@Override
	public String toString() {
		return this.itemID.get() + " " + this.vendorName.get() + " " + this.name.get() + " " + this.serial.get();
	}

}
