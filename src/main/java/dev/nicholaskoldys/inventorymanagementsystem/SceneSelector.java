package dev.nicholaskoldys.inventorymanagementsystem;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



/*
*	Class created to be the foucs of all scene changes through the controller classes.
*/

public class SceneSelector {
	
	private static Stage popOutAlertWindow;
	
	//event call from each controller will go through here for each button event
	//however not every button press leads to replacing/loading a new scene. 
	public static void loadScene(String sceneFileName) throws IOException {
		
		//Find the caller methods name as this is a static method.
		Parent root = FXMLLoader.load
			(MethodHandles.lookup().lookupClass().getResource
			(selectScene(sceneFileName)));

		Scene scene = new Scene(root);
				
		Stage mainWindow = InventoryManagementSystem.getStage();
		mainWindow.setScene(scene);
		mainWindow.show();
	}

	public static FXMLLoader loadScene(String sceneFileName, Boolean isLoaderReturned) throws IOException {

		FXMLLoader loader = new FXMLLoader(MethodHandles.lookup().lookupClass().getResource
				(selectScene(sceneFileName)));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage mainWindow = InventoryManagementSystem.getStage();
		mainWindow.setScene(scene);
		mainWindow.show();
		return loader;
	}
	
	//alternate method to load a scene
	public static void loadScene(Parent root) throws IOException {
		
		Scene scene = new Scene(root);
		
		scene.setRoot(root);
		
		Stage mainWindow = InventoryManagementSystem.getStage();
		mainWindow.setScene(scene);
		mainWindow.show();
	}
	
	//Close the Application Window - Stage
	public static void closeMainStage() {
		
		Stage mainWindow = InventoryManagementSystem.getStage();
		mainWindow.close();
	}
	
	/*
	*	Loads the Alert dialog windows.
	*	Controls the content text by the textField passed.
	*/
	
	public static void alertPopup(TextField textFieldWithError) {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Incorrect Input");
		textFieldWithError.setStyle("-fx-background-color: #ff9999;");	
		
		String checkId = textFieldWithError.getId();
		System.out.println(checkId);
		switch (checkId) {
			case "priceField":
				alert.setContentText
		("Please change the input of Price field to a numerical value.");
				break;
			case "invField":
				alert.setContentText
		("Please change the input of Inventory field to a numerical value.");
				break;
			case "minField":
				alert.setContentText
		("Please change the input of Min. field to a numerical value.");
				break;
			case "maxField":
				alert.setContentText
		("Please change the input of Max. field to a numerical value.");
				break;
			case "partNameField":
				alert.setContentText
		("Please change the input of Part Name field to a proper name.");
				break;
			case "productNameField":
				alert.setContentText
		("Please change the input of Product Name field to a proper name.");
				break;
		}
		
		alert.showAndWait();
	}
	
	/*
	*	Specific Alert Dialog window for Max, inv, and min disparity.
	*/
	
	public static void alertPopup(int min, int max, int inv) {		
		
		String inputString;
		
		inputString = max + " > " + inv + " > " + min;
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Incorrect Input");
		
		alert.setContentText
		("Please change the input of Inventory, Min, or Max field(s) to a correct value.\n"
		+ "(Example: Max > Inv > Min >= 0)\n\n" + "Your Input: " + inputString);
		alert.showAndWait();
	}
	
	/*
	*	Specific Alert Dialog window for price and addedPriceOfAllParts.
	*/
	
	public static void alertPopup(double price, double comparePrice) {
		
		String inputString;
		
		inputString = "$" + price + " > "+ "$" + comparePrice;
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Incorrect Input");
		alert.setContentText("Please change the input of the Price field to a correct value.\n" + "(Example: ProductPrice > Accumulated Price of its Consisting Parts >= 0)\n\n" + "Your input: " + inputString);
		alert.showAndWait();
	}

	//Match filename with button's fx:id on the fxml document
	private static String selectScene(String buttonId) {
		
		String fileName = "";
		
		if (buttonId.equals("partsAddButton")) {
			fileName = "view/PartAddScreen.fxml";
		}
		
		if (buttonId.equals("productsAddButton")) {
			fileName = "view/ProductAddScreen.fxml";
		}		
		
		if (buttonId.equals("saveButton")) {
			fileName = "view/MainScreen.fxml";
		}
		
		if (buttonId.equals("cancelButton")) {
			fileName = "view/MainScreen.fxml";
		}
		
		if (buttonId.equals("partsModifyButton")) {
			fileName = "view/PartAddScreen.fxml";
		}
		
		if (buttonId.equals("productsModifyButton")) {
			fileName = "view/ProductAddScreen.fxml";
		}
		
		return fileName;
	}
}
