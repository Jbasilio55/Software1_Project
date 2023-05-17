package com.jorge.passessmentqkm2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is model class for Inventory, that store all products and parts
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class Inventory {
    /**
     * List of all parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the collection.
     *
     * @param newPart the new part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * Adds a new product to the collection.
     *
     * @param newProduct the new product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**
     * Looks up a part in the collection by its ID.
     *
     * @param partId the ID of the part to look up
     * @return the part with the given ID, or null if no such part exists
     */
    public static Part lookupPart(int partId){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * Looks up a product in the collection by its ID.
     *
     * @param productId the ID of the product to look up
     * @return the product with the given ID, or null if no such part exists
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> newList = FXCollections.observableArrayList();

        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId){
                newList.add(allProducts.get(i));
            }
        }
        return null;
    }

    /**
     * Looks up a part in the collection by its name.
     *
     * @param name the string of the part to look up
     * @return a list of parts with the given name, or null if no such part exists
     */
    public static ObservableList<Part> lookupPart(String name){
        ObservableList<Part> newList = FXCollections.observableArrayList();

        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().contains(name)){
                newList.add(allParts.get(i));
            }
        }

        return newList;
    }

    /**
     * Looks up a product in the collection by its name.
     *
     * @param name the ID of the product to look up
     * @return a list of products with the given name, or null if no such part exists
     */
    public static ObservableList<Product> lookupProduct(String name){
        ObservableList<Product> newList = FXCollections.observableArrayList();

        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName().contains(name)){
                newList.add(allProducts.get(i));
            }
        }

        return newList;
    }

    /**
     * Updates a part at a given index in the collection.
     *
     * @param index the index of the part to update
     * @param part the part to update
     */
    public static void updatePart(int index, Part part){
        int id;
        String name = part.getName();
        double price = part.getPrice();
        int stock = part.getStock();
        int min = part.getMin();
        int max = part.getMax();

        for(int i = 0; i < allParts.size(); i++){
            if(i == index){
                id = i;
                allParts.get(i).setName(name);
                allParts.get(i).setPrice(price);
                allParts.get(i).setStock(stock);
                allParts.get(i).setMin(min);
                allParts.get(i).setMax(max);
                Inventory.deletePart(Inventory.lookupPart(id));

                if(part instanceof InHouse){
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, ((InHouse) part).getMachineId()));
                }else{
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, ((Outsourced) part).getCompanyName()));
                }
            }
        }
    }
    /**
     * Updates a product at a given index in the collection.
     *
     * @param index the index of the product to update
     * @param product the product to update
     */
    public static void updateProduct(int index, Product product){
        for(int i = 0; i < allProducts.size(); i++){
            if(i == index){
                allParts.get(i).setId(product.getId());
                allParts.get(i).setName(product.getName());
                allParts.get(i).setPrice(product.getPrice());
                allParts.get(i).setStock(product.getStock());
                allParts.get(i).setMin(product.getMin());
                allParts.get(i).setMax(product.getMax());
            }
        }
    }

    /**
     * Deletes a part from the collection.
     *
     * @param part the part to delete
     * @return true if the part was successfully deleted, false otherwise
     */
    public static boolean deletePart(Part part){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).equals(part)){
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a product from the collection.
     *
     * @param product the product to delete
     * @return true if the product was successfully deleted, false otherwise
     */
    public static boolean deleteProduct(Product product){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).equals(product)){
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }
    /**
     * Returns all parts in the collection.
     *
     * @return an ObservableList of all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Returns all products in the collection.
     *
     * @return an ObservableList of all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
