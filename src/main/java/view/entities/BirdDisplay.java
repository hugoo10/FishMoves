package view.entities;

import model.MovingEntity;
import view.BirdSprites;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BirdDisplay {
    private BirdDisplay() {
    }

    public static void renderFish(MovingEntity movingEntity, Graphics2D graphics2D) {
        graphics2D.rotate(movingEntity.getAngleInRadian() - Math.PI / 2, (int) movingEntity.getPosition().x, (int) movingEntity.getPosition().y);
        List<BufferedImage> fishSprites = BirdSprites.FISH_SPRITES.get(movingEntity.getId() % BirdSprites.FISH_SPRITES.size());
        graphics2D.drawImage(fishSprites.get(movingEntity.getIdSprite()), null, (int) movingEntity.getPosition().x - 16, (int) movingEntity.getPosition().y - 16);
    }

    public static Polygon renderMovingEntity(MovingEntity movingEntity) {
        Polygon polygon = new Polygon(new int[]{12, -6, -6}, new int[]{0, 6, -6}, 3);
        polygon.translate((int) movingEntity.getPosition().x, (int) movingEntity.getPosition().y);
        return rotatePolygon(polygon, movingEntity.getPosition(), movingEntity.getAngleInRadian());
    }


    private static Polygon rotatePolygon(Polygon polygon, Point2D.Double center, double rotationAngleRad) throws IllegalArgumentException {
        // copy the arrays so that we dont manipulate the originals, that way we can
        // reuse them if necessary
        int[] xpoints = Arrays.copyOf(polygon.xpoints, polygon.xpoints.length);
        int[] ypoints = Arrays.copyOf(polygon.ypoints, polygon.ypoints.length);
        if (xpoints.length != ypoints.length) {
            throw new IllegalArgumentException("The provided x points are not the same length as the provided y points.");
        }

        // create a list of Point2D pairs
        ArrayList<Point2D> list = new ArrayList<>();
        for (int i = 0; i < ypoints.length; i++) {
            list.add(new Point2D.Double(xpoints[i], ypoints[i]));
        }

        // create an array which will hold the rotated points
        Point2D[] rotatedPoints = new Point2D[list.size()];

        // rotate the points
        AffineTransform transform = AffineTransform.getRotateInstance(rotationAngleRad, center.x, center.y);
        transform.transform(list.toArray(new Point2D[0]), 0, rotatedPoints, 0, rotatedPoints.length);

        // build the polygon from the rotated points and return it
        int[] ixp = new int[list.size()];
        int[] iyp = new int[list.size()];
        for (int i = 0; i < ixp.length; i++) {
            ixp[i] = (int) rotatedPoints[i].getX();
            iyp[i] = (int) rotatedPoints[i].getY();
        }
        return new Polygon(ixp, iyp, ixp.length);
    }
}
