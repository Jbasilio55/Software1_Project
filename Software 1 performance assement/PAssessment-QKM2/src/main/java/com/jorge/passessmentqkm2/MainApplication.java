package com.jorge.passessmentqkm2;

import com.jorge.passessmentqkm2.model.InHouse;
import com.jorge.passessmentqkm2.model.Inventory;
import com.jorge.passessmentqkm2.model.Outsourced;
import com.jorge.passessmentqkm2.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

/**
 * This is the main application where the program starts
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class MainApplication extends Application {
    /**
     * This is my start method from fxml loader that holds static data I am using as test data
     * */
    @Override
    public void start(Stage stage) throws IOException {
        //Add Parts InHouse
        Inventory.addPart(new InHouse(1, "Lord of the Rings: Gollum", 49.99, 50, 150, 30, 85));
        Inventory.addPart(new InHouse(2, "Zombie Army", 7.99, 50, 100, 500, 88));
        Inventory.addPart(new InHouse(3, "Call of Duty: Black ops 3", 30.99, 50, 200, 1000, 34));
        Inventory.addPart(new InHouse(4, "Grand Theft auto 5", 30.50, 462, 50, 1000, 12));

        //Add Parts OutSourced
        Inventory.addPart(new Outsourced(5, "Rust", 39.99, 50, 1, 100, "Epic Games"));
        Inventory.addPart(new Outsourced(6, "IXION", 35.99, 35, 1, 50, "PopCap Games"));
        Inventory.addPart(new Outsourced(7, "Hogwarts legacy", 50.99, 88, 1, 100, "Activison"));
        Inventory.addPart(new Outsourced(8, "Elder Scrolls Online", 8.99, 20, 1, 50, "Ubisoft"));
        Inventory.addPart(new Outsourced(9, "Marvel's Guardian of the Galaxy", 20.99, 50, 1, 50, "Nintendo"));
        Inventory.addPart(new Outsourced(10, "Dredge", 10.99, 12, 1, 30, "Square two"));
        Inventory.addPart(new Outsourced(11, "Hell Let Loose", 17.89, 47, 1, 50, "Some video game company"));

        //Add Products
        Inventory.addProduct(new Product(1, "PS5", 550.19, 50, 1, 100));
        Inventory.addProduct(new Product(2, "Desktop Computer", 2099.99, 34, 1, 50));
        Inventory.addProduct(new Product(3, "Apple Laptop", 2500.50, 25, 1, 30));

        System.out.println(Arrays.asList(Inventory.getAllParts()));
        System.out.println(Arrays.asList(Inventory.getAllProducts()));

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/jorge/passessmentqkm2/mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * java main, where the program is launched
     * */
    public static void main(String[] args) {
        launch();
    }
}