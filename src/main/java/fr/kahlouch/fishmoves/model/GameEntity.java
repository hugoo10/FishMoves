package fr.kahlouch.fishmoves.model;


import fr.kahlouch.fishmoves.component.ai.AIComponent;
import fr.kahlouch.fishmoves.component.graphics.GraphicsComponent;
import fr.kahlouch.fishmoves.component.physics.PhysicsComponent;
import fr.kahlouch.fishmoves.model.tail_state.TailMiddleFromRightState;
import fr.kahlouch.fishmoves.model.tail_state.TailState;
import javafx.geometry.Point2D;

import java.util.Random;

public class GameEntity {
    private static final double SECOND_IN_NANO = 1_000_000_000D;

    public int id;
    public Point2D delta;
    public Point2D position;
    public TailState tailState;

    private long timeSinceLastTailMove = 0;

    private final GraphicsComponent graphicsComponent;
    private final AIComponent aiComponent;
    private final PhysicsComponent physicsComponent;

    public GameEntity(int id, World world, GraphicsComponent graphicsComponent, AIComponent aiComponent, PhysicsComponent physicsComponent) {
        this.id = id;
        this.graphicsComponent = graphicsComponent;
        this.aiComponent = aiComponent;
        this.physicsComponent = physicsComponent;
        this.tailState = TailMiddleFromRightState.INSTANCE;
        this.delta = new Point2D(new Random().nextDouble() * 10 + 10, new Random().nextDouble() * 10 + 10);

    }

    public static GameEntity createFish() {
        return null;
    }

    public void move(Point2D newDelta) {
        this.position = this.position.add(newDelta);
        this.delta = newDelta;
    }

    public void moveTail(double speed, long elapsedNano) {
        this.timeSinceLastTailMove += elapsedNano;
        final double delaySprite = this.timeSinceLastTailMove / SECOND_IN_NANO;
        if (speed <= 0 || (10 / speed) >= delaySprite) return;
        this.tailState = this.tailState.nextState();
        this.timeSinceLastTailMove = 0;
    }

}
