package model;

import view.FishView;

import java.awt.geom.Point2D;

public class Fish extends MovingEntity {
    private final FishView view;


    public Fish(int id, double posX, double posY, World world) {
        super(id, posX, posY, world);
        this.view = new FishView(this);
    }

    @Override
    public double getAngleInRadian() {
        return Math.atan(this.dY / this.dX) + (this.dX < 0 ? Math.PI : 0);
    }

    @Override
    public void move(long time) {
        flyTowardPoint();
        avoidPoint();
        matchPoint();
        limitSpeed(time);
        bounce();
        this.lastMoveTime = time;
        this.position.setLocation(this.position.x + this.dX, this.position.y + this.dY);
        this.addMove(new Point2D.Double(this.position.x, this.position.y));
    }

    public void updateView(long time) {
        this.view.updateView(this, time);
    }

    public FishView getView() {
        return this.view;
    }
}
