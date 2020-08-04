package model.state;

import javafx.scene.image.ImageView;
import model.Fish;
import view.FishSprites;
import view.FishView;

public class MovingState {
    private int currentTailPosition;
    private final long timeCalled;

    public MovingState(int currentTailPosition, long timeCalled) {
        this.currentTailPosition = currentTailPosition;
        this.timeCalled = timeCalled;
    }

    public void setNewCurrentNode(MovingStateContext context, long time, Fish fish, ImageView imageView) {
        final double delayInSeconds = (time - fish.getLastMoveTime()) / 1000D;
        final double speed = Math.sqrt(fish.getDX() * fish.getDX() + fish.getDY() * fish.getDY()) / delayInSeconds;
        final double delaySprite = (time - this.timeCalled) / 1000D;
        if (speed > 0 && (10 / speed) < delaySprite) {
            this.currentTailPosition = (this.currentTailPosition + 1) % 4;
            imageView.setImage(FishSprites.FISH_SPRITES.get(((FishView)imageView).getIdSprite()).get(this.currentTailPosition));
            context.setState(new MovingState(this.currentTailPosition, time));
        }
    }
}
