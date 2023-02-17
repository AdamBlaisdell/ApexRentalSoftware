package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class NavigationController {
	
	@FXML private Button rentalSceneButton;
	@FXML private Button customerSceneButton;
	@FXML private Button itemSceneButton;
	@FXML private Button vendorSceneButton;

	public void rentalSceneButtonEvent() throws Exception {
		Scene scene = rentalSceneButton.getScene();
		Pane center = FXMLLoader.load(getClass().getResource("/view/rentalScene.fxml"));
		Parent root = scene.getRoot();
		((BorderPane) root).setCenter(center);
		rentalSceneButton.setId("activeButton");
		customerSceneButton.setId(null);
		itemSceneButton.setId(null);
		vendorSceneButton.setId(null);
	}

	public void customerSceneButtonEvent() throws Exception {
		Scene scene = customerSceneButton.getScene();
		Pane center = FXMLLoader.load(getClass().getResource("/view/customerScene.fxml"));
		Parent root = scene.getRoot();
		((BorderPane) root).setCenter(center);
		rentalSceneButton.setId(null);
		customerSceneButton.setId("activeButton");
		itemSceneButton.setId(null);
		vendorSceneButton.setId(null);
	}

	public void itemSceneButtonEvent() throws Exception {
		Scene scene = itemSceneButton.getScene();
		Pane center = FXMLLoader.load(getClass().getResource("/view/itemScene.fxml"));
		Parent root = scene.getRoot();
		((BorderPane) root).setCenter(center);
		rentalSceneButton.setId(null);
		customerSceneButton.setId(null);
		itemSceneButton.setId("activeButton");
		vendorSceneButton.setId(null);
	}

	public void vendorSceneButtonEvent() throws Exception {
		Scene scene = vendorSceneButton.getScene();
		Pane center = FXMLLoader.load(getClass().getResource("/view/vendorScene.fxml"));
		Parent root = scene.getRoot();
		((BorderPane) root).setCenter(center);
		rentalSceneButton.setId(null);
		customerSceneButton.setId(null);
		itemSceneButton.setId(null);
		vendorSceneButton.setId("activeButton");
	}

}
