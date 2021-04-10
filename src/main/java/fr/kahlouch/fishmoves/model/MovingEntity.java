package fr.kahlouch.fishmoves.model;

import fr.kahlouch.fishmoves.util.Variables;
import javafx.geometry.Point2D;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
public abstract class MovingEntity {
    protected int id;
    protected Point2D position;
    protected Point2D delta;

    protected World world;
    protected Queue<Point2D> history = new ArrayDeque<>();
    //Meta
    protected long lastMoveTime;
    protected double lastChangeIdTime;

    public MovingEntity(int id, double posX, double posY, World world) {
        this.id = id;
        this.world = world;
        this.position = new Point2D(posX, posY);
        this.lastMoveTime = System.currentTimeMillis();
        this.lastChangeIdTime = this.lastMoveTime;
        this.delta = new Point2D(new Random().nextDouble(), new Random().nextDouble()).multiply(2).multiply(Variables.SPEED).subtract(new Point2D(Variables.SPEED, Variables.SPEED));
    }

    public abstract double getAngleInRadian();


    public double getAngleInDegree() {
        return Math.toDegrees(getAngleInRadian());
    }

    public abstract void move(long time);

    public Point2D getPosition() {
        return position;
    }

    public Optional<CountResult> computeCountResult(Predicate<MovingEntity> filter, Function<MovingEntity, CountResult> mapper, BinaryOperator<CountResult> reducer) {
        return this.world.getMovingEntities().stream()
                .filter(bird -> bird.id != this.id)
                .filter(bird -> bird.position.distance(this.position) < Variables.VIEW_DISTANCE)
                .filter(filter)
                .map(mapper)
                .reduce(reducer);
    }

    protected void addMove(Point2D move) {
        this.history.add(move);
        if (this.history.size() > Variables.MAX_HISTORY) {
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
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) < Variables.TOO_CLOSE_DISTANCE,
                movingEntity -> new CountResult(this.position.subtract(movingEntity.position), 1),
                CountResult::add
        )
                .ifPresent(escapePoint ->
                        this.delta = this.delta.add(escapePoint.getPoint2D().multiply(avoidFactor))
                );

    }

    public void flyTowardPoint() {
        final double flyTowardFactor = 0.005;
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) > Variables.TOO_FAR_DISTANCE,
                CountResult::fromMovingEntityPosition,
                CountResult::add
        )
                .ifPresent(centerOfMassPoint ->
                        this.delta = this.delta.add(centerOfMassPoint.getPoint2D().subtract(this.position).multiply(flyTowardFactor))
                );
    }

    public void matchPoint() {
        final double matchFactor = 0.05;
        computeCountResult(movingEntity -> movingEntity.position.distance(this.position) <= Variables.TOO_FAR_DISTANCE && movingEntity.position.distance(this.position) >= Variables.TOO_CLOSE_DISTANCE,
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
        if (speed > Variables.SPEED) {
            this.delta = this.delta.multiply(1D / speed).multiply(Variables.SPEED);
        }
    }

    public double getSpeed(long time) {
        final double delayInSeconds = (time - this.lastMoveTime) / 1000D;
        return Math.sqrt(this.delta.getX() * this.delta.getX() + this.delta.getY() * this.delta.getY()) / delayInSeconds;
    }
}
