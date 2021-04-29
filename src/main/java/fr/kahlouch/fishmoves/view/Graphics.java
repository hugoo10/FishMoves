package fr.kahlouch.fishmoves.view;

import fr.kahlouch.fishmoves.model.TailSide;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Graphics {
    private Group fishGroup;
    private Group current_;
    private Group next_;

    public Graphics(Stage stage) {
        Group root = new Group();
        fishGroup = new Group();
        this.current_ = new Group();
        this.next_ = new Group();
        root.getChildren().add(fishGroup);
        final Scene gameScene = new Scene(root);
        setCamera(root, gameScene);
        stage.setScene(gameScene);
        stage.setFullScreen(true);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void startDraw() {
        next_.getChildren().clear();
    }

    public void endDraw() {
        swap();
        fishGroup.getChildren().setAll(current_);
    }


    private void swap() {
        var tmp = current_;
        current_ = next_;
        next_ = tmp;
    }

    public void drawFish(Point2D position, double angleInDegree, FishColor fishColor, TailSide tailSide) {
        final var image = FishSprites.FISH_TAILS.get(fishColor).get(tailSide);
        final var imageView = new ImageView(image);
        imageView.setTranslateX(position.getX());
        imageView.setTranslateY(position.getY());
        imageView.setRotate((angleInDegree - 90) % 360);
        next_.getChildren().add(imageView);
    }

    private void setCamera(Group root, Scene scene) {
        final Camera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);
        var cameraGroup = new Group();
        cameraGroup.getChildren().add(camera);
        root.getChildren().add(cameraGroup);

        cameraGroup.setTranslateZ(-500);
    }
}
