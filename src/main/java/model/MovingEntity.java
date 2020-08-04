package model;

import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Getter
public abstract class MovingEntity {
    protected final static double VIEW_DISTANCE = 160;
    protected final static double TOO_CLOSE_DISTANCE = 40;
    protected final static double TOO_FAR_DISTANCE = 160;
    protected final static int SPEED = 300;
    protected final static int MAX_HISTORY = 100;
    protected int idSprite = 0;
    protected double lastChangeIdTime;

    protected int id;
    protected Point2D.Double position;
    protected World world;
    protected double dX;
    protected double dY;

    protected Queue<Point2D.Double> history = new ArrayDeque<>();
    //Meta
    protected long lastMoveTime;

    public MovingEntity(int id, double posX, double posY, World world) {
        this.id = id;
        this.world = world;
        this.position = new Point2D.Double(posX, posY);
        this.lastMoveTime = System.currentTimeMillis();
        this.lastChangeIdTime = this.lastMoveTime;
        this.dX = new Random().nextDouble() * 20 - 10;
        this.dY = new Random().nextDouble() * 20 - 10;
    }

    public abstract double getAngleInRadian();


    public double getAngleInDegree() {
        return Math.toDegrees(getAngleInRadian());
    }

    public abstract void move(long time);

    public Point2D.Double getPosition() {
        return position;
    }

    public List<MovingEntity> getNClosest(int n) {
        return getClosest().stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<MovingEntity> getClosest() {
        return getClosestWithAdditionalBehaviour((m, o) -> m, null)
                .stream()
                .sorted(Comparator.comparingDouble(bird -> bird.position.distance(this.position)))
                .collect(Collectors.toList());
    }

    public <T> List<MovingEntity> getClosestWithAdditionalBehaviour(BiFunction<MovingEntity, T, MovingEntity> additional, T object) {
        return this.world.getMovingEntities().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE)
                .map(movingEntity -> additional.apply(movingEntity, object))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Optional<MovingEntity> getClosestOne() {
        return getNClosest(1).stream().findFirst();
    }

    protected void addMove(Point2D.Double move) {
        this.history.add(move);
        if (this.history.size() > MAX_HISTORY) {
            this.history.remove();
        }
    }

    public Queue<Point2D.Double> getHistory() {
        return history;
    }

    public int getId() {
        return id;
    }

    public int getIdSprite() {
        return idSprite;
    }
}
