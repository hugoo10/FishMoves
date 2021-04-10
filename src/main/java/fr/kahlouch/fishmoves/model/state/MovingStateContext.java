package fr.kahlouch.fishmoves.model.state;

import fr.kahlouch.fishmoves.model.Fish;
import lombok.Setter;

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
