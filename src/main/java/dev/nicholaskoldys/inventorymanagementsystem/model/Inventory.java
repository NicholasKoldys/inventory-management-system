package dev.nicholaskoldys.inventorymanagementsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
	
	private static ObservableList<Part> allParts
			= FXCollections.observableArrayList();
	private static ObservableList<Product> allProducts
			= FXCollections.observableArrayList();
	
	public static void addPart(Part newPart) {
		allParts.add(newPart);
	}
	
	public static void addProduct(Product newProduct) {
		allProducts.add(newProduct);
	}
	
	public static Part lookupPart(int partId) {
		for (Part part : allParts) {
			if (partId == part.getId()) {
				return part;
			}
		}
		return null;
	}
	
	public static Product lookupProduct(int productId) {
		for (Product product : allProducts) {
			if (productId == product.getId()) {
				return product;
			}
		}
		return null;
	}
	
	public static ObservableList<Part> lookupPart(String partName) {
		
		ObservableList<Part> lookupPartList = FXCollections.observableArrayList();
		
		for (Part part : allParts) {
			if (part.getName().trim().toLowerCase().contains(partName)) {
				lookupPartList.add(part);
			}
		}
		return lookupPartList;
	}
	
	public static ObservableList<Product> lookupProduct(String productName) {
		
		ObservableList<Product> lookupProductList = FXCollections.observableArrayList();
		
		for (Product product : allProducts) {
			if (product.getName().trim().toLowerCase().contains(productName)) {
				lookupProductList.add(product);
			}
		}
		return lookupProductList;
	}
	
	public static void updatePart(int index, Part selectedPart) {
		
		allParts.remove(lookupPart(index));
		allParts.add(selectedPart);
		
		for (Product product : allProducts) {
			for (Part part : product.getAllAssociatedParts()) {
				if (part.getId() == selectedPart.getId()) {
					product.deleteAssociatedPart(part);
					product.addAssociatedPart(selectedPart);
				}
			}
		}
	}
	
	public static void updateProduct(int index, Product selectedProduct) {
		allProducts.remove(lookupProduct(index));
		allProducts.add(selectedProduct);
	}
	
	public static void deletePart(Part selectedPart) {
		
		for (Product product : Inventory.getAllProducts()) {
			if (product.getAllAssociatedParts().contains(selectedPart)) {
				product.deleteAssociatedPart(selectedPart);
			}
		}
		
		allParts.remove(selectedPart);
	}
	
	public static void deleteProduct(Product selectedProduct) {
		allProducts.remove(selectedProduct);
	}
	
	public static ObservableList<Part> getAllParts() {
		return allParts;
	}
	
	public static ObservableList<Product> getAllProducts() {
		return allProducts;
	}
	
}
