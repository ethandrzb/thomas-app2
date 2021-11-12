/*
 *  UCF COP3330 Summer 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class InventoryManagementApplication extends javafx.application.Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root =
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GraphicalInventoryManager.fxml")));

        Scene scene = new Scene(root); // attach scene graph to scene
        stage.setTitle("Graphical Inventory Manager"); // displayed in window's title bar

        stage.setScene(scene); // attach scene to stage
        stage.show(); // display the stage
    }
}
