package fr.kahlouch.fishmoves.component.graphics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.view.FishColor;
import fr.kahlouch.fishmoves.view.Graphics;

public class FishGraphicsComponent implements GraphicsComponent {
    private FishColor fishColor;

    public FishGraphicsComponent(FishColor color) {
        this.fishColor = color;
    }

    @Override
    public void render(GameEntity gameEntity, Graphics graphics) {
        final double angleInRad = Math.atan(gameEntity.delta.getY() / gameEntity.delta.getX()) + (gameEntity.delta.getX() < 0 ? Math.PI : 0);
        final double angleInDegree = Math.toDegrees(angleInRad);
        graphics.drawFish(gameEntity.position, angleInDegree, this.fishColor, gameEntity.tailState.getTailSide());

    }
}
