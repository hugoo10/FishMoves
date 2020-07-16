package model;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MovingEntity {
    protected final static double VIEW_DISTANCE = 50;
    protected final static double TOO_CLOSE_DISTANCE = 20;
    protected final static double TOO_FAR_DISTANCE = 50;
    protected final static int SPEED = 100;

    protected int id;
    protected Point2D.Double position;
    protected World world;
    //Meta
    protected long lastMoveTime;

    public MovingEntity(int id, double posX, double posY, World world) {
        this.id = id;
        this.world = world;
        this.position = new Point2D.Double(posX, posY);
        this.lastMoveTime = System.currentTimeMillis();
    }

    public abstract int getAngleInDegree();

    public abstract double getAngleInRadian();

    public abstract void move(long time);

    public Point2D.Double getPosition() {
        return position;
    }

    public List<MovingEntity> getNClosest(int n) {
        return this.world.getMovingEntities().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE)
                .sorted(Comparator.comparingDouble(bird -> bird.position.distance(this.position)))
                .limit(n)
                .collect(Collectors.toList());
    }

    public Optional<MovingEntity> getClosest() {
        return Optional.ofNullable(getNClosest(1).get(0));
    }
}
