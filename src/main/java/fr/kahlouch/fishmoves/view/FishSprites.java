package fr.kahlouch.fishmoves.view;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FishSprites {
    public static final List<Image> BLUE_FISH = new ArrayList<>();
    public static final List<Image> RED_FISH = new ArrayList<>();
    public static final List<Image> GREEN_FISH = new ArrayList<>();
    public static final List<Image> YELLOW_FISH = new ArrayList<>();
    public static final List<Image> WHITE_FISH = new ArrayList<>();
    public static final List<Image> BLACK_FISH = new ArrayList<>();
    public static final List<Image> GREY_FISH = new ArrayList<>();

    public static final List<List<Image>> FISH_SPRITES = List.of(BLUE_FISH, RED_FISH, GREEN_FISH, YELLOW_FISH, WHITE_FISH, BLACK_FISH, GREY_FISH);
}
