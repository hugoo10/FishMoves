package view;

import controller.WorldController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainFrame {
    private final WorldPane worldPane;
    private final WorldController worldController;

    public MainFrame(WorldController worldController, Stage stage) {
        stage.setFullScreen(true);
        this.worldController = worldController;

        Group group = new Group();
        Scene scene = new Scene(group);
        scene.setFill(Color.BLACK);
        stage.setTitle("Mars lander");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        this.worldPane = new WorldPane(worldController, group);
    }

    public void repaint() {
        this.worldPane.repaint();
    }
}
