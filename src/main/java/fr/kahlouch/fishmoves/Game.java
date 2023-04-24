package fr.kahlouch.fishmoves;

import fr.kahlouch.fishmoves.component.ai.AIComponent;
import fr.kahlouch.fishmoves.component.ai.FishAIComponent;
import fr.kahlouch.fishmoves.component.graphics.FishGraphicsComponent;
import fr.kahlouch.fishmoves.component.graphics.GraphicsComponent;
import fr.kahlouch.fishmoves.component.physics.FishPhysicsComponent;
import fr.kahlouch.fishmoves.component.physics.PhysicsComponent;
import fr.kahlouch.fishmoves.input.SharkMoves;
import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.World;
import fr.kahlouch.fishmoves.view.FishColor;
import fr.kahlouch.gameresources.graphics._2d.Graphics2D;
import fr.kahlouch.gameresources.pattern.game_loop.FluidGameLoop;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Random;

public class Game extends FluidGameLoop {

    private AIComponent[] aiComponents;
    private PhysicsComponent[] physicsComponents;
    private GraphicsComponent[] graphicsComponents;
    private World world;
    private Graphics2D graphics;

    public Game(Stage stage, int nbEntities) {
        this.world = new World(1920, 1080);
        this.graphics = Graphics2D.builder(stage)
                .addCamera(new PerspectiveCamera(false), 0, 0, -500)
                .setFullScreen(true)
                .build();
        final var sharkMoves = new SharkMoves();
        stage.addEventHandler(MouseEvent.MOUSE_MOVED, me -> {
            sharkMoves.handleInput(me);
            sharkMoves.nextCommand().execute();
        });
        this.aiComponents = new AIComponent[nbEntities];
        this.physicsComponents = new PhysicsComponent[nbEntities];
        this.graphicsComponents = new GraphicsComponent[nbEntities];

        for (var i = 0; i < nbEntities; ++i) {
            this.aiComponents[i] = new FishAIComponent();
            this.physicsComponents[i] = new FishPhysicsComponent();
            this.graphicsComponents[i] = new FishGraphicsComponent(FishColor.values()[new Random().nextInt(FishColor.values().length)]);
            this.world.addEntity(new GameEntity(i, this.world, this.graphicsComponents[i], this.aiComponents[i], this.physicsComponents[i]));
        }
        this.graphics.show();
    }

    @Override
    protected void render() {
        graphics.startDraw();
        for (var i = 0; i < this.aiComponents.length; ++i) {
            final var gameEntity = this.world.getEntities().get(i);
            this.graphicsComponents[i].render(gameEntity, graphics);
        }
        graphics.endDraw();
    }

    @Override
    protected void update(long elapsedNano) {
        for (var i = 0; i < this.aiComponents.length; ++i) {
            final var gameEntity = this.world.getEntities().get(i);
            this.aiComponents[i].update(gameEntity, this.world);
            this.physicsComponents[i].update(gameEntity, this.world, elapsedNano);
        }
    }

    @Override
    protected void processInput() {

    }
}
