package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Customer object class with javafx properties
public class Customer {

	// attributes
	private final IntegerProperty customerID;
	private final StringProperty name;
	private final StringProperty address;
	private final StringProperty city;
	private final StringProperty state;
	private final DoubleProperty phone;

	// ctor
	public Customer(Integer customerID, String name, String address, String city, String state, Double phone) {
		this.customerID = new SimpleIntegerProperty(customerID);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.phone = new SimpleDoubleProperty(phone);
	}

	// getters, setters, and properties
	public final int getCustomerID() {
		return this.customerIDProperty().get();
	}

	public final void setCustomerID(final int customerID) {
		this.customerIDProperty().set(customerID);
	}

	public final IntegerProperty customerIDProperty() {
		return this.customerID;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getAddress() {
		return this.addressProperty().get();
	}

	public final void setAddress(final String address) {
		this.addressProperty().set(address);
	}

	public final StringProperty addressProperty() {
		return this.address;
	}

	public final String getCity() {
		return this.cityProperty().get();
	}

	public final void setCity(final String city) {
		this.cityProperty().set(city);
	}

	public final StringProperty cityProperty() {
		return this.city;
	}

	public final String getState() {
		return this.stateProperty().get();
	}

	public final void setState(final String state) {
		this.stateProperty().set(state);
	}

	public final StringProperty stateProperty() {
		return this.state;
	}

	public final double getPhone() {
		return this.phoneProperty().get();
	}

	public final void setPhone(final double phone) {
		this.phoneProperty().set(phone);
	}

	public final DoubleProperty phoneProperty() {
		return this.phone;
	}

	@Override
	public String toString() {
		return this.customerID.get() + " " + this.name.get();
	}

}
