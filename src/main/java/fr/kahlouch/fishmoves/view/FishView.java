package fr.kahlouch.fishmoves.view;

import fr.kahlouch.fishmoves.model.Fish;
import fr.kahlouch.fishmoves.model.state.MovingStateContext;
import javafx.scene.image.ImageView;

import java.util.Random;

public class FishView extends ImageView {
    private final MovingStateContext movingStateContext;
    private final int idSprite;

    public FishView(Fish fish) {
        this.idSprite = new Random().nextInt(FishSprites.FISH_SPRITES.size());
        this.movingStateContext = new MovingStateContext(fish);
    }

    public void updateView(Fish fish, long time) {
        this.movingStateContext.setNewCurrentNode(time);
        this.setTranslateX(fish.getPosition().getX());
        this.setTranslateY(fish.getPosition().getY());
        this.setRotate((fish.getAngleInDegree() - 90) % 360);
    }

    public int getIdSprite() {
        return idSprite;
    }
}
