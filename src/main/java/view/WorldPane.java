package view;

import controller.WorldController;
import javafx.scene.Scene;
import model.MovingEntity;
import view.entities.BirdDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WorldPane {
    private final WorldController worldController;
    private final Scene scene;

    public WorldPane(WorldController worldController, Scene scene) {
        this.worldController = worldController;
        this.scene = scene;
    }


    protected void paintComponent(Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(55, 98, 140));
        graphics2D.fillRect(0, 0, 1920, 1080);

        worldController.getWorld().getMovingEntities().parallelStream().forEach(movingEntity -> {
            BirdDisplay.renderFish(movingEntity, (Graphics2D)graphics2D.create());
            renderMove(movingEntity, (Graphics2D)graphics2D.create());
        });
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
