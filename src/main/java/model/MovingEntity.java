package model;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class MovingEntity {
    protected final static double VIEW_DISTANCE = 160;
    protected final static double TOO_CLOSE_DISTANCE = 40;
    protected final static double TOO_FAR_DISTANCE = 160;
    protected final static int SPEED = 300;

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

    public abstract double getAngleInRadian();

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
}
