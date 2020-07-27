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

        Scene scene = new Scene(new Group());
        scene.setFill(Color.BLACK);
        stage.setTitle("Mars lander");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        this.worldPane = new WorldPane(worldController, scene);


        /*super("birds");
        this.worldController = worldController;
        this.worldPane = new WorldPane(worldController);
        this.setContentPane(this.worldPane);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);*/
    }

    public void repaint() {
        //this.worldPane.repaint();
    }
}
