package fr.kahlouch.fishmoves.resource;

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

    public List<Image> getSprites() {
        return sprites;
    }
}
