package model.state;

import javafx.scene.image.ImageView;
import lombok.Setter;
import model.Fish;

@Setter
public class MovingStateContext {
    private MovingState state;

    public MovingStateContext() {
        this.state = new MovingState(0, System.currentTimeMillis());
    }

    public void setNewCurrentNode(long time, Fish fish, ImageView imageView) {
        state.setNewCurrentNode(this, time, fish, imageView);
    }
}
