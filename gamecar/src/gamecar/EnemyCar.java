/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamecar;
import javafx.scene.image.ImageView;
/**
 * Represents an enemy car in the game.
 */
public class EnemyCar extends GameObject {
    private final ImageView view;

    public EnemyCar(ImageView view) {
        super(view.getLayoutX(), view.getLayoutY());
        this.view = view;
    }

    @Override
    public void moveVertically(double deltaY) {
        setY(getY() + deltaY);
    }

    @Override
    public void moveHorizontally(double deltaX) {
        setX(getX() + deltaX);
    }

    public ImageView getView() {
        return view;
    }

    public void updateView() {
        view.setLayoutX(getX());
        view.setLayoutY(getY());
    }
}