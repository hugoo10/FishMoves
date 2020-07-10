package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final double width;
    private final double height;
    private final List<Bird> birds;

    public World(double width, double height, int nbBirds) {
        this.width = width;
        this.height = height;
        this.birds = new ArrayList<>();
        for (int i = 0; i < nbBirds; ++i) {
            this.birds.add(new Bird(i, new Random().nextInt((int)this.width) , new Random().nextInt((int)this.height), this));
        }
    }

    public List<Bird> getBirds() {
        return birds;
    }

    public void tick() {
        long time = System.currentTimeMillis();
        birds.parallelStream().forEach(bird -> bird.move(time));
    }
}
