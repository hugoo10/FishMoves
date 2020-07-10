package view;

import controller.WorldController;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final WorldPane worldPane;
    private final WorldController worldController;

    public MainFrame(WorldController worldController) {
        super("birds");
        this.worldController = worldController;
        this.worldPane = new WorldPane(worldController);
        this.setContentPane(this.worldPane);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void repaint() {
        this.worldPane.repaint();
    }
}
