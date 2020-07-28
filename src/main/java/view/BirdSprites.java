package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class BirdSprites {
    private BirdSprites() {
    }

    public static final List<BufferedImage> BLUE_FISH = new ArrayList<>();
    public static final List<BufferedImage> RED_FISH = new ArrayList<>();
    public static final List<BufferedImage> GREEN_FISH = new ArrayList<>();
    public static final List<BufferedImage> YELLOW_FISH = new ArrayList<>();
    public static final List<BufferedImage> WHITE_FISH = new ArrayList<>();
    public static final List<BufferedImage> BLACK_FISH = new ArrayList<>();
    public static final List<BufferedImage> GREY_FISH = new ArrayList<>();


    public static final List<List<BufferedImage>> FISH_SPRITES = List.of(BLUE_FISH, RED_FISH, GREEN_FISH, YELLOW_FISH, WHITE_FISH, BLACK_FISH, GREY_FISH);

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }


}
