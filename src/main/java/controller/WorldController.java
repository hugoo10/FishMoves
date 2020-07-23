package controller;

import javafx.stage.Stage;
import model.World;
import view.MainFrame;

import java.util.Timer;
import java.util.TimerTask;

public class WorldController {
    private World world;
    private MainFrame mainFrame;

    public void startGame(Stage stage) {
        world = new World(1920, 1080, 200);
        mainFrame = new MainFrame(this, stage);

        final TimerTask task = setupTimer(() -> {
            this.world.tick();
        });
        Timer timer = new Timer("Timer");
        long delay = 1000L / 60;
        timer.scheduleAtFixedRate(task, 0, delay);
        /*for(;;) {
            this.mainFrame.repaint();
        }*/
    }

    private TimerTask setupTimer(Runnable runnable) {
        return new TimerTask() {
            public void run() {
                runnable.run();
            }
        };
    }

    public World getWorld() {
        return world;
    }
}
