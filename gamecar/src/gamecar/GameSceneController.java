/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package gamecar;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;
import javafx.stage.Stage;

public class GameSceneController {

    @FXML
    private AnchorPane gamePane;
    @FXML
    private Text scoreText;

    private ImageView playerCar;
    private ArrayList<ImageView> enemyCars = new ArrayList<>();
    private int score = 0;
    private boolean gameOver = false;
    
    public void setStage(Stage stage) {
    stage.getScene().setOnKeyPressed(event -> {
        if (!gameOver) {
            if (event.getCode() == KeyCode.LEFT && playerCar.getLayoutX() > 10) {
                playerCar.setLayoutX(playerCar.getLayoutX() - 20);
            } else if (event.getCode() == KeyCode.RIGHT && playerCar.getLayoutX() < 240) {
                playerCar.setLayoutX(playerCar.getLayoutX() + 20);
            }
        }
    });
}
    
    public void initialize() {
        setupGame();
        startGameLoop();
    }

    private void setupGame() {
        gamePane.setStyle("-fx-background-image: url('/assets/road.jpeg'); -fx-background-size: cover;");

        playerCar = new ImageView(new Image("/assets/car_player.png"));
        playerCar.setFitWidth(50);
        playerCar.setFitHeight(100);
        playerCar.setLayoutX(200);
        playerCar.setLayoutY(400);
        gamePane.getChildren().add(playerCar);

        for (int i = 0; i < 3; i++) {
            addEnemyCar();
        }

        scoreText.setText("Score: 0");
    }

    private void addEnemyCar() {
        Random random = new Random();
        ImageView enemyCar = new ImageView(new Image("/assets/enemy_car.png"));
        enemyCar.setFitWidth(50);
        enemyCar.setFitHeight(100);
        enemyCar.setLayoutX(random.nextInt(300));
        enemyCar.setLayoutY(-200);
        enemyCars.add(enemyCar);
        gamePane.getChildren().add(enemyCar);
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    moveEnemyCars();
                    checkCollisions();
                    updateScore();
                }
            }
        };
        gameLoop.start();
    }

    private void moveEnemyCars() {
        for (ImageView enemyCar : enemyCars) {
            enemyCar.setLayoutY(enemyCar.getLayoutY() + 5);
            if (enemyCar.getLayoutY() > 500) {
                enemyCar.setLayoutY(-200);
                enemyCar.setLayoutX(new Random().nextInt(240)); // Pastikan posisi musuh dalam area game
                score += 10;
            }
        }
    }

    private void checkCollisions() {
        for (ImageView enemyCar : enemyCars) {
            if (playerCar.getBoundsInParent().intersects(enemyCar.getBoundsInParent())) {
                endGame();
                return;
            }
        }
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }

    private void endGame() {
        gameOver = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game Over! Your final score is: " + score);
        alert.showAndWait();
    }
}