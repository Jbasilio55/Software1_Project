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
import java.util.ResourceBundle;

/**
 * This is the Modify Product screen where a user modifies a product already in inventory
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class ModifyProductController implements Initializable {
    /**
     * (Stage) The stage where the current scene is displayed.
     * (Scene) The root object in the scene graph.
     */
    private Stage stage;
    private Parent scene;

    /**
     * The product that is currently selected.
     */
    public static Product product = null;
    /**
     * A list of parts that are associated with the currently selected product.
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The table that displays all parts and associate parts.
     */
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Part> assPartTableView;

    /**
     * Table columns for associated parts
     * */
    @FXML
    private TableColumn<Part, Integer> assPartIdCol;
    @FXML
    private TableColumn<Part, Integer> assPartInvCol;
    @FXML
    private TableColumn<Part, String> assPartNameCol;
    @FXML
    private TableColumn<Part, Double> assPartPriceCol;

    /**
     * Table columns for parts
     * */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partPriceCol;

    /**
     * The text field for the info of the product.
     */
    @FXML
    private TextField productIdTxt;
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
    @FXML
    private TextField partsSearchTxt;

    /**
     * Changes the current window to a new window specified by the string resource path.
     * @param event the action event that triggers the window change.
     * @param title the title of the new window.
     * @param string the resource path to the new window's fxml file.
     * @throws IOException if an I/O error occurs.
     */
    public void changeWindow(ActionEvent event, String title, String string) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Updates the parts table view with all parts from the inventory.
     */
    public void updatePartTable() {
        partsTableView.setItems(Inventory.getAllParts());
    }

    /**
     * Updates the associated parts table view with parts from the associatedParts list.
     */
    private void updateAssociatedPartsTable() {
        assPartTableView.setItems(associatedParts);
    }
    /**
     * Sets the selected product to the specified product.
     * @param selectedProduct The product to be selected.
     */
    public static void getProduct(Product selectedProduct){
        product = selectedProduct;
        associatedParts.clear();
        associatedParts.addAll(product.getAllAssociatedParts());
    }

    /**
     * Adds the selected part from the parts table to the associated parts list.
     * @param event the action event that triggers the addition.
     */
    @FXML
    void onActionAdd(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        //if null do nothing
        if (selectedPart == null) {
            return;
        }
        //add to global associated parts then updates table
        associatedParts.add(selectedPart);
        updateAssociatedPartsTable();
    }

    /**
     * Removes the selected part from the associated parts list.
     * @param event the action event that triggers the removal.
     */
    @FXML
    void onActionRemove(ActionEvent event) {
        Part selectedPart = assPartTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            return;
        }
        if(MainScreenController.confirmDialog("Remove Part", "Remove",
                "Are you sure you want to remove associated part?")){
            associatedParts.remove(selectedPart);
            updateAssociatedPartsTable();
        }
    }

    /**
     * Saves the changes made to the selected product and its associated parts.
     * @param event the action event that triggers the save.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try{
            //get all products
            ObservableList<Product> products = Inventory.getAllProducts();

            //use global product to get id
            //if text-field values are supposed to be int/double parse them
            int id = product.getId();
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int inv = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            //min > price > max, prompt user with error dialog
            if(min >= max || inv < min || inv > max){
                MainScreenController.errorDialog("Error", "Input Error", "Minimum value must be below Max Value\n"+
                        "Inventory / stock value must be between min and max range");
            }else{
                //if all values are good, set to Inventory product values by find it with id
                for(int i = 0; i < products.size(); i++){
                    if(products.get(i).getId() == id){
                        products.get(i).setName(name);
                        products.get(i).setPrice(price);
                        products.get(i).setStock(inv);
                        products.get(i).setMax(max);
                        products.get(i).setMin(min);
                    }
                }

                //iterate through all products until product id match
                for(int i = 0; i < products.size(); i++){
                    if(products.get(i).getId() == product.getId()){
                        //clear the products associated parts from inventory
                        products.get(i).getAllAssociatedParts().clear();
                        for(int j = 0; j < associatedParts.size(); j++){
                            //add the static associated parts back into product associated parts inventory
                            product.addAssociatedPart(associatedParts.get(j));
                        }
                    }
                }

                //update table
                updateAssociatedPartsTable();
                //change window
                changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
            }
        }catch(Exception e){
            //if error, then it will be from text-field. prompt user with dialog box
            MainScreenController.errorDialog("Error", "Input Error", "Input error, Please fill out the inputs properly");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Filters the parts table view to only show parts whose name or ID match the entered text.
     * @param event the action event that triggers the search.
     */
    @FXML
    void onPartSearchTxt(ActionEvent event) {
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
     * Cancels the current action and changes the window back to the main screen.
     * @param event the action event that triggers the cancellation.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. It populates the parts and associated
     * parts tables and sets the text fields with the details of the selected product.
     *
     * @param url The location used to resolve relative paths for the root object,
     *            or null if the location is not known.
     *
     * @param resourceBundle The resources used to localize the root object,
     *                       or null if the root object was not localized.
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
        assPartTableView.setItems(product.getAllAssociatedParts());
        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //populate text-field of passed value from mainScreen
        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(String.valueOf(product.getStock()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productMaxTxt.setText(String.valueOf(product.getMax()));
        productMinTxt.setText(String.valueOf(product.getMin()));

        //update both tables
        updateAssociatedPartsTable();
        updatePartTable();
    }
}
