package controller;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private final List<Image> sprites;

    public SpriteSheet(List<Image> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }

    public int count() {
        return sprites.size();
    }

    public Image getSprite(double progress) {
        int frame = (int) (count() * progress);
        return sprites.get(frame);
    }

    public List<Image> getSprites() {
        return sprites;
    }
}
