package fr.kahlouch.fishmoves.model.tail_state;

import fr.kahlouch.fishmoves.model.TailSide;
import javafx.scene.image.Image;

import java.util.Map;

public abstract class TailState {
    private final long creationTime;
    private final Map<TailSide, Image> tailSide;

    protected TailState() {
        this.creationTime = System.nanoTime();
        tailSide = null;
    }

    public final TailState update(final double speed) {
        final double delaySprite = (System.nanoTime() - this.creationTime) / 1_000_000_000D;
        if (speed > 0 && (10 / speed) < delaySprite) {
            return nextState();
        }
        return this;
    }

    protected abstract TailState nextState();

    public abstract TailSide getTailSide();
}
