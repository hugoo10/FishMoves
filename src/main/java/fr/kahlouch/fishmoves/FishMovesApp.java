package fr.kahlouch.fishmoves;

import fr.kahlouch.fishmoves.resource.ResourceLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class FishMovesApp extends Application {
    @Override
    public void start(Stage stage) {
        new ResourceLoader().loadBirdSprites();
        new Game(stage, 200).start();
    }
}
