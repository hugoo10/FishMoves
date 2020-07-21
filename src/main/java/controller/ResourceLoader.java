package controller;

import view.BirdSprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ResourceLoader {
    public void loadBirdSprites() {
        final String imageName = "fish.png";
        final URL imageUrl = ResourceLoader.class.getClassLoader().getResource(imageName);
        BufferedImage image;
        try {
            image = ImageIO.read(imageUrl);
            final SpriteSheet spriteSheet = new SpriteSheetBuilder().
                    withSheet(image).
                    withColumns(12).
                    withRows(8).
                    withSpriteCount(8 * 12).
                    build();
            addFishSprite(BirdSprites.BLUE_FISH, spriteSheet, 0);
            addFishSprite(BirdSprites.GREEN_FISH, spriteSheet, 3);
            addFishSprite(BirdSprites.RED_FISH, spriteSheet, 6);
            addFishSprite(BirdSprites.GREY_FISH, spriteSheet, 9);
            addFishSprite(BirdSprites.YELLOW_FISH, spriteSheet, 48);
            addFishSprite(BirdSprites.BLACK_FISH, spriteSheet, 51);
            addFishSprite(BirdSprites.WHITE_FISH, spriteSheet, 54);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFishSprite(List<BufferedImage> bufferedImageList, SpriteSheet spriteSheet, int fromIdx) {
        bufferedImageList.add(spriteSheet.getSprites().get(fromIdx));
        bufferedImageList.add(spriteSheet.getSprites().get(fromIdx + 1));
        bufferedImageList.add(spriteSheet.getSprites().get(fromIdx + 2));
        bufferedImageList.add(spriteSheet.getSprites().get(fromIdx + 1));
    }
}
