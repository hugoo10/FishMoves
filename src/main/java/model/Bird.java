package model;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class Bird extends MovingEntity {
    private double dX;
    private double dY;

    public Bird(int id, double posX, double posY, World world) {
        super(id, posX, posY, world);
        this.dX = new Random().nextDouble() * 20 - 10;
        this.dY = new Random().nextDouble() * 20 - 10;
    }

    @Override
    public double getAngleInRadian() {
        return Math.atan(this.dY / this.dX) + (this.dX < 0 ? Math.PI : 0);
    }

    @Override
    public void move(long time) {
        flyTowardPoint();
        avoidPoint();
        matchPoint();
        limitSpeed(time);
        bounce();
        this.updateIdSprite(time);
        this.lastMoveTime = time;
        this.position.setLocation(this.position.x + this.dX, this.position.y + this.dY);
        this.addMove(new Point2D.Double(this.position.x, this.position.y));
    }

    private void bounce() {
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
                        point.x + ((Bird) movingEntity).dX,
                        point.y + ((Bird) movingEntity).dY
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

    private void limitSpeed(long time) {
        final double delayInSeconds = (time - this.lastMoveTime) / 1000D;
        final double speed = Math.sqrt(this.dX * this.dX + this.dY * this.dY) / delayInSeconds;
        if (speed > SPEED) {
            this.dX = (this.dX / speed) * SPEED;
            this.dY = (this.dY / speed) * SPEED;
        }
    }

    public void updateIdSprite(long time) {
        final double delayInSeconds = (time - this.lastMoveTime) / 1000D;
        final double speed = Math.sqrt(this.dX * this.dX + this.dY * this.dY) / delayInSeconds;
        final double delaySprite = (time - this.lastChangeIdTime) / 1000D;
        if (speed > 0 && (10 / speed) < delaySprite) {
            this.idSprite = (this.idSprite + 1) % 4;
            this.lastChangeIdTime = time;
        }
    }
}
