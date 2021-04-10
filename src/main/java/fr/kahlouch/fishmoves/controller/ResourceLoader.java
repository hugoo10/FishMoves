package fr.kahlouch.fishmoves.controller;

import fr.kahlouch.fishmoves.view.FishSprites;
import javafx.scene.image.Image;

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
            addFishSprite(FishSprites.BLUE_FISH, spriteSheet, 0);
            addFishSprite(FishSprites.GREEN_FISH, spriteSheet, 3);
            addFishSprite(FishSprites.RED_FISH, spriteSheet, 6);
            addFishSprite(FishSprites.GREY_FISH, spriteSheet, 9);
            addFishSprite(FishSprites.YELLOW_FISH, spriteSheet, 48);
            addFishSprite(FishSprites.BLACK_FISH, spriteSheet, 51);
            addFishSprite(FishSprites.WHITE_FISH, spriteSheet, 54);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFishSprite(List<Image> imageList, SpriteSheet spriteSheet, int fromIdx) {
        imageList.add(spriteSheet.getSprites().get(fromIdx));
        imageList.add(spriteSheet.getSprites().get(fromIdx + 1));
        imageList.add(spriteSheet.getSprites().get(fromIdx + 2));
        imageList.add(spriteSheet.getSprites().get(fromIdx + 1));
    }
}
