/*
 * Apex Rental Software 
 * Author: Adam Blaisdell
 * Last Edit: 11/12/2022
 */

package application;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/root.fxml"));
		AnchorPane navigation = FXMLLoader.load(getClass().getResource("/view/navigation.fxml"));
		Pane center = FXMLLoader.load(getClass().getResource("/view/rentalScene.fxml"));
		
		primaryStage.setTitle("Apex Rental Software");
		primaryStage.setScene(new Scene(root, 1100,670));
		
		root.setLeft(navigation);
		root.setCenter(center);
		
		primaryStage.show();
		root.getStylesheets().add(getClass().getResource("/view/styleSheet.css").toExternalForm());
	}
	
	public static void main(String[] args) {
		launch(args);
		}
	
}
