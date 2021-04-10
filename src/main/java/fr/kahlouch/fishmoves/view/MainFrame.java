package fr.kahlouch.fishmoves.view;

import fr.kahlouch.fishmoves.controller.WorldController;
import javafx.application.Platform;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainFrame {
    private final WorldPane worldPane;

    public MainFrame(WorldController worldController, Stage stage) {
        stage.setFullScreen(true);

        Group root = new Group();
        Group group = new Group();
        root.getChildren().add(group);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        stage.setTitle("Mars lander");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });


        setCamera(-500, root, scene);
        this.worldPane = new WorldPane(worldController, group);
    }

    public void repaint() {
        this.worldPane.repaint();
    }

    private void setCamera(int zTranslate, Group root, Scene scene) {
        final Camera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);

        Group cameraGroup = new Group();
        cameraGroup.getChildren().add(camera);
        root.getChildren().add(cameraGroup);

        cameraGroup.setTranslateZ(zTranslate);
        //camera.setRotate(180);
    }
}
