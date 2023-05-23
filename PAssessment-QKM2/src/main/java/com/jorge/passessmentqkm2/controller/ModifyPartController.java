package com.jorge.passessmentqkm2.controller;

import com.jorge.passessmentqkm2.model.InHouse;
import com.jorge.passessmentqkm2.model.Inventory;
import com.jorge.passessmentqkm2.model.Outsourced;
import com.jorge.passessmentqkm2.model.Part;
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
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This is the Modify Part screen where a user modifies a part already in inventory
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class ModifyPartController implements Initializable {

     //(Stage) The stage where the current scene is displayed.
     //(Scene) The root object in the scene graph.
    Stage stage;
    Parent scene;


    //The currently selected part. It is set to null by default.
    private static Part selectedPart = null;


    //RadioButton object for selecting "In House" or "outSourced" option.
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outSourcedRadio;

    //TextField for entering/displaying the data of the part.
    @FXML
    private TextField IdTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField priceTxt;


    //Label for the source field, changes based on whether the part is InHouse or Outsourced.
    @FXML
    private Label sourceLbl;

    //TextField for entering/displaying the source information of the part, which could be either machine ID or company name.
    @FXML
    private TextField sourceTxt;

    /**
     * Sets the selected part to the specified part.
     *
     * @param newPart The part to be selected.
     */
    public static void getPart(Part newPart){
        selectedPart = newPart;
    }

    /**
     * Changes the current window to a new window specified by the resource string.
     * RUNTIME ERROR: can throw a runtime error if there is an issue with redirect location.
     *
     * @param event The action event that triggered the window change.
     * @param title The title of the new window.
     * @param string The resource string specifying the FXML file of the new window.
     * @throws IOException If an error occurs during loading the FXML file.
     */
    public void changeWindow(ActionEvent event, String title, String string) throws IOException {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource(string));
            stage.setTitle(title);
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(IOException e){
            MainScreenController.errorDialog("Error", "Location Error", "Failed to load Fxml loader, please check location of redirect");
        }
    }

    /**
     * RUNTIME ERROR: can throw a runtime error if fields are empty. These errors are caught by IOException, and the error dialog is shown
     * RUNTIME ERROR: a letter is entered where a numerical value was supposed to be entered. These errors are caught by IOException, and the error dialog is shown
     *
     * Save the data entered in the form when the save button is clicked.
     *
     * @param event The action event that triggered the save.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException{
        try{
            //get values from global selectedPart which has values that were passed from MainScreen
            int id = selectedPart.getId();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int inv = Integer.parseInt(invTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            //find the index of part in inventor
            int index = Inventory.getAllParts().indexOf(selectedPart);

            //check is min > price > max, if so prompts user using an error dialog box
            if(min >= max || inv < min || inv > max){
                MainScreenController.errorDialog("Error", "Input Error", "Minimum value must be below Max Value\n"+
                        "Inventory / stock value must be between min and max range");
            }else{
                //if min, max, and inv are ok, check which radio button was selected
                if(inHouseRadio.isSelected()){
                    //if inHouse - parse the string to int and create new part
                    int inHouse = Integer.parseInt(sourceTxt.getText());
                    InHouse updatedPart = new InHouse(id, name, price, inv, min, max, inHouse);
                    //delete the old part from inventory and add the new part.
                    Inventory.updatePart(index, updatedPart);
                }else{
                    //if outsourced - make new part
                    String outSourced = sourceTxt.getText();
                    Outsourced updatedPart = new Outsourced(id, name, price, inv, min, max, outSourced);
                    //delete the old part from inventory and add the new part.
                    Inventory.updatePart(index, updatedPart);
                }
                System.out.println(Arrays.asList(Inventory.getAllParts()));
                //change window
                changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
            }
        }catch(Exception e){
            //if any errors from text-field prompt user
            MainScreenController.errorDialog("Error", "Input Error", "Input error, Please fill out the inputs properly");
        }
    }

    /**
     * Cancels the current operation and switches back to the main window when the cancel button is clicked.
     *
     * @param event The action event that triggered the cancellation.
     * @throws IOException If an error occurs during loading the main window.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "Inventory Management System","/com/jorge/passessmentqkm2/mainScreen.fxml");
    }

    /**
     * Changes the label to "Machine ID" when the In-House radio button is selected.
     *
     * @param event The action event that triggered this change.
     */
    @FXML
    void onInHouse(ActionEvent event) {
        if(inHouseRadio.isSelected()){
            //when radio button is toggled, set label to Machine ID and clear text field.
            sourceLbl.setText("Machine ID");
        }
    }

    /**
     * Changes the label to "Company Name" when the Outsourced radio button is selected.
     *
     * @param event The action event that triggered this change.
     */

    @FXML
    void onOutSorced(ActionEvent event) {
        if(outSourcedRadio.isSelected()){
            //when radio button is toggled, set label to Company Name and clear text field.
            sourceLbl.setText("Company Name");
        }
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets the values of the text fields according to the selected part.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate text-field from the static part above, which is a copy of the chosen part
        IdTxt.setText(String.valueOf(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        invTxt.setText(String.valueOf(selectedPart.getStock()));
        priceTxt.setText(String.valueOf(selectedPart.getPrice()));
        maxTxt.setText(String.valueOf(selectedPart.getMax()));
        minTxt.setText(String.valueOf(selectedPart.getMin()));

        System.out.println(Arrays.asList(selectedPart));

        //check if part is inHouse or outsourced
        if(selectedPart instanceof InHouse){
            InHouse source = (InHouse) selectedPart;
            //set radio button to inHouse, set label to Machine ID, and change the integer value to string and display it
            inHouseRadio.setSelected(true);
            sourceLbl.setText("Machine ID");
            sourceTxt.setText(Integer.toString(source.getMachineId()));
        }else{
            Outsourced source = (Outsourced) selectedPart;
            //set radio button to outSourced, set label to Company Name, then display company name
            outSourcedRadio.setSelected(true);
            sourceLbl.setText("Company Name");
            sourceTxt.setText(source.getCompanyName());
        }

        System.out.println(getClass().getName() + "in initialized");

    }
}
