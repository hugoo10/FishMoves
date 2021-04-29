package fr.kahlouch.fishmoves.view;

import fr.kahlouch.fishmoves.model.TailSide;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FishSprites {
    public static final Map<FishColor, Map<TailSide, Image>> FISH_TAILS = new HashMap<>();
}
