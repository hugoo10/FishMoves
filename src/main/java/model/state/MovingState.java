package model.state;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import model.Bird;
import view.BirdSprites;

public class MovingState {
    private int currentTailPosition;
    private final long timeCalled;
    private final Node currentNode;

    public MovingState(int currentTailPosition, long timeCalled, Node node) {
        this.currentTailPosition = currentTailPosition;
        this.timeCalled = timeCalled;
        this.currentNode = node;
    }

    public void setNewCurrentNode(MovingStateContext context, long time, Bird fish) {
        final double delayInSeconds = (time - fish.getLastMoveTime()) / 1000D;
        final double speed = Math.sqrt(fish.getDX() * fish.getDX() + fish.getDY() * fish.getDY()) / delayInSeconds;
        final double delaySprite = (time - this.timeCalled) / 1000D;
        if (speed > 0 && (10 / speed) < delaySprite) {
            this.currentTailPosition = this.currentTailPosition + 1 % BirdSprites.FISH_SPRITES.size();
            final Node node = new ImageView(BirdSprites.convertToFxImage(BirdSprites.FISH_SPRITES.get(fish.getIdSprite()).get(this.currentTailPosition)));
            fish.getNode().getChildren().setAll(node);
            context.setState(new MovingState(this.currentTailPosition, time, node));
        }
    }
}
