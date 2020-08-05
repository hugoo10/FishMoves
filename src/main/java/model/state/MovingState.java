package model.state;

import model.Fish;
import view.FishSprites;

public class MovingState {
    private final int currentTailPosition;
    private final long timeCalled;
    private final Fish fish;

    public MovingState(int currentTailPosition, long timeCalled, Fish fish) {
        this.currentTailPosition = currentTailPosition;
        this.timeCalled = timeCalled;
        this.fish = fish;
        if (fish.getView() != null) {
            fish.getView().setImage(FishSprites.FISH_SPRITES.get(fish.getView().getIdSprite()).get(this.currentTailPosition));
        }
    }

    public void setNewCurrentNode(MovingStateContext context, long time) {
        final double speed = this.fish.getSpeed(time);
        final double delaySprite = (time - this.timeCalled) / 1000D;
        if (speed > 0 && (10 / speed) < delaySprite) {
            context.setState(new MovingState((this.currentTailPosition + 1) % 4, time, this.fish));
        }
    }
}
