package model;

import java.awt.geom.Point2D;
import java.util.Random;

public class Bird2 extends MovingEntity {
    private double dX;
    private double dY;

    public Bird2(int id, double posX, double posY, World world) {
        super(id, posX, posY, world);
        this.dX = new Random().nextDouble() * 2 - 1;
        this.dY = new Random().nextDouble() * 2 - 1;
    }

    @Override
    public int getAngleInDegree() {
        return (int) Math.toDegrees(getAngleInRadian());
    }

    @Override
    public double getAngleInRadian() {
        return Math.atan(-dY / dX) + (dX < 0 ? Math.PI : 0);
    }

    @Override
    public void move(long time) {
        final Point2D.Double direction = getDirection();

    }

    private Point2D.Double getDirection() {
        return new Point2D.Double();
    }
}
