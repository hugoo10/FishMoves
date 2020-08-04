package controller;

import javafx.stage.Stage;
import model.World;
import view.MainFrame;

public class WorldController {
    private World world;
    private MainFrame mainFrame;

    public void startGame(Stage stage) {
        world = new World(1920, 1080, 200);
        mainFrame = new MainFrame(this, stage);

        this.world.runWorld();
        this.mainFrame.repaint();
    }


    public World getWorld() {
        return world;
    }
}
