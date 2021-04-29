package fr.kahlouch.fishmoves.model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final double width;
    private final double height;
    private final List<GameEntity> entities;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
    }

    private int randomBetween(int inf, int sup) {
        return new Random().nextInt(sup - inf) + inf;
    }

    private int randomWithMargin(int size, int margin) {
        return randomBetween(margin, size - margin);
    }

    public void addEntity(GameEntity entity) {
        entity.position = new Point2D(randomWithMargin((int) this.width, 400), randomWithMargin((int) this.height, 200));
        this.entities.add(entity);
    }

    public List<GameEntity> getEntities() {
        return entities;
    }
}
