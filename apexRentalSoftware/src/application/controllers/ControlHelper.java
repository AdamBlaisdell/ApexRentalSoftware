package application.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControlHelper {

	// checks if string is only made of spaces
	public static boolean isSpaces(String stringToCheck) {
		if( stringToCheck == null) {
			return true;
		}
		if (stringToCheck.replaceAll("\\s", "") == "") {
			return true;
		} else {
			return false;
		}
	}

	// clear all fields and boxes related to a tableview
	@SuppressWarnings("unchecked")
	public static void clearInput(TableView<?> table) {
		for (Node node : table.getParent().getParent().getChildrenUnmodifiable()) {
			for (Node innerNode : ((Parent) node).getChildrenUnmodifiable()) {
				if (innerNode instanceof TextField) {
					((TextField) innerNode).clear();
				}

				if (innerNode instanceof ComboBox) {
					((ComboBox<Object>) innerNode).valueProperty().set(null);
					((ComboBox<Object>) innerNode).valueProperty().setValue(null);
				}
			}
		}
	}
}
