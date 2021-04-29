package fr.kahlouch.fishmoves.resource;

import fr.kahlouch.fishmoves.model.TailSide;
import fr.kahlouch.fishmoves.view.FishColor;
import fr.kahlouch.fishmoves.view.FishSprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class ResourceLoader {
    public void loadBirdSprites() {
        final var imageName = "fish.png";
        final var imageUrl = ResourceLoader.class.getClassLoader().getResource(imageName);
        BufferedImage image;
        try {
            image = ImageIO.read(imageUrl);
            final SpriteSheet spriteSheet = new SpriteSheetBuilder().
                    withSheet(image).
                    withColumns(12).
                    withRows(8).
                    withSpriteCount(8 * 12).
                    build();

            addFishSprite(FishColor.BLUE_FISH, spriteSheet, 0);
            addFishSprite(FishColor.GREEN_FISH, spriteSheet, 3);
            addFishSprite(FishColor.RED_FISH, spriteSheet, 6);
            addFishSprite(FishColor.GREY_FISH, spriteSheet, 9);
            addFishSprite(FishColor.YELLOW_FISH, spriteSheet, 48);
            addFishSprite(FishColor.BLACK_FISH, spriteSheet, 51);
            addFishSprite(FishColor.WHITE_FISH, spriteSheet, 54);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addFishSprite(FishColor color, SpriteSheet spriteSheet, int fromIdx) {
        FishSprites.FISH_TAILS.put(color, Map.of(
                TailSide.LEFT, spriteSheet.getSprites().get(fromIdx),
                TailSide.MIDDLE, spriteSheet.getSprites().get(fromIdx + 1),
                TailSide.RIGHT, spriteSheet.getSprites().get(fromIdx + 2)
        ));
    }
}
