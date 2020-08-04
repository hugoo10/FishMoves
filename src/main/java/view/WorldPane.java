package view;

import controller.WorldController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import model.Bird;
import model.MovingEntity;

import java.awt.*;
import java.util.ArrayList;

public class WorldPane {
    private final WorldController worldController;
    private final Group group;

    public WorldPane(WorldController worldController, Group group) {
        this.worldController = worldController;
        this.group = group;
        worldController.getWorld().getMovingEntities().forEach(movingEntity -> {
            group.getChildren().add(movingEntity.getNode());
        });
    }


    public void repaint() {
        long time = System.currentTimeMillis();
        final AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                worldController.getWorld().getMovingEntities().forEach(movingEntity -> {
                    ((Bird)movingEntity).getMovingStateContext().setNewCurrentNode(time, ((Bird)movingEntity));
                    Node polygon = movingEntity.getNode();
                    polygon.setTranslateY(movingEntity.getPosition().y);
                    polygon.setTranslateX(movingEntity.getPosition().x);
                    polygon.setRotate((Math.toDegrees(movingEntity.getAngleInRadian()) - 90) % 360);
                });
            }
        };
        gameLoop.start();
    }

    private void renderMove(MovingEntity movingEntity, Graphics2D graphics2D) {
        graphics2D.setColor(Color.RED);
        var list = new ArrayList<>(movingEntity.getHistory());
        for (int i = 0; i < list.size() - 1; ++i) {
            final var from = list.get(i);
            final var to = list.get(i + 1);
            graphics2D.drawLine((int) from.x, (int) from.y, (int) to.x, (int) to.y);
        }
    }
}
