package com.jorge.passessmentqkm2.controller;

import com.jorge.passessmentqkm2.model.Inventory;
import com.jorge.passessmentqkm2.model.Part;
import com.jorge.passessmentqkm2.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This is the Add Product screen where a user can create a product, and add associated parts to product
 *
 * FUTURE ENHANCEMENT: I would like to add a sorting algorithm to the part-table.
 *
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class AddProductController implements Initializable {

    //(Stage) The stage where the current scene is displayed.
    //(Scene) The root object in the scene graph. The scene consists of a set of hierarchically arranged objects in a tree-like structure.
    private Stage stage;
    private Parent scene;


    //A list of parts that are associated with a specific product. The list is observed for changes to ensure the UI stays up to date.
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    //The table view in the UI that displays the list of parts and products.
    @FXML
    private TableView<Part> assPartTableView;
    @FXML
    private TableView<Part> partsTableView;


    //The table column in the UI that displays the id, inv, name, and price for associated part
    @FXML
    private TableColumn<Product, Integer> assPartIdCol;
    @FXML
    private TableColumn<Product, Integer> assPartInvCol;
    @FXML
    private TableColumn<Part, String> assPartNameCol;
    @FXML
    private TableColumn<Product, Double> assPartPriceCol;


     //The table column in the UI that displays the id, inv, name, and price for part
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;


    //The text field in the UI where the user can input the product info
    @FXML
    private TextField productInvTxt;
    @FXML
    private TextField productMaxTxt;
    @FXML
    private TextField productMinTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productPriceTxt;


     //The text field in the UI where the user can input the text to search for parts.
    @FXML
    private TextField partsSearchTxt;

    /**
     * Changes the current window to a new window.
     * RUNTIME ERROR: can throw a runtime error if there is an issue with redirect location.
     *
     * @param event the action event that triggers this method
     * @param title the title of the new window
     * @param string the path of the FXML file for the new window
     * @throws IOException if there's an error loading the FXML file
     */
    public void changeWindow(ActionEvent event, String title, String string) throws IOException {
        try{
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource(string));
            stage.setTitle(title);
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(IOException e){
            MainScreenController.errorDialog("Error", "Location Error", "Failed to load Fxml loader, please check location of redirect");
        }
    }

    /**
     * Updates the parts table view with all parts from the inventory.
     */
    public void updatePartsTable() {
        partsTableView.setItems(Inventory.getAllParts());
    }

    /**
     * Updates the associated parts table view with all associated parts from the list.
     */
    private void updateAssociatedPartsTable() {
        assPartTableView.setItems(associatedParts);
    }

    /**
     * Handles the action of adding a part to the associated parts list.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        //if nothing is selected (do nothing)
        if (selectedPart == null) {
            return;
        }
        //if a user selected a product add it to the global list above
        associatedParts.add(selectedPart);
        //update associate table
        updateAssociatedPartsTable();
    }

    /**
     * Handles the action of canceling the current operation. Returns the user to the main screen.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
    }

    /**
     * Handles the action of removing a part from the associated parts list.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionRemoveAssPart(ActionEvent event) {
        Part selectedPart = assPartTableView.getSelectionModel().getSelectedItem();
        //if does not select anything (do nothing)
        if(selectedPart == null){
            return;
        }
        //if selection is not null prompt user for conformation using dialog
        if(MainScreenController.confirmDialog("Remove Associated Part", "Remove", "Do you want to remove this associated part")){
            associatedParts.remove(selectedPart);
            updateAssociatedPartsTable();
        }
    }

    /**
     * RUNTIME ERROR: can throw a runtime error if fields are empty. These errors are caught by IOException, and the error dialog is shown
     * RUNTIME ERROR: a letter is entered where a numerical value was supposed to be entered. These errors are caught by IOException, and the error dialog is shown
     *
     * Handles the action of saving a new product. Validates the input, creates a new product, adds it to inventory, and returns the user to the main screen.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try{
            //get new unique Id by iterating through all products. incrementing id until id doesn't match existing product id
            int length = Inventory.getAllProducts().size();
            int id = 1;

            for(int i = 0; i < length; i++){
                int productId = Inventory.getAllProducts().get(i).getId();

                while(id == productId){
                    id++;
                }
            }
            //assign values in text-fields to variables. use parse to change string to int or double
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int inv = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            // if min > price > max, prompt user about error in values
            if(min >= max || inv < min || inv > max){
                MainScreenController.errorDialog("Error", "Input Error", "Minimum value must be below Max Value\n"+
                        "Inventory / stock value must be between min and max range");
            }else{
                //make new product and add to Inventory
                Inventory.addProduct(new Product(id, name, price, inv, min, max));
                //get product that was just created and add parts from static associated part
                for(int i = 0; i < Inventory.getAllProducts().size(); i++){
                    if(Inventory.getAllProducts().get(i).getId() == id){
                        for(int j = 0; j < associatedParts.size(); j++){
                            //add the static associated parts back into product associated parts inventory
                            Inventory.getAllProducts().get(i).addAssociatedPart(associatedParts.get(j));
                        }
                    }
                }

                System.out.println(Arrays.asList(associatedParts));

                //update table
                updateAssociatedPartsTable();
                //change window
                changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
            }
        }catch(Exception e){
            //if there is an error in any of the text-fields, prompt user with dialog
            MainScreenController.errorDialog("Error", "Input Error", "Input error, Please fill out the inputs properly");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles the action of searching for a part in the parts list.
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        String text = partsSearchTxt.getText();
        ObservableList<Part> searchedParts = Inventory.lookupPart(text);
        partsTableView.setItems(searchedParts);

        try{
            if(searchedParts.size() == 0){
                int searchedId = Integer.parseInt(text);
                Part searchedPart = Inventory.lookupPart(searchedId);
                if(searchedPart != null){
                    searchedParts.add(searchedPart);
                }
            }
        }catch(NumberFormatException e){
            //ignored
        }
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called automatically.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate parts table
        ObservableList<Part> allParts = Inventory.getAllParts();
        partsTableView.setItems(allParts);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //populate associated parts table
        assPartTableView.setItems(associatedParts);
        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //update both tables
        updatePartsTable();
        updateAssociatedPartsTable();
    }
}
