package dev.nicholaskoldys.inventorymanagementsystem;

import dev.nicholaskoldys.inventorymanagementsystem.model.InHouse;
import dev.nicholaskoldys.inventorymanagementsystem.model.Inventory;
import dev.nicholaskoldys.inventorymanagementsystem.model.Outsourced;
import dev.nicholaskoldys.inventorymanagementsystem.model.Part;
import dev.nicholaskoldys.inventorymanagementsystem.model.Product;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManagementSystem extends Application {
	
	private static Stage mainWindow;
	
	public static int partGenId = 0;
	public static int productGenId = 0;
	
	public static Stage getStage(){
		return mainWindow;
	}
	
	@Override
	public void init() {
		
		Part part1 = new InHouse(
				1, "Part1", 10.00, 200, 100, 500, 2147483641);
		Part part2 = new InHouse(
				2, "Part2", 20.00, 300, 300, 350, 2147483642);
		Part part3 = new Outsourced(
				3, "Part3", 30.00, 142, 135, 285, "Cool inc.");
		Part part4 = new InHouse(
				4, "Part4", 400.00, 30, 10, 50, 2147483644);
		Part part5 = new Outsourced(
				5, "Part5", 5000.00, 2, 1, 3, "Hazerdous llc.");
		
		Inventory.addPart(part1);
		Inventory.addPart(part2);
		Inventory.addPart(part3);
		Inventory.addPart(part4);
		Inventory.addPart(part5);
		
		Product product1 = new Product(
				1, "Product1", 1000.99, 100, 1, 100);
		Product product2 = new Product(
				2, "Product2", 10000.99, 200, 10, 1000);
		Product product3 = new Product(
				3, "Product3", 300.99, 300, 100, 10000);
		Product product4 = new Product(
				4, "Product4", 450.99, 4000, 1000, 100000);
		Product product5 = new Product(
				5, "Product5", 999.99, 50000, 10000, 1000000);
		
		Inventory.addProduct(product1);
		Inventory.addProduct(product2);
		Inventory.addProduct(product3);
		Inventory.addProduct(product4);
		Inventory.addProduct(product5);
		
		//This code is temporary - but must stay until an ID Generation method has been realized.
		//BEGIN TEMP
		partGenId = Inventory.getAllParts().size() + 1;
		productGenId = Inventory.getAllProducts().size() + 1;
		//END Temp
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("view/MainScreen.fxml"));
		
		Scene scene = new Scene(root);
		scene.setRoot(root);
		
		mainWindow = primaryStage;
		
		primaryStage.setScene(scene);
		primaryStage.show();		
	}


	public static void main(String[] args) {
		launch(args);
	}
	
}
