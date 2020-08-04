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

    public void bounce() {
        if (position.x + dX < 20 || position.x + dX >= 1900) {
            this.dX = -dX;
        } else if (position.y + dY < 20 || position.y + dY >= 1060) {
            this.dY = -dY;
        }
    }

    public void avoidPoint() {
        final Point2D.Double escapePoint = new Point2D.Double();
        final double avoidFactor = 0.05;
        List<MovingEntity> toAvoid = getClosestWithAdditionalBehaviour((movingEntity, point) -> {
            if (movingEntity.position.distance(this.position) < TOO_CLOSE_DISTANCE) {
                point.setLocation(
                        point.x + this.position.x - movingEntity.position.x,
                        point.y + this.position.y - movingEntity.position.y
                );
                return movingEntity;
            }
            return null;
        }, escapePoint);
        if (!toAvoid.isEmpty()) {
            this.dX += escapePoint.x * avoidFactor;
            this.dY += escapePoint.y * avoidFactor;
        }
    }

    public void flyTowardPoint() {
        final double flyTowardFactor = 0.005;
        final Point2D.Double centerOfMassPoint = new Point2D.Double();
        List<MovingEntity> toFlyTowards = getClosestWithAdditionalBehaviour((movingEntity, point) -> {
            if (movingEntity.position.distance(this.position) > TOO_FAR_DISTANCE) {
                point.setLocation(
                        point.x + movingEntity.position.x,
                        point.y + movingEntity.position.y
                );
                return movingEntity;
            }
            return null;
        }, centerOfMassPoint);
        if (!toFlyTowards.isEmpty()) {
            centerOfMassPoint.setLocation(centerOfMassPoint.x / toFlyTowards.size(), centerOfMassPoint.y / toFlyTowards.size());
            this.dX += (centerOfMassPoint.x - this.position.x) * flyTowardFactor;
            this.dY += (centerOfMassPoint.y - this.position.y) * flyTowardFactor;
        }
    }

    public void matchPoint() {
        final double matchFactor = 0.05;
        final Point2D.Double averageVelocity = new Point2D.Double();
        List<MovingEntity> toMatch = getClosestWithAdditionalBehaviour((movingEntity, point) -> {
            if (movingEntity.position.distance(this.position) <= TOO_FAR_DISTANCE && movingEntity.position.distance(this.position) >= TOO_CLOSE_DISTANCE) {
                point.setLocation(
                        point.x + movingEntity.dX,
                        point.y + movingEntity.dY
                );
                return movingEntity;
            }
            return null;
        }, averageVelocity);
        if (!toMatch.isEmpty()) {
            averageVelocity.setLocation(averageVelocity.x / toMatch.size(), averageVelocity.y / toMatch.size());
            this.dX += (averageVelocity.x - this.dX) * matchFactor;
            this.dY += (averageVelocity.y - this.dY) * matchFactor;
        }
    }

    public void limitSpeed(long time) {
        final double delayInSeconds = (time - this.lastMoveTime) / 1000D;
        final double speed = Math.sqrt(this.dX * this.dX + this.dY * this.dY) / delayInSeconds;
        if (speed > SPEED) {
            this.dX = (this.dX / speed) * SPEED;
            this.dY = (this.dY / speed) * SPEED;
        }
    }
}
