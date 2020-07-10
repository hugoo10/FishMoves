package view;

import controller.WorldController;
import model.Bird;
import view.entities.BirdDisplay;

import javax.swing.*;
import java.awt.*;

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
        graphics2D.setColor(Color.BLUE);
        for (Bird bird : worldController.getWorld().getBirds()) {
            graphics2D.fillPolygon(BirdDisplay.renderBird(bird));
        }
    }
}
