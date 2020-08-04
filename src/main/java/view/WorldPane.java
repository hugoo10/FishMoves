package view;

import controller.WorldController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import model.Fish;
import model.MovingEntity;

import java.awt.*;
import java.util.ArrayList;

public class WorldPane {
    private final WorldController worldController;

    public WorldPane(WorldController worldController, Group group) {
        this.worldController = worldController;
        worldController.getWorld().getMovingEntities().forEach(movingEntity -> {
            group.getChildren().add(((Fish) movingEntity).getView());
        });
    }


    public void repaint() {
        final AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long time = System.currentTimeMillis();
                worldController.getWorld().getMovingEntities().forEach(movingEntity -> {
                    ((Fish) movingEntity).updateView(time);
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
