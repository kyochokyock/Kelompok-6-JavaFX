/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamecar;
/**
 * Abstract base class for all game objects.
 */
public abstract class GameObject {
    private double x, y;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract void moveVertically(double deltaY);
    public abstract void moveHorizontally(double deltaX);

    public void resetPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}