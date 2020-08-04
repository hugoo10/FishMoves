package view;

import javafx.scene.image.ImageView;
import model.Fish;
import model.state.MovingStateContext;

import java.util.Random;

public class FishView extends ImageView {
    private final MovingStateContext movingStateContext;
    private final int idSprite;

    public FishView() {
        this.idSprite = new Random().nextInt(FishSprites.FISH_SPRITES.size());
        this.movingStateContext = new MovingStateContext();
    }

    public void updateView(Fish fish, long time) {
        this.movingStateContext.setNewCurrentNode(time, fish, this);
        this.setTranslateX(fish.getPosition().x);
        this.setTranslateY(fish.getPosition().y);
        this.setRotate((fish.getAngleInDegree() - 90) % 360);
    }

    public int getIdSprite() {
        return idSprite;
    }
}
