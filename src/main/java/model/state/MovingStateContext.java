package model.state;

import lombok.Setter;
import model.Bird;

@Setter
public class MovingStateContext {
    private MovingState state;

    public MovingStateContext() {
        this.state = new MovingState(0, System.currentTimeMillis(), null);
    }

    public void setNewCurrentNode(long time, Bird fish) {
        state.setNewCurrentNode(this, time, fish);
    }
}
