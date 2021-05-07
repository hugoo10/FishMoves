package fr.kahlouch.fishmoves.resource;

import fr.kahlouch.fishmoves.model.TailSide;
import fr.kahlouch.fishmoves.view.FishColor;
import fr.kahlouch.fishmoves.view.FishSprites;
import fr.kahlouch.gameresources.resource.ResourceLoader;
import fr.kahlouch.gameresources.resource.SpriteSheet;

import java.util.Map;

public class FishResourceLoader extends ResourceLoader {
    @Override
    public void loadResource() {
        final var spriteSheet = this.loadSprites("fish.png", 8, 12);
        addFishSprite(FishColor.BLUE_FISH, spriteSheet, 0, 0);
        addFishSprite(FishColor.GREEN_FISH, spriteSheet, 3, 0);
        addFishSprite(FishColor.RED_FISH, spriteSheet, 6, 0);
        addFishSprite(FishColor.GREY_FISH, spriteSheet, 9, 0);
        addFishSprite(FishColor.YELLOW_FISH, spriteSheet, 0, 4);
        addFishSprite(FishColor.BLACK_FISH, spriteSheet, 3, 4);
        addFishSprite(FishColor.WHITE_FISH, spriteSheet, 6, 4);
    }

    private void addFishSprite(FishColor color, SpriteSheet spriteSheet, int x, int y) {
        FishSprites.FISH_TAILS.put(color, Map.of(
                TailSide.LEFT, spriteSheet.getImageAt(x, y),
                TailSide.MIDDLE, spriteSheet.getImageAt(x + 1, y),
                TailSide.RIGHT, spriteSheet.getImageAt(x + 2, y)
        ));
    }
}
