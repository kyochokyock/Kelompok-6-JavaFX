/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package gamecar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class Gamecar extends Application {
    
    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the initial FXML file (Login Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();

            // Get the controller and set the stage
            FXMLDocumentController controller = loader.getController();
            controller.setStage(primaryStage);

            // Set the scene and show the stage
            primaryStage.setTitle("GameCar - Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing the application: " + e.getMessage());
        }
    }
//    public void start2(Stage primaryStage) {
//        try {
//            // Load the GameScene.fxml directly
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gamecar/GameScene.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller and set the stage
//            GameSceneController controller = loader.getController();
//            controller.setStage(primaryStage);
//
//            // Set the scene and show the stage
//            primaryStage.setTitle("GameCar - Game Scene");
//            primaryStage.setScene(new Scene(root));
//            primaryStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error initializing the game scene: " + e.getMessage());
//        }
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
