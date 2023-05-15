package com.jorge.passessmentqkm2.controller;

import com.jorge.passessmentqkm2.model.Inventory;
import com.jorge.passessmentqkm2.model.Part;
import com.jorge.passessmentqkm2.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This is my first screen and main screen of Inventory Management System
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class MainScreenController implements Initializable {

    /**
     * (Stage) The stage where the current scene is displayed.
     * (Scene) The root object in the scene graph.
     */
    Stage stage;
    Parent scene;

    /**
     * Table View for product and Part and Product
     * */
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Product> productsTableView;

    /**
     * Table columns for part
     * */
    @FXML
    private TableColumn<Part, Integer> partsIdCol;
    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    /**
     * Table columns for product
     * */
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * Search text-field for parts and products
     * */
    @FXML
    private TextField partsSearchTxt;
    @FXML
    private TextField productsSearchTxt;

    /**
     * Changes the window from one screen to another.
     *
     * @param event the action event that triggers the window change
     * @param title the title of the new window
     * @param string the name of the FXML file for the new window
     * @throws IOException if there's an error loading the FXML file
     */
    public void changeWindow(ActionEvent event, String title, String string) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays a confirmation dialog.
     *
     * @param title the title of the dialog
     * @param header the header text of the dialog
     * @param content the content text of the dialog
     * @return true if OK is clicked, false otherwise
     */
    static boolean confirmDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Displays an error dialog.
     *
     * @param title the title of the dialog
     * @param header the header text of the dialog
     * @param content the content text of the dialog
     */
    static void errorDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Handles the action of adding a new part. Opens the "Add Part" window.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        changeWindow(event, "Add Part", "/com/jorge/passessmentqkm2/AddPart.fxml");
    }

    /**
     * Handles the action of adding a new product. Opens the "Add Product" window.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        changeWindow(event, "Add Product", "/com/jorge/passessmentqkm2/AddProduct.fxml");
    }

    /**
     * Handles the action of modifying a part. Opens the "Modify Part" window for the selected part.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        try{
            //assign selectedPart to selected item from table
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            //if nothing is chosen return (do nothing)
            if(selectedPart == null){
                return;
            }
            //Pass the part that was selected to ModifyPartController
            ModifyPartController.getPart(selectedPart);
            //change window
            changeWindow(event,"Modify Part", "/com/jorge/passessmentqkm2/ModifyPart.fxml");
        }catch(IOException e){
            //Ignore if no selection was chosen (do nothing)
        }

    }

    /**
     * Handles the action of modifying a product. Opens the "Modify Product" window for the selected product.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        try{
            //assign selectedProduct to selected product from table
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            //ig null return (do nothing)
            if(selectedProduct == null){
                return;
            }
            //Pass the produced that was selected to ModifyProductController
            ModifyProductController.getProduct(selectedProduct);
            //change window
            changeWindow(event, "Modify Product", "/com/jorge/passessmentqkm2/ModifyProduct.fxml");
        }catch(IOException e){
            System.out.println("Unable to pass data");
        }
    }

    /**
     * Handles the action of deleting a part. Deletes the selected part after user confirmation.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        // assign selectedPart to chosen item
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        // if nothing was chosen, just return (do nothing)
        if(selectedPart == null){
            return;
        }
        //prompt user for confirmation of deletion. true - delete | false cancel
        if(confirmDialog("Delete Part","Delete", "Do you want to delete this part")){
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Handles the action of deleting a product. Deletes the selected product after user confirmation.
     * If the product has associated parts, an error dialog is displayed and the product is not deleted.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        //assign selectedPart to chosen value
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        //if null return (do nothing)
        if(selectedProduct == null){
            return;
        }
        //If selected product is empty, prompt user for confirmation
        if(selectedProduct.getAllAssociatedParts().isEmpty()){
            if(confirmDialog("Delete Part","Delete", "Do you want to delete this part")){
                Inventory.deleteProduct(selectedProduct);
            }
        }else{
            //if the product has an associated part, prompt error
            errorDialog("Error", "Error", "Can not delete products that have associated parts \n \n"+
                    "Please delete associated parts before deleting product");
        }
    }

    /**
     * Handles the action of exiting the application. Terminates the application when triggered.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Handles the action of searching for parts. Displays the parts that match the search criteria in the part table view.
     * Uses two methods for search, one using id and the other is matching with a character sequence
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void OnActionPartsSearch(ActionEvent event) {
        String text = partsSearchTxt.getText();
        //make a new list that will hold all matches
        ObservableList<Part> searchedParts = Inventory.lookupPart(text);
        //change partsTableView to all matches
        partsTableView.setItems(searchedParts);

        try{
            //if list has 0 matches its possible that the user entered an integer
            if(searchedParts.size() == 0){
                //change the text to a numeric form
                int searchedId = Integer.parseInt(text);
                //if any matches are found, add to searchedParts
                Part searchedPart = Inventory.lookupPart(searchedId);
                //only add if the prompt is not null
                if(searchedPart != null){
                    searchedParts.add(searchedPart);
                }
            }
        }catch(NumberFormatException e){
            //ignore
        }
    }

    /**
     * Handles the action of searching for products. Displays the products that match the search criteria in the product table view.
     *  Uses two methods for search, one using id and the other is matching with a character sequence
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionProductSearch(ActionEvent event){
        String text = productsSearchTxt.getText();
        ObservableList<Product> searchedProducts = Inventory.lookupProduct(text);
        productsTableView.setItems(searchedProducts);

        try{
            if(searchedProducts.size() == 0){
                int searchedId = Integer.parseInt(text);
                Product searchedProduct = Inventory.lookupProduct(searchedId);
                if(searchedProduct != null){
                    searchedProducts.add(searchedProduct);
                }
            }
        }catch(NumberFormatException e){
            //ignored
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate parts table and display in table
        partsTableView.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //populate products table and display in table
        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}