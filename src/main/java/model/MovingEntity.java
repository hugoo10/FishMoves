package model;

import javafx.geometry.Point2D;
import lombok.Getter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
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
    protected Point2D position;
    protected World world;
    protected Point2D delta;

    protected Queue<Point2D> history = new ArrayDeque<>();
    //Meta
    protected long lastMoveTime;

    public MovingEntity(int id, double posX, double posY, World world) {
        this.id = id;
        this.world = world;
        this.position = new Point2D(posX, posY);
        this.lastMoveTime = System.currentTimeMillis();
        this.lastChangeIdTime = this.lastMoveTime;
        this.delta = new Point2D(new Random().nextDouble() * 20 - 10, new Random().nextDouble() * 20 - 10);
    }

    public abstract double getAngleInRadian();


    public double getAngleInDegree() {
        return Math.toDegrees(getAngleInRadian());
    }

    public abstract void move(long time);

    public Point2D getPosition() {
        return position;
    }

    public <T> List<MovingEntity> getClosestWithAdditionalBehaviour(BiFunction<MovingEntity, T, MovingEntity> additional, T object) {
        return this.world.getMovingEntities().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE)
                .map(movingEntity -> additional.apply(movingEntity, object))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Optional<CountResult> computeCountResult(Predicate<MovingEntity> filter, Function<MovingEntity, CountResult> mapper, BinaryOperator<CountResult> reducer) {
        return this.world.getMovingEntities().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < VIEW_DISTANCE)
                .filter(filter)
                .map(mapper)
                .reduce(reducer);
    }

    protected void addMove(Point2D move) {
        this.history.add(move);
        if (this.history.size() > MAX_HISTORY) {
            this.history.remove();
        }
    }

    public Queue<Point2D> getHistory() {
        return history;
    }

    public void bounce() {
        if (position.getX() + delta.getX() < 20 || position.getX() + delta.getX() >= 1900) {
            this.delta = new Point2D(-delta.getX(), delta.getY());
        } else if (position.getY() + delta.getY() < 20 || position.getY() + delta.getY() >= 1060) {
            this.delta = new Point2D(delta.getX(), -delta.getY());
        }
    }

    public void avoidPoint() {
        final double avoidFactor = 0.05;
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) < TOO_CLOSE_DISTANCE,
                movingEntity -> new CountResult(this.position.subtract(movingEntity.position), 1),
                CountResult::add
        )
                .ifPresent(escapePoint ->
                        this.delta = this.delta.add(escapePoint.getPoint2D().multiply(avoidFactor))
                );

    }

    public void flyTowardPoint() {
        final double flyTowardFactor = 0.005;
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) > TOO_FAR_DISTANCE,
                CountResult::fromMovingEntityPosition,
                CountResult::add
        )
                .ifPresent(centerOfMassPoint ->
                        this.delta = this.delta.add(centerOfMassPoint.getPoint2D().subtract(this.position).multiply(flyTowardFactor))
                );
    }

    public void matchPoint() {
        final double matchFactor = 0.05;
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) <= TOO_FAR_DISTANCE && movingEntity.position.distance(this.position) >= TOO_CLOSE_DISTANCE,
                CountResult::fromMovingEntityDelta,
                CountResult::add
        )
                .ifPresent(averageVelocity -> {
                    final Point2D averageVelocityPt = averageVelocity.getPoint2D().multiply(1D / averageVelocity.getNumber());
                    this.delta = this.delta.add(averageVelocityPt.subtract(this.delta).multiply(matchFactor));
                });
    }

    public void limitSpeed(long time) {
        final double speed = getSpeed(time);
        if (speed > SPEED) {
            this.delta = this.delta.multiply(1D / speed).multiply(SPEED);
        }
    }

    public double getSpeed(long time) {
        final double delayInSeconds = (time - this.lastMoveTime) / 1000D;
        return Math.sqrt(this.delta.getX() * this.delta.getX() + this.delta.getY() * this.delta.getY()) / delayInSeconds;
    }
}
