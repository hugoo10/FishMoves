import controller.ResourceLoader;
import controller.WorldController;

public class Application {
    public static void main(String... args) {
        new ResourceLoader().loadBirdSprites();
        new WorldController().startGame();
    }
}
