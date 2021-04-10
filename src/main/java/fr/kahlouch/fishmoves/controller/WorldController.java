package fr.kahlouch.fishmoves.controller;

import fr.kahlouch.fishmoves.model.World;
import fr.kahlouch.fishmoves.view.MainFrame;
import javafx.stage.Stage;

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
