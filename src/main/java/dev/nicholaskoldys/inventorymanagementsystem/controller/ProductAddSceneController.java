package dev.nicholaskoldys.inventorymanagementsystem.controller;

import dev.nicholaskoldys.inventorymanagementsystem.InventoryManagementSystem;
import dev.nicholaskoldys.inventorymanagementsystem.SceneSelector;
import dev.nicholaskoldys.inventorymanagementsystem.model.Inventory;
import dev.nicholaskoldys.inventorymanagementsystem.model.Part;
import dev.nicholaskoldys.inventorymanagementsystem.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*; // contains button and label
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductAddSceneController implements Initializable {
	
	@FXML private Label titleLabel;
	
	@FXML private TextField partsSearchTextField;
	private ObservableList<Part> dummyList = FXCollections.observableArrayList();
	
	@FXML private TextField idField;
	@FXML private TextField productNameField;
	@FXML private TextField invField;
	@FXML private TextField priceField;
	@FXML private TextField maxField;
	@FXML private TextField minField;
	
	@FXML private TableView<Part> partSearchProductTable;
	@FXML private TableView<Part> partListProductTable;
	
	@FXML private TableColumn<Part, Integer> partSearchIdCol;
	@FXML private TableColumn<Part, String> partSearchNameCol;
	@FXML private TableColumn<Part, Integer> partSearchInvCol;
	@FXML private TableColumn<Part, Double> partSearchPriceCol;
	
	@FXML private TableColumn<Part, Integer> partListIdCol;
	@FXML private TableColumn<Part, String> partListNameCol;
	@FXML private TableColumn<Part, Integer> partListInvCol;
	@FXML private TableColumn<Part, Double> partListPriceCol;
	
	@FXML private Button searchButton;
	@FXML private Button addButton;
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	private Product modifiedProduct;

	
	/*
	*	function called by the mainSceneController class to load the appropriate
	*	product when modifyButton is selected.
	*/
	
	public void setProduct(Product selectedProduct) {
		
		this.modifiedProduct = selectedProduct;

		idField.setText(Integer.toString(modifiedProduct.getId()));
		productNameField.setText(modifiedProduct.getName());
		priceField.setText(Double.toString(modifiedProduct.getPrice()));
		invField.setText(Integer.toString(modifiedProduct.getStock()));
		maxField.setText(Integer.toString(modifiedProduct.getMax()));
		minField.setText(Integer.toString(modifiedProduct.getMin()));
				
		dummyList = modifiedProduct.getAllAssociatedParts();
		partListProductTable.setItems(dummyList);
	}
	
	/*
	*	Load parts to list.
	*/
	
	@FXML
	private void searchButtonAction(ActionEvent clickEvent) {
		
		String searchParameter = partsSearchTextField.getText().trim();
		Scanner scanner = new Scanner(searchParameter);
		
		if (scanner.hasNextInt()) {
			
			Part part = Inventory.lookupPart(Integer.parseInt(searchParameter));
			
			if (part != null) {
				
				partSearchProductTable.setItems(
					Inventory.lookupPart(part.getName().trim().toLowerCase()));
			}
		} else {
			
			partSearchProductTable.setItems(
					Inventory.lookupPart(searchParameter.trim().toLowerCase()));
		}
	}
	
	/*
	*	clears TextField when it is clicked.
	*/
	
	@FXML
	private void fieldSelectedAction(MouseEvent clickEvent) {		
		
		((TextField)clickEvent.getSource()).clear();
	}
	
	/*
	*	Add a part pulled from a searchTable to the specific products table that matches
	*	with the productsPartList - only manipulates the dummyList.
	*/
	
	@FXML
	private void addButtonAction(ActionEvent clickEvent) {
		
		Part addPartToProduct = partSearchProductTable.getSelectionModel().getSelectedItem();
		
		if (addPartToProduct != null) {
			
			if(dummyList.contains(addPartToProduct) == false) {
				
				dummyList.add(addPartToProduct);
			}
		}
	}
	
	/*
	*	remove the selected part from productsPartList - only manipulates the dummyList.
	*/
	
	@FXML
	private void deleteButtonAction(ActionEvent clickEvent) {
		
		Part deletePartFromProduct = partListProductTable.getSelectionModel().getSelectedItem();
		
		if (deletePartFromProduct != null) {
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pressing OK will remove the part from the product.\n\n" + "Are you sure you want to remove the part?");
		
			Optional<ButtonType> result = alert.showAndWait();
		
			if (result.isPresent() && result.get() == ButtonType.OK) {
			
				dummyList.remove(deletePartFromProduct);

			}
		}
	}
	
	/*
	*	Check each value and applies it to Inventorys.ProductList
	*/
	
	@FXML
	private void saveButtonAction(ActionEvent clickEvent) throws IOException {
		
		int idGen;
		double price;
		int inv, min, max;
		double totalPartsPrice;
		/*
		Added functionality:
		Catch any errors and dont leave the page
		remove all fields or hightlight incorrectly filled fields
		
		*/
		
		defaultStyle();
		
		
		try {
			inv = Integer.parseInt(invField.getText());
		} catch (NumberFormatException ex) {
			
			SceneSelector.alertPopup(invField);
			System.out.println("input:" + invField.getText() + " Inventory: input not a integer");
			System.out.println("Exception: " + ex);
			return;
		}
		
		try {
			price = Double.parseDouble(priceField.getText());
		} catch (NumberFormatException ex) {
			
			SceneSelector.alertPopup(priceField);
			System.out.println("input:" + priceField.getText() + " Price: input not a double");
			System.out.println("Exception: " + ex);
			return;
		}
		
		try {
			min = Integer.parseInt(minField.getText());
		} catch (NumberFormatException ex) {
			
			SceneSelector.alertPopup(minField);
			System.out.println("input:" + minField.getText() + " Min: input not a integer");
			System.out.println("Exception: " + ex);
			return;
		}
		
		try {
			max = Integer.parseInt(maxField.getText());
		} catch (NumberFormatException ex) {
			
			SceneSelector.alertPopup(maxField);
			System.out.println("input:" + maxField.getText() + " Max: input not a integer");
			System.out.println("Exception: " + ex);
			return;
		}
		
		if (inv > max || inv < min || min < 0) {
			
			SceneSelector.alertPopup(min, max, inv);
			System.out.println("input:" + max + " > " + inv + " > " + min + " Inv: value is incorrectly matched between Max. and Min. values and/or min is less than 0");
			return;
		}
		
		totalPartsPrice = 0;
		
		for (Part addedPart : dummyList) {
			
			totalPartsPrice += addedPart.getPrice();
		}
		
		if (price <= totalPartsPrice || totalPartsPrice < 0) {
			
			SceneSelector.alertPopup(price, totalPartsPrice);
			System.out.println("input:" + price + ">" + totalPartsPrice + " Price: Doesn't meet a realistic profitable value.");
			return;
		}
		
		if (productNameField.getText().trim().equals("")) {
			SceneSelector.alertPopup(productNameField);
			System.out.println("input:" + productNameField.getText() + " ProductName: Doesn't meet the proper criteria for a name.");
			return;
		}
		
		if (new Scanner(idField.getText()).hasNextInt()) {
			
			idGen = Integer.parseInt(idField.getText());
			
		} else {
			
			idGen = InventoryManagementSystem.productGenId++;
		}
		
		/*
		After all value checks, finally assign the product to the inventory.
		*/
		
		Product product = new Product(idGen, productNameField.getText(), price, inv, min, max);
		
		for (Part addedPart : dummyList) {
			
			product.addAssociatedPart(addedPart);
		}
		
		if (Inventory.lookupProduct(idGen) == null) {
			
			Inventory.addProduct(product);
			
		} else {
			
			Inventory.updateProduct(product.getId(), product);
		}
		
		//convert event from button to buttonId
		String buttonId = ((Button)clickEvent.getSource()).getId();
		
		SceneSelector.loadScene(buttonId);
	}
	
	/*
	*	Change page to MainScene
	*/
	
	@FXML
	private void cancelButtonAction(ActionEvent clickEvent) throws IOException {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pressing OK will exit to the Main Screen without Saving input.\n\n" + "Are you sure you want to exit?");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK) {
			
			String buttonId = ((Button)clickEvent.getSource()).getId();
			SceneSelector.loadScene(buttonId);
		}
	}
	
	/*
	*	remove styles applied in error checks.
	*/
	
	private void defaultStyle() {
		//idField.setStyle("");
		productNameField.setStyle("");
		invField.setStyle("");
		priceField.setStyle("");
		maxField.setStyle("");
		minField.setStyle("");
	}
	
	/*
	*	Setup Tables to accept pars loaded.
	*/
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		if(!Inventory.getAllParts().isEmpty()) {
			partSearchProductTable.setItems(Inventory.getAllParts());
		}
		
		partSearchIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
		partSearchNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		partSearchInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		partSearchPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		
		partListProductTable.setItems(dummyList);
		
		partListIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
		partListNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		partListInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		partListPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		
		//BEGIN TEMP
		//Temporary due to automatic id creation not implemented
		idField.setDisable(true);
		idField.setStyle("-fx-opacity: 1;"
				+ "-fx-background-color: lightgrey;");
		//END TEMP
	}
}
