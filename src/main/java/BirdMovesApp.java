import controller.ResourceLoader;
import controller.WorldController;
import javafx.application.Application;
import javafx.stage.Stage;

public class BirdMovesApp extends Application {
    @Override
    public void start(Stage stage) {
        new ResourceLoader().loadBirdSprites();
        new WorldController().startGame(stage);
    }
}
