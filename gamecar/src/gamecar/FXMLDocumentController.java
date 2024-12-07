/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gamecar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField regUsernameField;

    @FXML
    private PasswordField regPasswordField;
    
    private Stage stage;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Please enter both username and password.");
            return;
        }
        
        try (Connection conn = DatabaseConfig.connect()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                showAlert("Login Successful", "Welcome, " + username + "!");
                switchScene("GameScene.fxml");
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while accessing the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Registration Failed", "Please fill in both username and password.");
            return;
        }

        try (Connection conn = DatabaseConfig.connect()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            showAlert("Registration Successful", "You can now log in.");
            switchScene("FXMLDocument.fxml");
        } catch (SQLException e) {
            showAlert("Registration Failed", "Username may already exist.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gamecar/"+fxmlFile));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Object controller = loader.getController();
            if (controller instanceof FXMLDocumentController) {
                ((FXMLDocumentController) controller).setStage(stage);
            }else if (controller instanceof GameSceneController) {
                ((GameSceneController) controller).setStage(stage); // Set Stage untuk GameSceneController
                ((GameSceneController) controller).initialize();
            }
            stage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to switch scene: " + e.getMessage());
        }
    }

    @FXML
    private void switchSceneToLogin(ActionEvent event) {
        switchScene("FXMLDocument.fxml");
    }

    @FXML
    private void switchSceneToRegister(ActionEvent event) {
        switchScene("Register.fxml");
    }

}