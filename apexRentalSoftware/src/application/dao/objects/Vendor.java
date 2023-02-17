package application.dao.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Vendor object class with javafx properties
public class Vendor {

	// attributes
	private final IntegerProperty vendorID;
	private final StringProperty name;
	private final StringProperty address;
	private final StringProperty city;
	private final StringProperty state;
	private final StringProperty website;
	private final StringProperty phone;

	// ctor
	public Vendor(Integer vendorID, String name, String address, String city, String state, String website,
			String phone) {
		this.vendorID = new SimpleIntegerProperty(vendorID);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.website = new SimpleStringProperty(website);
		this.phone = new SimpleStringProperty(phone);
	}

	// getters, setters, and properties
	public final IntegerProperty vendorIDProperty() {
		return this.vendorID;
	}

	public final int getVendorID() {
		return this.vendorIDProperty().get();
	}

	public final void setVendorID(final int vendorID) {
		this.vendorIDProperty().set(vendorID);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty addressProperty() {
		return this.address;
	}

	public final String getAddress() {
		return this.addressProperty().get();
	}

	public final void setAddress(final String address) {
		this.addressProperty().set(address);
	}

	public final StringProperty cityProperty() {
		return this.city;
	}

	public final String getCity() {
		return this.cityProperty().get();
	}

	public final void setCity(final String city) {
		this.cityProperty().set(city);
	}

	public final StringProperty stateProperty() {
		return this.state;
	}

	public final String getState() {
		return this.stateProperty().get();
	}

	public final void setState(final String state) {
		this.stateProperty().set(state);
	}

	public final StringProperty websiteProperty() {
		return this.website;
	}

	public final String getWebsite() {
		return this.websiteProperty().get();
	}

	public final void setWebsite(final String website) {
		this.websiteProperty().set(website);
	}

	public final StringProperty phoneProperty() {
		return this.phone;
	}

	public final String getPhone() {
		return this.phoneProperty().get();
	}

	public final void setPhone(final String phone) {
		this.phoneProperty().set(phone);
	}

	@Override
	public String toString() {
		return this.getVendorID() + " " + this.getName();
	}

}
