package fr.kahlouch.fishmoves.component.graphics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.gameresources.graphics._2d.Graphics2D;

public interface GraphicsComponent {
    void render(GameEntity gameEntity, Graphics2D graphics);
}
