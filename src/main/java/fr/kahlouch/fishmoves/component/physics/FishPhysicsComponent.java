package fr.kahlouch.fishmoves.component.physics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.Shark;
import fr.kahlouch.fishmoves.model.World;
import fr.kahlouch.fishmoves.util.Constants;
import javafx.geometry.Point2D;

import java.util.Optional;

public class FishPhysicsComponent implements PhysicsComponent {

    @Override
    public void update(GameEntity gameEntity, World world, long elapsedNano) {

        var newDelta = flee(gameEntity, elapsedNano).orElseGet(() -> limitSpeed(gameEntity.delta, elapsedNano));
        newDelta = bounce(gameEntity, newDelta);

        gameEntity.move(newDelta);
        gameEntity.moveTail(getSpeed(gameEntity.delta, elapsedNano), elapsedNano);
    }

    private Optional<Point2D> flee(GameEntity gameEntity,long elapsedNano) {
        if(gameEntity.position.distance(Shark.INSTANCE.getPosition()) < 200){
            return Optional.of(maxSpeed(gameEntity.position.subtract(Shark.INSTANCE.getPosition()), elapsedNano));
        }
        return Optional.empty();
    }

    private Point2D maxSpeed(Point2D direction, long elapsedNano) {
        final double absoluteSpeed = getSpeed(direction, elapsedNano);
        return direction.multiply(1D / absoluteSpeed).multiply(Constants.MAX_SPEED);
    }
    private Point2D limitSpeed(Point2D speed, long elapsedNano) {
        final double absoluteSpeed = getSpeed(speed, elapsedNano);
        if (absoluteSpeed > Constants.MAX_SPEED) {
            return maxSpeed(speed, elapsedNano);
        }
        return speed;
    }

    private double getSpeed(Point2D speed, long elapsedNano) {
        final double delayInSeconds = elapsedNano / 1_000_000_000D;
        return Math.sqrt(speed.getX() * speed.getX() + speed.getY() * speed.getY()) / delayInSeconds;
    }

    private Point2D bounce(GameEntity gameEntity, Point2D delta) {
        if (gameEntity.position.getX() + delta.getX() < 20 || gameEntity.position.getX() + delta.getX() >= 1900) {
            return new Point2D(-delta.getX(), delta.getY());
        } else if (gameEntity.position.getY() + delta.getY() < 20 || gameEntity.position.getY() + delta.getY() >= 1060) {
            return new Point2D(delta.getX(), -delta.getY());
        }
        return delta;
    }
}
