/*
 * Apex Rental Software 
 * Author: Adam Blaisdell
 * Last Edit: 11/12/2022
 */

package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Main extends Application {

	@SuppressWarnings("unchecked")
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
