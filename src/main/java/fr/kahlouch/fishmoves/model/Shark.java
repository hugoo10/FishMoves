package fr.kahlouch.fishmoves.model;

import javafx.geometry.Point2D;

public class Shark {
    public static final Shark INSTANCE = new Shark();

    private Shark() {
    }

    private Point2D position = new Point2D(-10, -10);

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return position;
    }
}
