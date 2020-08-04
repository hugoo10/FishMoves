package model;

import javafx.scene.image.ImageView;
import model.state.MovingStateContext;
import view.FishSprites;

import java.util.Random;

public class FishView extends ImageView {
    private MovingStateContext movingStateContext;
    private int idSprite;

    public FishView() {
        this.idSprite = new Random().nextInt(FishSprites.FISH_SPRITES.size());
        this.movingStateContext = new MovingStateContext();
    }

    public void updateView(Fish fish, long time) {
        this.movingStateContext.setNewCurrentNode(time, fish, this);
        this.setTranslateX(fish.position.x);
        this.setTranslateY(fish.position.y);
        this.setRotate((fish.getAngleInDegree() - 90) % 360);
    }

    public int getIdSprite() {
        return idSprite;
    }
}
