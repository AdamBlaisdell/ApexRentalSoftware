package application.dao.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


//Rental object class with javafx properties
public class State {

	// attributes
	private final IntegerProperty stateID;
	private final StringProperty stateCode;
	private final StringProperty stateName;

	// ctor
	public State(Integer stateID, String stateCode, String stateName) {	
		this.stateID = new SimpleIntegerProperty(stateID);
		this.stateCode = new SimpleStringProperty(stateCode);
		this.stateName = new SimpleStringProperty(stateName);
	}

	public final IntegerProperty stateIDProperty() {
		return this.stateID;
	}

	public final int getStateID() {
		return this.stateIDProperty().get();
	}

	public final void setStateID(final int stateID) {
		this.stateIDProperty().set(stateID);
	}

	public final StringProperty stateCodeProperty() {
		return this.stateCode;
	}

	public final String getStateCode() {
		return this.stateCodeProperty().get();
	}

	public final void setStateCode(final String stateCode) {
		this.stateCodeProperty().set(stateCode);
	}
	
	public final StringProperty stateNameProperty() {
		return this.stateName;
	}

	public final String getStateName() {
		return this.stateNameProperty().get();
	}

	public final void setStateName(final String stateName) {
		this.stateNameProperty().set(stateName);
	}

	@Override
	public String toString() {
		return this.getStateName();
	}

}
