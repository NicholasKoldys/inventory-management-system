package dev.nicholaskoldys.inventorymanagementsystem.controller;

import dev.nicholaskoldys.inventorymanagementsystem.model.Inventory;
import dev.nicholaskoldys.inventorymanagementsystem.model.Part;
import dev.nicholaskoldys.inventorymanagementsystem.SceneSelector;
import dev.nicholaskoldys.inventorymanagementsystem.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/* TODO TEMP */
import javafx.scene.*;

import javafx.scene.Parent;
import javafx.scene.control.*; // contains button and label
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainSceneController implements Initializable {
	
	@FXML private Label titleLabel;
	@FXML private Label partsSectionLabel;
	@FXML private Label productsSectionLabel;
	
	@FXML private TableView<Part> partsTable;
	@FXML private TableView<Product> productsTable;
	
	@FXML private TableColumn<Part, Integer> partIdCol;
	@FXML private TableColumn<Part, String> partNameCol;
	@FXML private TableColumn<Part, Integer> partInvCol;
	@FXML private TableColumn<Part, Double> partPriceCol;
	
	@FXML private TableColumn<Part, Integer> productIdCol;
	@FXML private TableColumn<Part, String> productNameCol;
	@FXML private TableColumn<Part, Integer> productInvCol;
	@FXML private TableColumn<Part, Double> productPriceCol;
	
	@FXML private Button partsSearchButton;
	@FXML private Button productsSearchButton;
	@FXML private Button partsAddButton;
	@FXML private Button productsAddButton;
	@FXML private Button partsModifyButton;
	@FXML private Button productsModifyButton;
	@FXML private Button partsDeleteButton;
	@FXML private Button productsDeleteButton;
	@FXML private Button exitButton;
	
	@FXML private TextField partsSearchTextField;
	@FXML private TextField productsSearchTextField;
	
	
	/*
	*	obtains the search parameter from the partSearchTextField.
	*	checks whether it was an int or string that was entered.
	*	then pulls the objects from the Inventory and sets them in the table.
	*/
	
	@FXML
	private void partsSearchButtonAction(ActionEvent clickEvent) {
		
		String searchParameter = partsSearchTextField.getText().trim().toLowerCase();
		Scanner scanner = new Scanner(searchParameter);
		
		if (scanner.hasNextInt()) {
			
			Part part = Inventory.lookupPart(Integer.parseInt(searchParameter));
			if (part != null) {
				partsTable.setItems(Inventory.lookupPart(part.getName().trim().toLowerCase()));
			}
		} else {
			
			partsTable.setItems(Inventory.lookupPart(searchParameter));
		}
	}
	
	/*
	*	obtains the search parameter from the productSearchTextField.
	*	checks whether it was an int or string that was entered.
	*	then pulls the objects from the Inventory and sets them in the table.
	*/
	
	@FXML
	private void productsSearchButtonAction(ActionEvent clickEvent) {
		
		String searchParameter = productsSearchTextField.getText().trim().toLowerCase();
		Scanner scanner = new Scanner(searchParameter);
		
		if (scanner.hasNextInt()) {
			
			Product product = Inventory.lookupProduct(Integer.parseInt(searchParameter));
			if (product != null) {
				productsTable.setItems(Inventory.lookupProduct(product.getName().trim().toLowerCase()));
			}
			
		} else {
			
			productsTable.setItems(Inventory.lookupProduct(searchParameter));
		}
	}
	
	/*
	*	get specific TextField that was clicked,
	*	call objects .clear() to remove default text,
	*	For easy typing purposes - but poses issue when field was accidentally clicked.
	*/
	
	@FXML
	private void fieldSelectedAction(MouseEvent clickEvent) {
		((TextField)clickEvent.getSource()).clear();
	}
	
	/*
	*	sets up the addPart or addProduct scenes.
	*	passes controller information to the SceneSelector class to load the specified scene
	*	calls the specified scenes .setPart() to pass part or product information to the new scene.
	*/
	
	private void setupModifyScene(Object partOrProduct) throws IOException {
		
		Parent root;
		
		if (partOrProduct.getClass().toString().contains("InHouse") 
				|| partOrProduct.getClass().toString().contains("Outsourced")) {

			/*// ! This method is iffy as resource location depends on class location, it is best to call from one method.
			FXMLLoader loader = new FXMLLoader(InventoryManagementSystem.getClass()
				.getResource("view/PartAddScreen.fxml"));
			root = loader.load();
			PartAddSceneController controller = loader.getController();
			controller.setPart((Part) partOrProduct);
			SceneSelector.loadScene(root);*/

			FXMLLoader loader = SceneSelector.loadScene("partsModifyButton", true);
			PartAddSceneController controller = loader.getController();
			controller.setPart((Part) partOrProduct);

		} else if (partOrProduct.getClass().toString().contains("Product")) {

			FXMLLoader loader = SceneSelector.loadScene("productsModifyButton", true);
			ProductAddSceneController controller = loader.getController();
			controller.setProduct((Product) partOrProduct);
		}
		
	}
	
	/*
	*	get the buttonId from the ActionEvent,
	*	and pass the buttonId to .loadScene in the SceneSelector class.
	*	Will load the scene tied to partAddButton.
	*/
	
	@FXML
	private void partsAddButtonAction(ActionEvent clickEvent) throws IOException {
		
		//convert event from button to buttonId
		String buttonId = ((Button)clickEvent.getSource()).getId();
		
		//send button id to SceneSelector class created for launching the scene
		SceneSelector.loadScene(buttonId);
		
	}	
	
	/*
	*	get the buttonId from the ActionEvent,
	*	and pass the buttonId to .loadScene in the SceneSelector class.
	*	will load the scene tied to productAddButton.
	*/
	
	@FXML
	private void productsAddButtonAction(ActionEvent clickEvent) throws IOException {
		
		//convert event from button to buttonId
		String buttonId = ((Button)clickEvent.getSource()).getId();
		
		//send button id to SceneSelector class created for launching the scene
		SceneSelector.loadScene(buttonId);
		
	}
	
	/*
	*	Test the PartsTable to find a selected item,
	*	if true take item and pass it to setupModifyScene().
	*/
	
	@FXML
	private void partsModifyButtonAction(ActionEvent clickEvent) throws IOException {
		
		Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
		
		if (selectedPart != null) {
			
			setupModifyScene(selectedPart);
		}
	}
	
	/*
	*	Test the productsTable to find a selected item,
	*	if true take item and pass it to setupModifyScene().
	*/
	
	@FXML
	private void productsModifyButtonAction(ActionEvent clickEvent) throws IOException {
		
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		
		if (selectedProduct != null) {
			
			setupModifyScene(selectedProduct);
		}
	}
	
	/*
	*	Test the partsTable to find a selected item,
	*	if true take item and call inventory's function .deletePart().
	*/
	
	@FXML
	private void partsDeleteButtonAction(ActionEvent clickEvent) throws IOException {
				
		Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
		
		if (selectedPart != null) {
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pressing OK will DELETE the selected Part.\n\n" + "Are you sure you want to exit?");
		
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				
				Inventory.deletePart(selectedPart);
			}
		}
	}
	
	/*
	*	Test the productsTable to find a selected item,
	*	if true take item and call inventory's function .deletePart().
	*/
	
	@FXML
	private void productsDeleteButtonAction(ActionEvent clickEvent) throws IOException {
		
		Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
		
		if (selectedProduct != null) {
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pressing OK will DELETE the selected Product.\n\n" + "Are you sure you want to exit?");
		
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {

				Inventory.deleteProduct(selectedProduct);
			}
			
		}
	}
	
	/*
	*	Close the stage through .closeStage() in SceneSelector class
	*/
	
	@FXML
	private void exitButtonAction(ActionEvent clickEvent) throws IOException {
		
		SceneSelector.closeMainStage();
	}
	
	/*
	*	Initialize the two tables - partsTable and productsTable
	*	set specified coloumns to call appropriate cell replacement functions.
	*/
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		partsTable.setItems(Inventory.getAllParts());

		partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
		partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		partInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		partPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		
		productsTable.setItems(Inventory.getAllProducts());
		
		productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
		productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		productInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		productPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
	}
}
