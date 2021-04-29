package fr.kahlouch.fishmoves.model;


import fr.kahlouch.fishmoves.component.ai.AIComponent;
import fr.kahlouch.fishmoves.component.graphics.GraphicsComponent;
import fr.kahlouch.fishmoves.component.physics.PhysicsComponent;
import fr.kahlouch.fishmoves.model.tail_state.TailMiddleFromRightState;
import fr.kahlouch.fishmoves.model.tail_state.TailState;
import javafx.geometry.Point2D;

public class GameEntity {
    public int id;
    public Point2D speed;
    public Point2D delta;
    public Point2D position;
    public TailState tailState;

    private final GraphicsComponent graphicsComponent;
    private final AIComponent aiComponent;
    private final PhysicsComponent physicsComponent;

    public GameEntity(int id, World world, GraphicsComponent graphicsComponent, AIComponent aiComponent, PhysicsComponent physicsComponent) {
        this.id = id;
        this.graphicsComponent = graphicsComponent;
        this.aiComponent = aiComponent;
        this.physicsComponent = physicsComponent;
        this.tailState = new TailMiddleFromRightState();
        this.delta = new Point2D(0,0);

    }

    public static GameEntity createFish() {
        return null;
    }
}
