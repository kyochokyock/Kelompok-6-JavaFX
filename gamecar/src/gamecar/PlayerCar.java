/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamecar;

/**
 * Represents the player-controlled car.
 */
public class PlayerCar extends GameObject {
    public PlayerCar(double x, double y) {
        super(x, y);
    }

    @Override
    public void moveHorizontally(double deltaX) {
        System.out.println("Moving horizontally: " + deltaX); 
        setX(getX() + deltaX);
    }

    @Override
    public void moveVertically(double deltaY) {
        System.out.println("Moving vertically: " + deltaY); 
        setY(getY() + deltaY);
    }
}