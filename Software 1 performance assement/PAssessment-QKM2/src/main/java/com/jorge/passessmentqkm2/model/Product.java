package com.jorge.passessmentqkm2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is model class for product which also hold a list of associated parts
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class Product {
    /**
     * The associated parts of this product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * fields
     * */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;

    private int max;

    /**
     * Creates a new Product with the given parameters.
     *
     * @param id the ID of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the stock level of the product
     * @param min the minimum allowable stock level of the product
     * @param max the maximum allowable stock level of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * Setters
     * */
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getters
     * */
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    /**
     * Adds an associated part to this product.
     *
     * @param part the part to be added
     */

    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     * Attempts to remove an associated part from this product.
     *
     * @param associatedPart the part to be removed
     * @return true if the part was successfully removed, false otherwise
     */
    public boolean deleteAssociatedPart(Part associatedPart){
        //iterate through an associated parts list
        for(int i = 0; i < associatedParts.size(); i++){
            //parameter Part is in associated part then delete
            if(this.associatedParts.get(i).equals(associatedPart)){
                this.associatedParts.remove(associatedPart);
                //return true
                return true;
            }
        }
        //return false
        return false;
    }

    /**
     * Returns all associated parts of this product.
     *
     * @return a list of associated parts
     */

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    //toString method, I used it to make sure the values were being passed on correctly
    @Override
    public String toString() {
        return "Product ID: " + this.getId() +
                ", Name: " + this.getName() +
                ", Price: " + this.getPrice() +
                ", Stock: " + this.getStock() +
                ", Min: " + this.getMin() +
                ", Max: " + this.getMax();
    }
}