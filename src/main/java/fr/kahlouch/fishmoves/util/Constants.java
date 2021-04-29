package fr.kahlouch.fishmoves.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final double VIEW_DISTANCE = 160;
    public static final double TOO_CLOSE_DISTANCE = 40;
    public static final double TOO_FAR_DISTANCE = 160;
    public static final int MAX_SPEED = 300;
    public static final int MAX_HISTORY = 100;
    public static final boolean DISPLAY_HISTORY = true;
}
