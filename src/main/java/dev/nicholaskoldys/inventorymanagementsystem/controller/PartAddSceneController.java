package dev.nicholaskoldys.inventorymanagementsystem.controller;

import dev.nicholaskoldys.inventorymanagementsystem.InventoryManagementSystem;
import dev.nicholaskoldys.inventorymanagementsystem.model.Part;
import dev.nicholaskoldys.inventorymanagementsystem.model.InHouse;
import dev.nicholaskoldys.inventorymanagementsystem.model.Inventory;
import dev.nicholaskoldys.inventorymanagementsystem.model.Outsourced;
import dev.nicholaskoldys.inventorymanagementsystem.SceneSelector;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*; // contains button and label
import javafx.scene.input.MouseEvent;

import java.util.Optional;
import java.util.Scanner;

//import inventorymanagementsystem.Inventory;

public class PartAddSceneController implements Initializable {
	
	@FXML private Label titleLabel;
		
	@FXML private RadioButton inHouseRadio;
	@FXML private RadioButton outsourcedRadio;
	
	@FXML private TextField idField;
	@FXML private TextField partNameField;
	@FXML private TextField invField;
	@FXML private TextField priceField;
	@FXML private TextField maxField;
	@FXML private TextField minField;
	
	@FXML private Label companyNameVsMachineIdLabel;
	@FXML private TextField companyNameVsMachineIdField;
	
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	private Part modifiedPart;
	
	
	/*
	*	Point to the appropriate radio button and calls changeRadioButton().
	*/
	
	@FXML
	private void sourceRadioButtonAction(ActionEvent clickEvent) {
		
		switch (((RadioButton)clickEvent.getSource()).getId()) {
			
			case "inHouseRadio":
				
				changeRadioButton("InHouse");
				break;
			
			case "outsourcedRadio":
				
				changeRadioButton("Outsourced");
				break;
		}
	}
	
	/*
	*	Sets textField and Label text changes after radioButton select.
	*	Function call to lighten copy paste code.
	*/
	
	private void changeRadioButton(String radioButtonDecision) {
		
		switch (radioButtonDecision) {
			
			case "InHouse":
				
				inHouseRadio.setSelected(true);
				companyNameVsMachineIdLabel.setText("Machine ID");
				
				if (companyNameVsMachineIdField.getText() == null 
						|| companyNameVsMachineIdField.getText().trim().isEmpty()
						|| companyNameVsMachineIdField.getText().equals("Comp Nm")) {
					
					companyNameVsMachineIdField.setText("Mach Id");
				}
				break;
				
			case "Outsourced":
				
				outsourcedRadio.setSelected(true);
				companyNameVsMachineIdLabel.setText("Company Name");
				
				if (companyNameVsMachineIdField.getText() == null 
						|| companyNameVsMachineIdField.getText().trim().isEmpty() 
						|| companyNameVsMachineIdField.getText().equals("Mach Id")) {
					
					companyNameVsMachineIdField.setText("Comp Nm");
				}
				break;
		}
	}
	
	/*
	*	function called in the MainSceneController to set the part if modifybutton is pressed.
	*/
	
	public void setPart(Part selectedPart) {
		
		this.modifiedPart = selectedPart;
		
		if (selectedPart instanceof InHouse) {
			
			//Type cast the Part Object into an InHouse Object
			companyNameVsMachineIdField
					.setText(Integer.toString(((InHouse)modifiedPart).getMachineId()));
			changeRadioButton("InHouse");
			
		} else if (selectedPart instanceof Outsourced) {
			
			//Type cast the Part Object into an Outsourced Object
			companyNameVsMachineIdField
					.setText(((Outsourced)modifiedPart).getCompanyName());
			changeRadioButton("Outsourced");
		}
		
		idField.setText(Integer.toString(modifiedPart.getId()));
		partNameField.setText(modifiedPart.getName());
		invField.setText(Integer.toString(modifiedPart.getStock()));
		priceField.setText(Double.toString(modifiedPart.getPrice()));
		maxField.setText(Integer.toString(modifiedPart.getMax()));
		minField.setText(Integer.toString(modifiedPart.getMin()));
	}
	
