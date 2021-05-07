package fr.kahlouch.fishmoves.component.graphics;

import fr.kahlouch.fishmoves.model.GameEntity;
import fr.kahlouch.fishmoves.model.TailSide;
import fr.kahlouch.fishmoves.view.FishColor;
import fr.kahlouch.fishmoves.view.FishSprites;
import fr.kahlouch.gameresources.graphics._2d.Graphics2D;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class FishGraphicsComponent implements GraphicsComponent {
    private FishColor fishColor;

    public FishGraphicsComponent(FishColor color) {
        this.fishColor = color;
    }

    @Override
    public void render(GameEntity gameEntity, Graphics2D graphics) {
        final double angleInRad = Math.atan(gameEntity.delta.getY() / gameEntity.delta.getX()) + (gameEntity.delta.getX() < 0 ? Math.PI : 0);
        final double angleInDegree = Math.toDegrees(angleInRad);
        drawFish(graphics, gameEntity.position, angleInDegree, this.fishColor, gameEntity.tailState.getTailSide());

    }

    public void drawFish(Graphics2D graphics, Point2D position, double angleInDegree, FishColor fishColor, TailSide tailSide) {
        final var image = FishSprites.FISH_TAILS.get(fishColor).get(tailSide);
        final var imageView = new ImageView(image);
        imageView.setTranslateX(position.getX());
        imageView.setTranslateY(position.getY());
        imageView.setRotate((angleInDegree - 90) % 360);
        graphics.draw(imageView);
    }
}
