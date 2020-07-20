package view;

import controller.WorldController;
import model.MovingEntity;
import view.entities.BirdDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WorldPane extends JPanel {
    private final WorldController worldController;

    public WorldPane(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, 1920, 1080);

        for (MovingEntity movingEntity : worldController.getWorld().getMovingEntities()) {
            graphics2D.setColor(Color.BLUE);
            graphics2D.fillPolygon(BirdDisplay.renderMovingEntity(movingEntity));
            renderMove(movingEntity, graphics2D);
        }
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
