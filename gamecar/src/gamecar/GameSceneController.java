/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gamecar;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;
/**
 * Controller for the game, managing player and enemy cars.
 */
public class GameSceneController {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView playerCar;

    @FXML
    private ImageView enemyCar1, enemyCar2, enemyCar3;

    @FXML
    private Rectangle playerBounds;

    @FXML
    private Text scoreText, gameOverText;

    @FXML
    private Button playButton, resetButton;

    private PlayerCar player;
    private ArrayList<EnemyCar> enemies;
    private int score;
    private boolean gameOver;
    private AnimationTimer gameLoop;
    
    private double speed = 1.5;  // Initial speed of the enemies
    private final double maxSpeed = 5.0;  // Maximum speed

    /**
     * Initialize the game scene.
     */
    public void initialize() {
        // Initialize player car logic
        player = new PlayerCar(playerCar.getLayoutX(), playerCar.getLayoutY());

        // Initialize enemy cars
        enemies = new ArrayList<>();
        enemies.add(new EnemyCar(enemyCar1));
        enemies.add(new EnemyCar(enemyCar2));
        enemies.add(new EnemyCar(enemyCar3));

        // Prepare UI elements
        resetButton.setVisible(false);
        gameOverText.setVisible(false);
        playButton.setVisible(true);

        setupKeyboardControls();
        gamePane.setFocusTraversable(true);
        resetGame();
        
        
    }

    private void setupKeyboardControls() {
        // Cek apakah gamePane benar-benar menerima input keyboard
        gamePane.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode()); 
            if (gameOver) return;  // Tidak bisa bergerak jika game over

            double x = playerCar.getLayoutX();
            double y = playerCar.getLayoutY();

            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                if (x > playerBounds.getLayoutX()) {
                    playerCar.setLayoutX(x - 10);
                }
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                if (x + playerCar.getFitWidth() < playerBounds.getLayoutX() + playerBounds.getWidth()) {
                    playerCar.setLayoutX(x + 10);
                }
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                if (y > playerBounds.getLayoutY()) {
                    playerCar.setLayoutY(y - 10);
                }
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                if (y + playerCar.getFitHeight() < playerBounds.getLayoutY() + playerBounds.getHeight()) {
                    playerCar.setLayoutY(y + 10);
                }
            }
        });

        gamePane.requestFocus();
    }

    private void updatePlayerView() {
        System.out.println("Player position updated: " + player.getX() + ", " + player.getY());
        playerCar.setLayoutX(player.getX());
        playerCar.setLayoutY(player.getY());
    }

   
    private void moveEnemies() {
        Random random = new Random();
        for (EnemyCar enemy : enemies) {
            enemy.moveVertically(speed);
            if (enemy.getY() > playerBounds.getHeight()) {
                // Set new valid position for the enemy when it goes off the screen
                setUniquePosition(enemy, random);
                score += 10;
            }
            enemy.updateView(); // Update the view of the enemy car after moving
        }

        // Increase the speed gradually as the score increases
        if (score % 50 == 0 && speed < maxSpeed) {
            speed += 0.5; // Increase speed by 0.5
        }
    }
      
    
    private void setUniquePosition(EnemyCar enemy, Random random) {
        boolean positionValid = false;

        while (!positionValid) {
            // Generate random X and Y positions for the enemy
            double newX = playerBounds.getLayoutX() + random.nextInt((int) (playerBounds.getWidth() - enemyCar1.getFitWidth()));
            double newY = -200 - random.nextInt(300); // Spawn above the screen

            positionValid = true;

            // Check if the new position overlaps with other enemies
            for (EnemyCar otherEnemy : enemies) {
                if (otherEnemy != enemy && Math.abs(otherEnemy.getX() - newX) < 50 && Math.abs(otherEnemy.getY() - newY) < 50) {
                    positionValid = false;
                    break;
                }
            }

            // Check if the new position is too close to the player
            double playerX = playerCar.getLayoutX();
            double playerY = playerCar.getLayoutY();
            double distanceX = Math.abs(playerX - newX);
            double distanceY = Math.abs(playerY - newY);

            // Ensure there's enough distance between the player and enemy
            if (positionValid && distanceX < 100 && distanceY < 100) {
                positionValid = false; // Too close to the player, try again
            }

            // If valid, set the new position for the enemy
            if (positionValid) {
                enemy.setX(newX);
                enemy.setY(newY);
            }
        }
    }


    private void checkCollisions() {
        for (EnemyCar enemy : enemies) {
            if (playerCar.getBoundsInParent().intersects(enemy.getView().getBoundsInParent())) {
                gameOver = true;
                gameLoop.stop();
                gameOverText.setText("Game Over! Your Final Score is : " + score);
                gameOverText.setVisible(true);
                resetButton.setVisible(true);
            }
        }
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }

    
    @FXML
    private void startGame() {
        System.out.println("Game started!"); 
        score = 0;
        gameOver = false;
        gameOverText.setVisible(false);
        playButton.setVisible(false);
        resetButton.setVisible(false);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveEnemies();
                checkCollisions();
                updateScore();
            }
        };
        gameLoop.start();
        gamePane.requestFocus(); 
    }

    @FXML
    private void resetGame() {
        player.resetPosition(200, 550);
        updatePlayerView();

        for (EnemyCar enemy : enemies) {
            enemy.resetPosition(enemy.getX(), -100); // Reset to initial positions
            enemy.updateView();
        }

        score = 0;
        scoreText.setText("Score: 0");
        gameOver = false;
        gameOverText.setVisible(false);
        resetButton.setVisible(false);
        playButton.setVisible(true);

        // Reset speed when the game is reset
        speed = 1.5;
    }
}