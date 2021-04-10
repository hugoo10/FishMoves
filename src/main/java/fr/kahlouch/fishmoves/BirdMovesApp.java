package fr.kahlouch.fishmoves;

import fr.kahlouch.fishmoves.controller.ResourceLoader;
import fr.kahlouch.fishmoves.controller.WorldController;
import javafx.application.Application;
import javafx.stage.Stage;

public class BirdMovesApp extends Application {
    @Override
    public void start(Stage stage) {
        new ResourceLoader().loadBirdSprites();
        new WorldController().startGame(stage);
    }
}
