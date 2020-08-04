package model;

import javafx.scene.Group;
import model.state.MovingStateContext;

public class FishView extends Group {
    private MovingStateContext movingStateContext;

    public FishView() {
        this.movingStateContext = new MovingStateContext();
    }

    public void updateView(Fish fish, long time) {
        this.movingStateContext.setNewCurrentNode(time, null);
        this.setTranslateX(fish.position.x);
        this.setTranslateY(fish.position.y);
        this.setRotate((fish.getAngleInDegree() - 90) % 360);
    }
}
