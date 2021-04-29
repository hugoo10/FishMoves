package fr.kahlouch.fishmoves.component.graphics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.view.Graphics;

public interface GraphicsComponent {
    void render(GameEntity gameEntity, Graphics graphics);
}
