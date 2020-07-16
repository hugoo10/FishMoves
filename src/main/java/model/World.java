package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final double width;
    private final double height;
    private final List<MovingEntity> movingEntities;

    public World(double width, double height, int nbBirds) {
        this.width = width;
        this.height = height;
        this.movingEntities = new ArrayList<>();
        for (int i = 0; i < nbBirds; ++i) {
            this.movingEntities.add(new Bird2(i, new Random().nextInt((int) this.width), new Random().nextInt((int) this.height), this));
        }
    }

    public List<MovingEntity> getMovingEntities() {
        return movingEntities;
    }

    public void tick() {
        long time = System.currentTimeMillis();
        movingEntities.parallelStream().forEach(movingEntity -> movingEntity.move(time));
    }
}