	/*
	*	Clears the default text in each TextField upon mouseClick.
	*/
	
	@FXML
	private void fieldSelectedAction(MouseEvent clickEvent) {		
		
		((TextField)clickEvent.getSource()).clear();
	}
	
	/*
	*	Before adding the Part to inventory,
	*	read all values from TextFields and attempt to store them in their respective types.
	*	Call Alert for each respective error - following tests.
	*/
	
	@FXML
	private void saveButtonAction(ActionEvent clickEvent) throws IOException {
		
		int idGen;
		double price;
		int inv, min, max, machineId;
		String companyName;
		
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
		
		if (partNameField.getText().trim().equals("")) {
			SceneSelector.alertPopup(partNameField);
			System.out.println("input:" + partNameField.getText() + " PartName: Doesn't meet the proper criteria for a name.");
			return;
		}
		
		/*
		*	After all value checks, find the selected Radio Button and
		*	finally assign the part to the inventory - check if GenId is taken, if so
		*	call Inventory.update function.
		*/
		
		if (inHouseRadio.isSelected()) {
			
			try {
				machineId = Integer.parseInt(companyNameVsMachineIdField.getText());
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Incorrect Input");
				alert.setContentText("Please change the input of Machine ID field to a numerical value.");
				alert.showAndWait();
				companyNameVsMachineIdField.setStyle("-fx-background-color: #ff9999;");	
				System.out.println("input: " + companyNameVsMachineIdField.getText() + " MachineId: input of Machine ID is not a numerical integer");
				System.out.println("Exception: " + ex);
				return;
			}
			
			if (new Scanner(idField.getText()).hasNextInt()) {
			
				idGen = Integer.parseInt(idField.getText());
			
			} else {
			
				idGen = InventoryManagementSystem.partGenId++;
			}
			
			InHouse part = new InHouse(idGen, partNameField.getText(), price, inv, min, max, machineId);
			if (Inventory.lookupPart(idGen) == null) {

				Inventory.addPart(part);

			} else {

				Inventory.updatePart(part.getId(), part);	
			}
			
		} else {
			try {
				companyName = companyNameVsMachineIdField.getText();
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Incorrect Input");
				alert.setContentText("Please change the input of Company Name field to a proper value.");	
				alert.showAndWait();
				companyNameVsMachineIdField.setStyle("-fx-background-color: #ff9999;");
				System.out.println("Exception: " + ex);
				return;
			}
			
			if (new Scanner(idField.getText()).hasNextInt()) {
			
				idGen = Integer.parseInt(idField.getText());
			
			} else {
			
				idGen = InventoryManagementSystem.partGenId++;
			}
			
			Outsourced part = new Outsourced(idGen, partNameField.getText(), price, inv, min, max, companyName);
			
			if (Inventory.lookupPart(idGen) == null) {

				Inventory.addPart(part);

			} else {

				Inventory.updatePart(part.getId(), part);
			}
		}
		
		//convert event from button to buttonId
		String buttonId = ((Button)clickEvent.getSource()).getId();
		
		SceneSelector.loadScene(buttonId);
	}
	
	/*
	*	Before loading mainScene, ask if its ok with Dialog Window.
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
	*	Reset all fields to blank styles
	*/
	
	private void defaultStyle() {
		//idField.setStyle("");
		partNameField.setStyle("");
		invField.setStyle("");
		priceField.setStyle("");
		maxField.setStyle("");
		minField.setStyle("");
	}
	
	/*
	*	Set toggleGroup for radio buttons
	*	Set default radio button
	*	Grey out idField and disable typing to it
	*/
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		//Allow only one radio button to be selected.
		ToggleGroup partSourceToggleGroup = new ToggleGroup();
		inHouseRadio.setToggleGroup(partSourceToggleGroup);
		outsourcedRadio.setToggleGroup(partSourceToggleGroup);
		
		//Start the Scene with InHouse Radio selected
		changeRadioButton("InHouse");
		
		//BEGIN TEMP
		//Temporary due to automatic ID creation not implemented
		idField.setDisable(true);
		idField.setStyle("-fx-opacity: 1;"
				+ "-fx-background-color: lightgrey;");
		//END TEMP
	}
}
