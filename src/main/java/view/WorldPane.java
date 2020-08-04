package view;

import controller.WorldController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import model.Fish;

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
}
