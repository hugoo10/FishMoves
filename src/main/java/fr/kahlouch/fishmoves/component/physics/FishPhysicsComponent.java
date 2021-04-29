package fr.kahlouch.fishmoves.component.physics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.World;
import fr.kahlouch.fishmoves.util.Variables;
import javafx.geometry.Point2D;

public class FishPhysicsComponent implements PhysicsComponent {

    @Override
    public void update(GameEntity gameEntity, World world, long elapsedNano) {
        var newDelta = limitSpeed(gameEntity.delta, elapsedNano);
        newDelta = bounce(gameEntity, newDelta);
        gameEntity.position = gameEntity.position.add(newDelta);
        gameEntity.delta = newDelta;

        gameEntity.tailState = gameEntity.tailState.update(getSpeed(gameEntity.delta, elapsedNano));

    }

    private Point2D limitSpeed(Point2D speed, long elapsedNano) {
        final double absoluteSpeed = getSpeed(speed, elapsedNano);
        if (absoluteSpeed > Variables.MAX_SPEED) {
            return speed.multiply(1D / absoluteSpeed).multiply(Variables.MAX_SPEED);
        }
        return speed;
    }

    private double getSpeed(Point2D speed, long elapsedNano) {
        final double delayInSeconds = elapsedNano / 1_000_000_000D;
        return Math.sqrt(speed.getX() * speed.getX() + speed.getY() * speed.getY()) / delayInSeconds;
    }

    private Point2D bounce(GameEntity gameEntity, Point2D speed) {
        if (gameEntity.position.getX() + speed.getX() < 20 || gameEntity.position.getX() + speed.getX() >= 1900) {
            return new Point2D(-speed.getX(), speed.getY());
        } else if (gameEntity.position.getY() + speed.getY() < 20 || gameEntity.position.getY() + speed.getY() >= 1060) {
            return new Point2D(speed.getX(), -speed.getY());
        }
        return speed;
    }
}
