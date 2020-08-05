package model.state;

import lombok.Setter;
import model.Fish;

@Setter
public class MovingStateContext {
    private MovingState state;

    public MovingStateContext(Fish fish) {
        this.state = new MovingState(0, System.currentTimeMillis(), fish);
    }

    public void setNewCurrentNode(long time) {
        state.setNewCurrentNode(this, time);
    }
}
