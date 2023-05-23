package com.jorge.passessmentqkm2.controller;

import com.jorge.passessmentqkm2.model.InHouse;
import com.jorge.passessmentqkm2.model.Inventory;
import com.jorge.passessmentqkm2.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the Add Part screen where a user can create a part and add to inventory
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class AddPartController implements Initializable {


    //(Stage) The stage where the current scene is displayed.
    //(Scene) The root object in the scene graph.
    Stage stage;
    Parent scene;


    //Radio button for inhouse.
    @FXML
    private RadioButton inHouseRadio;


    //text-fields for part.
    @FXML
    private TextField InvTxt;
    @FXML
    private TextField sourceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField priceTxt;


    //label to switch from company name to machine id.
    @FXML
    private Label sourceLbl;

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
     * Handles the action of canceling the addition of a part. Returns the user to the main screen.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "Inventory Management System", "/com/jorge/passessmentqkm2/mainScreen.fxml");
    }

    /**
     * RUNTIME ERROR: can throw a runtime error if fields are empty. These errors are caught by IOException, and the error dialog is shown
     * RUNTIME ERROR: a letter is entered where a numerical value was supposed to be entered. These errors are caught by IOException, and the error dialog is shown
     *
     * Handles the action of saving a part. Validates the input, creates a new part (either InHouse or Outsourced), adds it to inventory, and returns the user to the main screen.
     *
     * @param event the action event that triggers this method
     * @throws IOException if there's an error loading the FXML file
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try{
            //Make a unique id by initializing id and searching all parts id, if it matches another keep adding 1
            int length = Inventory.getAllParts().size();
            int id = 1;

            for(int i = 0; i < length; i++){
                int productId = Inventory.getAllParts().get(i).getId();

                while(id == productId){
                    id++;
                }
            }

            //assign values in text-field to variables below
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int inv = Integer.parseInt(InvTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            String outSourced;

            //check if min > price > max, if so throw an error as a dialog box
            if(min >= max || inv < min || inv > max){
                MainScreenController.errorDialog("Error", "Input Error", "Minimum value must be below Max Value\n"+
                        "Inventory / stock value must be between min and max range");
            }else{
                if(inHouseRadio.isSelected()){
                    //If radio box in-house is selected, parse the string because the user will provide a number
                    int inHouse = Integer.parseInt(sourceTxt.getText());
                    //make new in-house part and add to inventory
                    Inventory.addPart(new InHouse(id, name, price, inv, min, max, inHouse));
                }else{
                    //get string from outsourced and make new outsourced part (using values above) and add to inventory
                    outSourced = sourceTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, inv, min, max, outSourced));
                }
                //change window
                changeWindow(event, "Inventory Management System", "/com/jorge/passessmentqkm2/mainScreen.fxml");
            }
        }catch(Exception e){
            //if there are any errors with text-field, prompt the user with an error message
            MainScreenController.errorDialog("Error", "Input Error", "Input error, Please fill out the inputs properly");
        }
    }

    /**
     * Handles the action of selecting the InHouse radio button. Changes the label of the source text field to "Machine ID".
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onInHouse(ActionEvent event) {
        sourceLbl.setText("Machine ID");
    }

    /**
     * Handles the action of selecting the Outsourced radio button. Changes the label of the source text field to "Company Name".
     *
     * @param event the action event that triggers this method
     */
    @FXML
    void onOutSourced(ActionEvent event) {
        sourceLbl.setText("Company Name");
    }

    /**
     * Initializes the AddPartController after its root element has been completely processed.
     * This method is called automatically.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add parts Initialized");
    }
}
